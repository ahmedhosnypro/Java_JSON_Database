package client.cli;

import com.beust.jcommander.IStringConverter;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

//implementation group: 'com.google.guava', name: 'guava', version: '31.1-jre'

public class CommandFileConverter implements IStringConverter<String> {
    @Override
    public String convert(String value) {
        String path = "src/client/data/" + value;
        String path1 = "JSON Database/task/src/client/data/" + value;
        try {
            return Files.toString(new File(path1), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
