
// functions:
// add new text symbols and classify them
// come with prebuilt library that is easy to use

public class Main {

    public static void main(String[] args) {
        printWelcome();
        System.out.println(getIcon("a"));
    }

    public static void printWelcome () {
        System.out.println("welcome to face chooser");
    }

    public static String getIcon (String emote) {
        System.out.println("getIcon is running");
        return ":)";
    }
}
