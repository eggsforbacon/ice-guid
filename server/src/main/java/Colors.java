public enum Colors {
    ANSI_RESET(0, "\u001B[0m"),
    ANSI_RED(1, "\u001B[31m"),
    ANSI_BRIGHT_RED(2, "\u001B[91m"),
    ANSI_GREEN(3, "\u001B[32m"),
    ANSI_YELLOW(4, "\u001B[33m"),
    ANSI_BOLD(5, "\u001B[1m"),
    ANSI_UNBOLD(6, "\u001B[21m");

    final int index;
    final String value;

    Colors(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    public int index() {
        return this.index;
    }
}
