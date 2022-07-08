package server.commander;

public enum CommandType {
    GET("get"), SET("set"), DELETE("delete"), EXIT("exit");
    private final String notation;

    CommandType(String notation) {
        this.notation = notation;
    }


    @Override
    public String toString() {
        return notation;

    }
}
