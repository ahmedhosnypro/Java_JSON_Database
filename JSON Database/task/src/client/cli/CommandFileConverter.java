package client.cli;

import com.beust.jcommander.IStringConverter;
import com.google.common.io.Files;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

//implementation group: 'com.google.guava', name: 'guava', version: '31.1-jre'

public class CommandFileConverter implements IStringConverter<CommandArgs> {
    @Override
    public CommandArgs convert(String value) {
        String path = "src/client/data/" + value;
        Gson gson = new Gson();
        try {
            return gson.fromJson(Files.toString(new File(path), StandardCharsets.UTF_8), CommandArgs.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
