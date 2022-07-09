package client;

import client.cli.Command;
import client.cli.CommandArgs;
import com.beust.jcommander.JCommander;

public class Main {

    public static void main(String[] args) {
        CommandArgs commandArgs = new CommandArgs();
        JCommander.newBuilder()
                .addObject(commandArgs)
                .build()
                .parse(args);
        Command command = new Command(commandArgs.getType(), commandArgs.getKey(), commandArgs.getValue());
        ClientStream.send(command);
        ClientStream.receive();
    }
}
