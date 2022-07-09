package server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static ExecutorService executor = Executors.newCachedThreadPool();
    public static void main(String[] args) {
        new ServerStream();
    }
}
