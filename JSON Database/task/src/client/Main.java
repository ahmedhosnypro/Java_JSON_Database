package client;

public class Main {

    public static void main(String[] args) {
        ClientStream.writeUTF("Give me a record # 12");
        ClientStream.readUTF();
    }
}
