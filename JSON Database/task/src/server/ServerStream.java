package server;

import com.google.gson.Gson;
import server.commander.CommandData;
import server.commander.Controller;
import server.data.ServerConfig;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerStream {
    private static final Gson gson = new Gson();
    private static final Controller controller = new Controller();


    ServerStream() {
        System.out.println("Server started!");
        start();
    }

    private void start() {
        //start server socket and wait for client connection
        try (ServerSocket serverSocket = new ServerSocket(ServerConfig.getPort(), 50,
                InetAddress.getByName(ServerConfig.getAddress()))) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                     DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
                    String msg = inputStream.readUTF();
                    CommandData commandData = gson.fromJson(msg, CommandData.class);
                    System.out.println("Received: " + commandData.toString());
                    reply(commandData, outputStream);
                    if (commandData.getType().equals("exit")) {
                        break;
                    }
                } catch (IOException e) {
                    // ignored
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void send(String msg, DataOutputStream outputStream) {
        try {
            outputStream.writeUTF(msg);
            System.out.println("Sent: " + msg);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void reply(CommandData commandData, DataOutputStream outputStream) {
        try {
            controller.setCommand(commandData);
            String result = controller.executeCommand();
            send(result, outputStream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
