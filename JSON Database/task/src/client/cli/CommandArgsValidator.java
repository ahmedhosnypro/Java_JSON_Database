package client.cli;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class CommandArgsValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        if (name.equals("-t")) {
            if (!value.equalsIgnoreCase("get") &&
                    !value.equalsIgnoreCase("set") &&
                    !value.equalsIgnoreCase("delete") &&
                    !value.equalsIgnoreCase("exit")) {
                throw new ParameterException("Invalid command type");
            }
        }
        if (name.equals("-i")) {
            try {
                int index = Integer.parseInt(value);
                if (index < 0) {
                    throw new ParameterException("Index must be positive");
                }
            } catch (NumberFormatException e) {
                throw new ParameterException("Index must be a number");
            }
        }
        if (name.equals("-m")) {
            if (value.equals("")) {
                throw new ParameterException("Value must be specified");
            }
        }
    }
}
