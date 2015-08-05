package Server.game.beta;

import java.io.*;
import java.net.Socket;

public class GameClientBeta extends Thread {
    private int numberOfChips;
    private int bet;
    private int myTurn;

    static ObjectInputStream in;


    public static void main(String[] args) throws IOException {
        Socket socket=new Socket("localhost",12345);
        BufferedReader inu=new BufferedReader(new InputStreamReader(System.in));
        ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());

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

    public void check(){
    }

    public void fold(){
    }

    public void rise(){
    }

    public void allIn(){

    }

    public void call(){
    }
}
