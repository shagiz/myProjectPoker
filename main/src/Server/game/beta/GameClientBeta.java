package Server.game.beta;

import java.io.*;
import java.net.Socket;

public class GameClientBeta extends Thread {
    private static int numberOfChips;
    private static boolean myTurn=true;
    private static boolean isLost=false;

    public static String playerName;

    private static BufferedReader inu;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    private static Move move;


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket=new Socket("localhost",12345);
        inu=new BufferedReader(new InputStreamReader(System.in));
        out=new ObjectOutputStream(socket.getOutputStream());
        in=new ObjectInputStream(socket.getInputStream());

        numberOfChips=1000;

        playerName=String.valueOf(socket.getLocalPort());
        new GameClientBeta().start();
        int myChoose=0;
        while (!isLost){
            if(myTurn) {                   //очередность хода:true - мой ход, false - нет
                myChoose = Integer.parseInt(inu.readLine());
                switch (myChoose) {
                    case 1:
                        myTurn = false;
                        move=new Move("rise",numberOfChips,rise(),isLost);
                        out.writeObject(move);
                        break;
                    case 2:
                        myTurn = false;
                        move=new Move("all-in",0,allIn(),isLost);
                        out.writeObject(move);
                        break;

                }
            }else try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
        System.out.println(move.name+" " + move.move+" "+move.bet+" chips left"+move.currentChips);
    }

    public void check(){
    }

    public void fold(){
    }

    public static int rise() throws IOException {
        System.out.println("ПОДНЯТЬ НА");
        int riseUp=Integer.parseInt(inu.readLine());
        numberOfChips-=riseUp;
        return riseUp;
    }

    public static int allIn() throws IOException {
        System.out.println("All-in");
        return numberOfChips;

    }

    public void call(){
    }


}

