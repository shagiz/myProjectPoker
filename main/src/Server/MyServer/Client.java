package Server.MyServer;

import java.io.*;
import java.net.Socket;


public class Client extends Thread{
    static ObjectOutputStream out;
    static ObjectInputStream in;
    static BufferedReader inu;

    public static void main(String[] args) throws IOException {
        Socket socket=new Socket("localhost",12345);
        inu=new BufferedReader(new InputStreamReader(System.in));
        out=new ObjectOutputStream(socket.getOutputStream());
        in=new ObjectInputStream(socket.getInputStream());
        new Client().start();
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
}
