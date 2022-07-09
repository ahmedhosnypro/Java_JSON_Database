package client;

import client.cli.CommandArgs;
import com.beust.jcommander.JCommander;

public class Main {

    public static void main(String[] args) {
        CommandArgs commandArgs = new CommandArgs();
        JCommander.newBuilder()
                .addObject(commandArgs)
                .build()
                .parse(args);
        ClientStream.send(commandArgs.toString());
        ClientStream.receive();
    }
}
