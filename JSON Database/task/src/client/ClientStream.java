package client;

import stream.ServerInfo;

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
        super(InetAddress.getByName(ServerInfo.get("address")),
                Integer.parseInt(ServerInfo.get("port")));
        System.out.println("Client started!");
        input = new DataInputStream(getInputStream());
        output = new DataOutputStream(getOutputStream());

    }

    public static void readUTF() {
        try {
            System.out.println("Received: " + instance.input.readUTF());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
