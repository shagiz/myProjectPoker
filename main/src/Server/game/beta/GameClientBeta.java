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

        String fromUser;
        while ((fromUser=inu.readLine())!=null){
            out.writeObject(fromUser);
        }
    }

    @Override
    public void run() {
        String fromServer;
        try {

            while (true){
                fromServer= (String) in.readObject();
                print(fromServer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    synchronized void print(String string){
        System.out.println(string);
    }

    public static Move fold(){
        return new Move("fold",currentChipBalance,0,false);
    }
}

