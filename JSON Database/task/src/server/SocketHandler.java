package server;

import server.commander.CommandData;
import server.commander.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class SocketHandler {
    private final Socket socket;
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private final CommandData commandData;

    private final Controller controller = new Controller();

    public SocketHandler(Socket socket, DataInputStream inputStream, DataOutputStream outputStream, CommandData commandData) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.commandData = commandData;
    }

    void reply() {
        try {
            controller.setCommand(commandData);
            String result = controller.executeCommand();
            send(result);
            socket.close();
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void send(String msg) {
        try {
            outputStream.writeUTF(msg);
            System.out.println("Sent: " + msg);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}