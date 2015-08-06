package Server.game.beta;

import java.io.*;
import java.net.Socket;

public class GameClientBeta extends Thread {
    private static int currentChipBalance=1000;

    static ObjectOutputStream out;
    static ObjectInputStream in;
    static BufferedReader inu;
    public static void main(String[] args) throws IOException {
        boolean isLost=false;

        System.out.println("Подключаемся к серверу. Порт 12345");
        Socket socket=new Socket("localhost",12345);
        System.out.println("Соединение установлено"+socket);
        inu=new BufferedReader(new InputStreamReader(System.in));
        out=new ObjectOutputStream(socket.getOutputStream());
        in=new ObjectInputStream(socket.getInputStream());

        new GameClientBeta().start();

        while (!isLost){
            System.out.printf("1.Rise\n2.All-In\n3.check\n4.call\n5.fold\n10.....\n");
            int myChoose = Integer.parseInt(inu.readLine());
            switch (myChoose){
                case 1:out.writeObject(rise());
                    break;
                case 2:
                    out.writeObject(allIn());
                    break;
                case 3:out.writeObject(check());
                    break;
                case 4:out.writeObject(call());
                    break;
                case 5:out.writeObject(fold());
                    break;
                case 10:return;
                
            }
        }
    }




    @Override
    public void run() {
        Move fromServer;
        try {

            while (true){
                fromServer= (Move) in.readObject();
                print(fromServer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    synchronized void print(Move move){
        System.out.println(move.move+" "+move.currentChips+" "+move.bet);
    }

    public static Move fold(){
        return new Move("fold",currentChipBalance,0,false);
    }

    private static Move call() {
        return new Move("call",currentChipBalance,123,false);
    }

    private static Move check() {
        return new Move("check",currentChipBalance,123,false);
    }

    private static Move allIn() {
        return new Move("all-in",currentChipBalance,321,false);
    }

    private static Move rise() {
        return new Move("rise",currentChipBalance,222,false);
    }

}

