package stream;

public enum ServerInfo {
    ADDRESS("127.0.0.1"),
    PORT("8080");

    private final String value;

    ServerInfo(String value) {
        this.value = value;
    }

    public static String get(ServerInfo info) {
        return info.value;
    }

    public static String get(String info) {
        return ServerInfo.valueOf(info.toUpperCase()).value;
    }
}
