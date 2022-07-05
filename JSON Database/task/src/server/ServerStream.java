package server;

import stream.ServerInfo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerStream {
    private static ServerStream instance;
    private final DataInputStream input;
    private final DataOutputStream output;


    private ServerStream() throws IOException {
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(ServerInfo.get("port")),
                50, InetAddress.getByName(ServerInfo.get("address")));
        System.out.println("Server started!");
        Socket socket = serverSocket.accept();
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
    }

    public static boolean readUTF() {
        initialize();
        try {
            String msg = instance.input.readUTF();
            System.out.println("Received: " + msg);
            response(msg);
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static void writeUTF(String msg) {
        initialize();
        try {
            instance.output.writeUTF(msg);
            System.out.println("Sent: " + msg);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void response(String msg) {
        try {
            var recordNum = msg.split("Give me a record # ")[1];
            writeUTF("A record # " + recordNum + " was sent!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void initialize() {
        if (instance == null) {
            try {
                instance = new ServerStream();
            } catch (IOException e) {
                System.out.println("ERROR");
            }
        }
    }
}
