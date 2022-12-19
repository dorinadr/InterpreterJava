import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                final String arg = args[0];
                if (arg.compareTo("-console") == 0) {
                    Scanner scanner = new Scanner(System.in);
                    String srcCode = writeSourceCode(scanner);
                    printVarsTable(srcCode);
                } else {
                    throw new Exception(String.format("Command unknown: '%s'.", args[0].replaceFirst("^(-)+", "")));
                }
            } else {
                System.out.println("No arguments entered\nRunning default script...\ntest.toy");
                System.out.println("\n\nInput 1:");
                String input1 = "x = 001;";
                System.out.println(input1);
                try {
                    printVarsTable(input1);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                String input2 = "x_2 = 0;";
                System.out.println(input2);
                try {
                    printVarsTable(input2);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                System.out.println("\n\nInput 3");
                String input3 = "x = 0\n" +
                        "y = x;\n" +
                        "z = ---(x + y);";
                System.out.println(input3);
                try {
                    printVarsTable(input3);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                System.out.println("\n\nInput 4:");
                String input4 = "x = 1;\n" +
                        "y = 2;\n" +
                        "z = ---(x + y) * (x + -y);";
                System.out.println(input4);
                try {
                    printVarsTable(input4);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void printVarsTable(String srcCode) throws UnexpectedTokenException, UninitializedVariableException {
        Interpreter interpreter = new Interpreter();
        Map<String, Integer> varsTable = interpreter.getVarsTable(srcCode);
        // Print variables and their values
        System.out.println();
        System.out.println("printing variables table...");
        for (String var : varsTable.keySet()) {
            System.out.println(String.format("%s = %d", var, varsTable.get(var)));
        }
    }

    // Register user input as source code.
    private static String writeSourceCode(Scanner scanner) {
        StringBuilder sb = new StringBuilder();
        String line = "";
        System.out.println("Type '$end' then press <ENTER> to finish");
        line = scanner.nextLine();
        while (line.compareTo("$end") != 0) {
            sb.append(line);
            line = scanner.nextLine();
        }
        return sb.toString();
    }
}
