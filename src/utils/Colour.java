package utils;

public record Colour(String str) {

    public static void println(String colour, String str) {
        switch (colour) {
            case "red", "RED" -> System.out.println(new Colour("\u001B[31m" + str + "\u001B[0m"));
            case "green", "GREEN" -> System.out.println(new Colour("\u001B[32m" + str + "\u001B[0m"));
            case "yellow", "YELLOW" -> System.out.println(new Colour("\u001B[33m" + str + "\u001B[0m"));
            case "blue", "BLUE" -> System.out.println(new Colour("\u001B[34m" + str + "\u001B[0m"));
            case "purple", "PURPLE" -> System.out.println(new Colour("\u001B[35m" + str + "\u001B[0m"));
            case "cyan", "CYAN" -> System.out.println(new Colour("\u001B[36m" + str + "\u001B[0m"));
            case null, default -> System.out.println(new Colour("\u001B[37m" + str + "\u001B[0m"));
        }
    }

    public static void print(String colour, String str) {
        switch (colour) {
            case "red", "RED" -> System.out.print(new Colour("\u001B[31m" + str + "\u001B[0m"));
            case "green", "GREEN" -> System.out.print(new Colour("\u001B[32m" + str + "\u001B[0m"));
            case "yellow", "YELLOW" -> System.out.print(new Colour("\u001B[33m" + str + "\u001B[0m"));
            case "blue", "BLUE" -> System.out.print(new Colour("\u001B[34m" + str + "\u001B[0m"));
            case "purple", "PURPLE" -> System.out.print(new Colour("\u001B[35m" + str + "\u001B[0m"));
            case "cyan", "CYAN" -> System.out.print(new Colour("\u001B[36m" + str + "\u001B[0m"));
            case null, default -> System.out.print(new Colour("\u001B[37m" + str + "\u001B[0m"));
        }
    }

    @Override
    public String toString() {
        return str;
    }
}
