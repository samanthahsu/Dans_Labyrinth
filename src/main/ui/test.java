package ui;

import java.util.regex.Pattern;

public class test {

    public static void main(String[] args) {
        System.out.println(Pattern.matches("help", "me|help|quit|map"));
    }
}
