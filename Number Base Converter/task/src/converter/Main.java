package converter;

import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        String currentCommand1 = "";
        while (!currentCommand1.equals("/exit")) {
            currentCommand1 = takeUserCommand1(scanner);
            if (!Objects.equals(currentCommand1, "/exit")) {
                int baseFrom = Integer.parseInt(currentCommand1.split(" ")[0]);
                int baseTo = Integer.parseInt(currentCommand1.split(" ")[1]);
                String currentCommand2 = "";
                while (!currentCommand2.equals("/back")) {
                    currentCommand2 = takeUserCommand2(scanner, baseFrom, baseTo);
                    if (!Objects.equals(currentCommand2, "/back")) {
                        System.out.println(Converter.convert(baseFrom, baseTo, currentCommand2));
                    }
                }
            }
        }
    }

    /**
     * The possible commands are /from, /to, and /exit
     *
     * @return console input
     */
    public static String takeUserCommand1(Scanner sc) {
        String OUTPUT_PROMPT = "Enter two numbers in format: {source base} {target base} (To quit type /exit) ";

        System.out.print(OUTPUT_PROMPT);
        return sc.nextLine();
    }

    /**
     * The possible commands are /from, /to, and /exit
     *
     * @return console input
     */
    public static String takeUserCommand2(Scanner sc, int baseFrom, int baseTo) {
        String OUTPUT_PROMPT = String.format(
                "Enter number in base %d to convert to base %d (To go back type /back) ",
                baseFrom,
                baseTo
        );
        System.out.print(OUTPUT_PROMPT);
        return sc.nextLine();
    }
}
