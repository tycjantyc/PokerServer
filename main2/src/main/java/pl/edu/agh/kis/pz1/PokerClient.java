package pl.edu.agh.kis.pz1;


import java.io.*;
import java.util.Scanner;
import java.net.Socket;

public class PokerClient {



    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    BufferedWriter bufferedWriter;


    public PokerClient(String serverAddress) throws Exception {

        socket = new Socket(serverAddress, 9000);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
        //bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

    }


    public void play() throws Exception {
        try {
            String response = in.nextLine();

            while (in.hasNextLine()) {
                response = in.nextLine();

                if (response.startsWith("MESSAGE")) {
                    System.out.println(response.substring(8));

                } else if (response.startsWith("OTHER_PLAYER_LEFT")) {
                    System.out.println("Other player left");
                    break;
                }
            }
            out.println("QUIT");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
    public void sendMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);
                while (socket.isConnected()) {
                    String message = scanner.nextLine();
                    out.println(message);
                }
            }
        }).start();
    }
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Pass the server IP as the sole command line argument");
            return;
        }
        PokerClient client = new PokerClient(args[0]);
        client.sendMessage();
        client.play();

    }
}
