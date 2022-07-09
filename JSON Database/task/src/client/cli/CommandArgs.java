package client.cli;

import com.beust.jcommander.*;

import java.util.Objects;

@Parameters(separators = " ", commandDescription = "server.serialization.Command line interface for JSON Database")
public class CommandArgs {
    @Parameter(names = "-in", converter = CommandFileConverter.class, description = "Input file")
    private CommandArgs command;
    @Parameter(names = "-t", description = "Type of the command, get, set, deleted exit", validateWith = CommandArgsValidator.class)
    private String type;
    @Parameter(names = "-k", description = "Index of the element to be edited in the array", validateWith = CommandArgsValidator.class)
    private String key;

    @Parameter(names = "-v", description = "value of the element to be edited in the array", variableArity = true, validateWith = CommandArgsValidator.class)
    private String value;

    public String getType() {
        if (Objects.nonNull(command)) {
            return command.getType();
        }
        return type;
    }

    public String getKey() {
        if (Objects.nonNull(command)) {
            return command.getKey();
        }
        return key;
    }

    public String getValue() {
        if (Objects.nonNull(command)) {
            return command.getValue();
        }
        return value;
    }

    @Override
    public String toString() {
        if (Objects.nonNull(command)) {
            return command.type +
                    (Objects.nonNull(command.key) ? " " + command.key : "") +
                    (Objects.nonNull(command.value) ? " " + command.value : "");
        } else {
            return type +
                    (Objects.nonNull(key) ? " " + key : "") +
                    (Objects.nonNull(value) ? " " + value : "");
        }
    }
}
