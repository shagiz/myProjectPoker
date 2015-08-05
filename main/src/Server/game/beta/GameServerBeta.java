package Server.game.beta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class GameServerBeta {
    private static int members;
    private static volatile int[] bank;
    private static ArrayList<ClientThread> clients = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(12345);
        System.out.println("Введите количество игроков:");
        Scanner sc = new Scanner(System.in);

        members=sc.nextByte();
        bank=new int[members];

        for (int i = 0; i < members; i++) {
            clients.add(new ClientThread(serverSocket.accept()));
            System.out.println("Client "+ i+ " !!!");
        }

        System.out.println("Game started!");

        for (ClientThread client:clients){
            client.start();
        }
    }
    static class ClientThread extends Thread{

        Socket client;
        int send;
        final ObjectInputStream input;
        final ObjectOutputStream output;
        ClientThread(Socket socket) throws IOException {
            this.client=socket;
            input=new ObjectInputStream(client.getInputStream());
            output=new ObjectOutputStream(client.getOutputStream());
        }

        @Override
        public void run() {
            try {
                while (true) {
                    send = (int) input.readObject();
                    System.out.println("ставка "+send);
                    System.out.println("банк "+(bank[0]+=send));
                    for (ClientThread clientThread : clients) {
                        clientThread.output.writeObject(send);
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
