package client.cli;

import com.beust.jcommander.*;

import java.util.List;
import java.util.Objects;

@Parameters(separators = " ", commandDescription = "server.serialization.Command line interface for JSON Database")
public class CommandArgs {
    @Parameter(names = "-t", description = "Type of the command, get, set, deleted exit", required = true, validateWith = CommandArgsValidator.class)
    private String type;
    @Parameter(names = "-k", description = "Index of the element to be edited in the array", validateWith = CommandArgsValidator.class)
    private String key;

    @Parameter(names = "-v", description = "value of the element to be edited in the array", variableArity = true, validateWith = CommandArgsValidator.class)
    private String value;

    @Override
    public String toString() {
        return type +
                (Objects.nonNull(key) ? " " + key : "") +
                (Objects.nonNull(value) ? " " + value : "");
    }
}
