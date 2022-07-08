package client;

import client.cli.CommandArgs;
import com.beust.jcommander.JCommander;

public class Main {

    public static void main(String[] args) {
        CommandArgs command = new CommandArgs();
        JCommander.newBuilder()
                .addObject(command)
                .build()
                .parse(args);
        ClientStream.send(command);
        ClientStream.receive();
    }
}
