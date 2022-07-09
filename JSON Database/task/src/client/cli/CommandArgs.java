package client.cli;

import com.beust.jcommander.*;
import com.google.gson.Gson;

import java.util.Objects;

@Parameters(separators = " ", commandDescription = "server.serialization.Command line interface for JSON Database")
public class CommandArgs {
    @Parameter(names = "-in", converter = CommandFileConverter.class, description = "Input file")
    private String command;
    @Parameter(names = "-t", description = "Type of the command, get, set, deleted exit", validateWith = CommandArgsValidator.class)
    private String type;
    @Parameter(names = "-k", description = "Index of the element to be edited in the array", validateWith = CommandArgsValidator.class)
    private String key;

    @Parameter(names = "-v", description = "value of the element to be edited in the array", variableArity = true, validateWith = CommandArgsValidator.class)
    private String value;

    @Override
    public String toString() {
        if (Objects.nonNull(command)) {
            return command;
        } else {
            Gson gson = new Gson();
            return gson.toJson(new Command(type, key, value));
        }
    }
}
