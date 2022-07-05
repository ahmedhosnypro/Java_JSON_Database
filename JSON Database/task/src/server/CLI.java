package server;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CLI {
    private CLI() {
    }

    private static final Scanner scanner = new Scanner(System.in);
    private static final String ERROR = "ERROR";
    private static final String SET_REGEX = "set\\s+\\d+";
    private static final Pattern SET_PATTERN = Pattern.compile(SET_REGEX, Pattern.CASE_INSENSITIVE);

    public static void start() {
        while (true) {
            runCommand();
        }
    }

    private static void runCommand() {
        var input = scanner.nextLine().trim();
        var tokens = ArgumentTokenizer.tokenize(input);

        if (tokens.size() == 1) {
            if (tokens.get(0).equals("exit")) {
                System.exit(0);
            } else {
                System.out.println(ERROR);
            }
        } else if (tokens.size() > 1) {

            try {
                Integer.parseInt(tokens.get(1));
            } catch (NumberFormatException e) {
                System.out.println(ERROR);
                return;
            }

            int index = Integer.parseInt(tokens.get(1)) - 1;

            if (tokens.size() > 2 && tokens.get(0).equals("set")) {
                set(index, input);
                return;
            }

            switch (tokens.get(0)) {
                case "get" -> get(index);
                case "delete" -> delete(index);
                default -> System.out.println(ERROR);
            }
        }
    }

    private static void get(int index) {
        String cell = Data.getCellData(index);
        if (cell == null) {
            System.out.println(ERROR);
            return;
        }
        System.out.println(cell);
    }

    private static void set(int index, String value) {
        Matcher matcher = SET_PATTERN.matcher(value);

        int beginIndex = -1;
        if (matcher.find()) {
            beginIndex = matcher.end() + 1;
        }
        String text = value.substring(beginIndex);
        if (Data.setCell(index, text)) {
            System.out.println("OK");
        } else {
            System.out.println(ERROR);
        }
    }

    private static void delete(int index) {
        if (Data.deleteCell(index)) {
            System.out.println("OK");
        } else {
            System.out.println(ERROR);
        }
    }
}
