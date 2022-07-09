package client;

import server.data.ServerConfig;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientStream extends Socket {
    private static ClientStream instance;
    private final DataInputStream input;
    private final DataOutputStream output;


    private ClientStream() throws IOException {
        super(InetAddress.getByName(ServerConfig.getAddress()),
                ServerConfig.getPort());
        System.out.println("Client started!");
        input = new DataInputStream(getInputStream());
        output = new DataOutputStream(getOutputStream());

    }

    public static void receive() {
        try {
            System.out.println("Received: " + instance.input.readUTF());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void send(String commandArgs) {
        initialize();
        try {
            instance.output.writeUTF(commandArgs);
            System.out.println("Sent: " + commandArgs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void initialize() {
        if (instance == null) {
            try {
                instance = new ClientStream();
            } catch (IOException e) {
                System.out.println("ERROR");
            }
        }
    }
}
