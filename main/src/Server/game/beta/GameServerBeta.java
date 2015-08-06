package Server.game.beta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class GameServerBeta {
    private static boolean isGameEnd=false;
    private static boolean isRoundEnd=false;

    private static ArrayList<PlayerThread> players=new ArrayList<>();

    private static volatile Move move;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket=new ServerSocket(12345);
        System.out.println("Сервер создан: " + serverSocket);
        System.out.print("Введите количество игроков:");
        int members=new Scanner(System.in).nextByte();

        for (int i = 0; i < members; i++) {
            System.out.println("Ожидаем подключение "+(i+1)+"-ого игрока...");
            Socket socket=serverSocket.accept();
            System.out.println("Игрок подключился " + socket);
            PlayerThread newPlayer=new PlayerThread(socket);
            players.add(newPlayer);
        }

        System.out.println("------Игра началсь------");

        while (!isGameEnd){
            for (PlayerThread player:players){
                player.isActive=true;
            }
            while (!isRoundEnd){
                for (PlayerThread player:players){
                    if (player.isActive) {
                        move = (Move) player.input.readObject();
                       if (move.move.equals("fold"))player.isActive=false;
                        System.out.println(move.move+" "+move.currentChips+" "+move.bet);
                        for (PlayerThread playerThread : players) {
                            try {
                                playerThread.output.writeObject(move);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    static class PlayerThread{
        Socket client;
        String send;
        final ObjectInputStream input;
        final ObjectOutputStream output;
        PlayerThread(Socket socket) throws IOException {
            this.client=socket;
            input=new ObjectInputStream(client.getInputStream());
            output=new ObjectOutputStream(client.getOutputStream());
        }
        boolean isActive;

    }
}
