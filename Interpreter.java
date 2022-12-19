import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Interpreter {

     // Generates a variable table from a given program source code.
    public Map<String, Integer> getVarsTable(String program) throws UnexpectedTokenException, UninitializedVariableException {
        Parser parser = new Parser();
        List<String> tokens = Tokenizer.tokenize(program);
        return buildVarsTable(parser.parse(tokens));
    }

    // Build a variable table from a Parse Tree.
    private Map<String, Integer> buildVarsTable(ParseTree parseTree) throws UninitializedVariableException {
        Map<String, Integer> varsTable = new HashMap<>();
        for (ASTNode node : parseTree.getChildren()) {
            String var = node.getLeft().getValue();
            int value = evaluateAST(node.getRight(), varsTable);
            varsTable.put(var, value);
        }
        return varsTable;
    }

    // Evaluate each branch of the AST.
    private int evaluateAST(ASTNode node, Map<String, Integer> varsTable) throws UninitializedVariableException {
        if (node == null) return 1;
        int leftVal = evaluateAST(node.getLeft(), varsTable);
        int rightVal = evaluateAST(node.getRight(), varsTable);

        String value = node.getValue();

        switch (value) {
            case "+":
                return leftVal + rightVal;
            case "-":
                return leftVal - rightVal;
            case "*":
                return leftVal * rightVal;
            default:
                if (Pattern.matches("^(0|-?[1-9][0-9]*)$", value)) return Integer.parseInt(value);
                else if (Pattern.matches("^([a-zA-Z_][a-zA-Z_0-9]*)$", value)) return varsTable.get(value);
                throw new UninitializedVariableException(String.format("Error: '%s' may have not been initialized", value));
        }
    }
}
