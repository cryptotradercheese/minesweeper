import java.util.*;
import java.util.regex.*;

class Main {
    public static void main(String[] args) {
        // put your code here
        final Scanner scanner = new Scanner(System.in);
        String input = "";
        while (scanner.hasNextLine()) {
            input += scanner.nextLine();
        }
        scanner.close();
        parse(input);

        Matcher matcher = Pattern.compile("(?i)<(.+?)>.*</\\1>").matcher(input);
        System.out.println(matcher.find());
        String content = matcher.group(0);
        System.out.println(content);
    }

    static void parse(String input) {

    }
}

class HTMLParser {

    void parse(String input) {
        String content = Pattern.compile("(?i)<(.+?)>.*</\\1>").matcher(input).group();
    }
}