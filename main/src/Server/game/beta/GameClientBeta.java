package Server.game.beta;

import java.io.*;
import java.net.Socket;

public class GameClientBeta extends Thread {
    private int numberOfChips;
    private int bet;
    private int myTurn;

    private static BufferedReader inu;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;


    public static void main(String[] args) throws IOException {
        Socket socket=new Socket("localhost",12345);
        inu=new BufferedReader(new InputStreamReader(System.in));
        out=new ObjectOutputStream(socket.getOutputStream());

        in=new ObjectInputStream(socket.getInputStream());

        new GameClientBeta().start();
        int myChoose=0;
        while (myChoose!=11) {
            myChoose=Integer.parseInt(inu.readLine());
            switch (myChoose) {
                case 1:rise();
                    break;
            }
        }
    }

    @Override
    public void run() {
        Object fromServer;
        try {

            while (true){
                fromServer= in.readObject();
                print(fromServer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    synchronized void print(Object string){
        System.out.println(string);
    }

    public void check(){
    }

    public void fold(){
    }

    public static void rise() throws IOException {
        System.out.println("ПОДНЯТЬ НА");
        out.writeObject(Integer.parseInt(inu.readLine()));
    }

    public void allIn(){

    }

    public void call(){
    }
}
