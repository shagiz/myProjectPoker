package Server.game.beta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class GameServerBeta {
    private static volatile int[] bank;
    private static ArrayList<ClientThread> clients = new ArrayList<>();
    public static boolean isGameOver=false;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(12345);
        System.out.println("Введите количество игроков:");
        Scanner sc = new Scanner(System.in);

        int members = sc.nextByte();
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
        Move send;
        boolean isActive=true;
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
                while (!isGameOver) {                  //конец игры, когда 1 !isLost
                    //сюда добавить активирование всех игроков которые не проиграли, но скинули на прошлом круге
                    oneGameLap();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //один цикл стола, до разделения банка
        public void oneGameLap() throws IOException,ClassNotFoundException{
            for (int i = 0; i < 4; i++) {
               everyPlayer();
            }
        }

        //определить очередность хода игроков, изменяя myTurn
        public void everyPlayer() throws IOException,ClassNotFoundException{
            send = (Move) input.readObject();
            System.out.println(send.name+" " + send.move+" "+send.bet);
            System.out.println("банк " + (bank[0] += send.bet));
            for (ClientThread clientThread : clients) {
                clientThread.output.writeObject(send);
            }
        }
    }

}
