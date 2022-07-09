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
import java.util.ArrayList;
import java.util.List;

import static server.Main.executor;

public class ServerStream {
    private Thread serverThread;
    static final Gson gson = new Gson();
    static final Controller controller = new Controller();

    private boolean isRunning = true;
    private final List<Socket> sockets = new ArrayList<>();

    ServerStream() {
        System.out.println("Server started!");
        start();
    }

    private void start() {
        try (ServerSocket serverSocket = new ServerSocket(ServerConfig.getPort(), 50,
                InetAddress.getByName(ServerConfig.getAddress()))) {
            while (isRunning) {
                try {
                    Socket socket = serverSocket.accept();
                    sockets.add(socket);
                    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                    String msg = inputStream.readUTF();
                    System.out.println("Received: " + msg);
                    CommandData commandData = gson.fromJson(msg, CommandData.class);
                    executor.execute(() -> {
                        SocketHandler handler = new SocketHandler(socket, inputStream, outputStream, commandData);
                        handler.reply();
                    });
                    if (commandData.getType().equals("exit")) {
                        isRunning = false;
                        break;
                    }
                } catch (IOException e) {
                    // ignored
                }
            }
            executor.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
