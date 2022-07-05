package server;

public class Data {
    private Data() {
    }

    private static final String[] DATABASE = new String[100];

    public static String getCellData(int index) {
        if (index >= DATABASE.length || index < 0) {
            return null;
        }
        return DATABASE[index];
    }

    public static boolean setCell(int index, String value) {
        if (index >= DATABASE.length || index < 0) {
            return false;
        }
        DATABASE[index] = value;
        return true;
    }

    public static boolean deleteCell(int index) {
        if (index >= DATABASE.length || index < 0) {
            return false;
        }
        DATABASE[index] = null;
        return true;
    }
}
