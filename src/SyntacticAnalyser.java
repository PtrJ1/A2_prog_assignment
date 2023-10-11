import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyntacticAnalyser {

	public enum NonTerminalSymbol implements Symbol {
		PROG, LOS, STAT, WHILE, FOR, FOR_START, FOR_ARITH, IF, ELSE_IF, ELSE_IF_POSS,
		ASSIGN, DECL, POSS_ASSIGN, PRINT, TYPE, EXPR, CHAR_EXPR, BOOL_EXPR,
		BOOL_OP, BOOL_EQ, BOOL_LOG, REL_EXPR, REL_EXPR_PRIME, REL_OP,
		ARITH_EXPR, ARITH_EXPR_PRIME, TERM, TERM_PRIME, FACTOR, PRINT_EXPR;

		@Override
		public boolean isVariable() {
			return true;
		}
	}




	private static final Map<Pair<Symbol, Symbol>, List<Symbol>> parsingTable = new HashMap<>();

    static {
        // Fill the parsing table based on the given grammar.

        // prog → public class ID { public static void main ( String[] args ) { los } }
        parsingTable.put(new Pair<>(NonTerminalSymbol.PROG, Token.TokenType.PUBLIC), Arrays.asList(
                Token.TokenType.PUBLIC, Token.TokenType.CLASS, Token.TokenType.ID, Token.TokenType.LBRACE,
                Token.TokenType.PUBLIC, Token.TokenType.STATIC, Token.TokenType.VOID, Token.TokenType.MAIN,
                Token.TokenType.LPAREN, Token.TokenType.STRINGARR, Token.TokenType.ARGS, Token.TokenType.RPAREN,
                Token.TokenType.LBRACE, NonTerminalSymbol.LOS, Token.TokenType.RBRACE, Token.TokenType.RBRACE));

		/*
		// <<los>> → <<stat>> <<los>> | ε
		parsingTable.put(new Pair<>(NonTerminalSymbol.LOS, Token.TokenType.WHILE), Arrays.asList(NonTerminalSymbol.STAT, NonTerminalSymbol.LOS));
		parsingTable.put(new Pair<>(NonTerminalSymbol.LOS, Token.TokenType.FOR), Arrays.asList(NonTerminalSymbol.STAT, NonTerminalSymbol.LOS));
		parsingTable.put(new Pair<>(NonTerminalSymbol.LOS, Token.TokenType.IF), Arrays.asList(NonTerminalSymbol.STAT, NonTerminalSymbol.LOS));
		parsingTable.put(new Pair<>(NonTerminalSymbol.LOS, Token.TokenType.ID), Arrays.asList(NonTerminalSymbol.STAT, NonTerminalSymbol.LOS));
		parsingTable.put(new Pair<>(NonTerminalSymbol.LOS, Token.TokenType.TYPE), Arrays.asList(NonTerminalSymbol.STAT, NonTerminalSymbol.LOS));
		parsingTable.put(new Pair<>(NonTerminalSymbol.LOS, Token.TokenType.PRINT), Arrays.asList(NonTerminalSymbol.STAT, NonTerminalSymbol.LOS));
		parsingTable.put(new Pair<>(NonTerminalSymbol.LOS, Token.TokenType.SEMICOLON), Arrays.asList(NonTerminalSymbol.STAT, NonTerminalSymbol.LOS));
		parsingTable.put(new Pair<>(NonTerminalSymbol.LOS, Token.TokenType.RBRACE), Arrays.asList());  // ε production

		// <<stat>> → <<while>> | <<for>> | <<if>> | <<assign>> ; | <<decl>> ; | <<print>> ; | ;
		parsingTable.put(new Pair<>(NonTerminalSymbol.STAT, Token.TokenType.WHILE), Arrays.asList(NonTerminalSymbol.WHILE));
		parsingTable.put(new Pair<>(NonTerminalSymbol.STAT, Token.TokenType.FOR), Arrays.asList(NonTerminalSymbol.FOR));
		parsingTable.put(new Pair<>(NonTerminalSymbol.STAT, Token.TokenType.IF), Arrays.asList(NonTerminalSymbol.IF));
		parsingTable.put(new Pair<>(NonTerminalSymbol.STAT, Token.TokenType.ID), Arrays.asList(NonTerminalSymbol.ASSIGN, Token.TokenType.SEMICOLON));
		parsingTable.put(new Pair<>(NonTerminalSymbol.STAT, Token.TokenType.TYPE), Arrays.asList(NonTerminalSymbol.DECL, Token.TokenType.SEMICOLON));
		parsingTable.put(new Pair<>(NonTerminalSymbol.STAT, Token.TokenType.PRINT), Arrays.asList(NonTerminalSymbol.PRINT, Token.TokenType.SEMICOLON));
		parsingTable.put(new Pair<>(NonTerminalSymbol.STAT, Token.TokenType.SEMICOLON), Arrays.asList(Token.TokenType.SEMICOLON));

		// <<while>> → while ( <<rel expr>> <<bool expr>> ) { <<los>> }
		parsingTable.put(new Pair<>(NonTerminalSymbol.WHILE, Token.TokenType.WHILE), Arrays.asList(
				Token.TokenType.WHILE, Token.TokenType.LPAREN, NonTerminalSymbol.REL_EXPR, NonTerminalSymbol.BOOL_EXPR, Token.TokenType.RPAREN,
				Token.TokenType.LBRACE, NonTerminalSymbol.LOS, Token.TokenType.RBRACE));

		*/
        // ... Similar entries for other rules ...


    }

	

	public static ParseTree parse(List<Token> tokens) throws SyntaxException {

		
		// Print the entire parsingTable
		for (Map.Entry<Pair<Symbol, Symbol>, List<Symbol>> entry : parsingTable.entrySet()) {
			Pair<Symbol, Symbol> key = entry.getKey();
			List<Symbol> value = entry.getValue();
			
			System.out.println("Key: " + key + " -> Value: " + value);
		}

		Deque<Pair<Symbol, TreeNode>> stack = new ArrayDeque<>();
        TreeNode rootNode = new TreeNode(toTreeNodeLabel(SyntacticAnalyser.NonTerminalSymbol.PROG), null);
        stack.push(new Pair<>(SyntacticAnalyser.NonTerminalSymbol.PROG, rootNode));

        for (Token token : tokens) {
            Pair<Symbol, TreeNode> top = stack.peek();

			        // Diagnostic print statements:
			System.out.println("Current Token: " + token);
			System.out.println("Top of Stack: " + top);

            if (top == null) {
                throw new SyntaxException("Unexpected token: " + token);
            }

            if (top.fst() instanceof Token.TokenType) {
                // Terminal on top of stack
                if (token.getType() == top.fst()) {
                    stack.pop();
                } else {
                    throw new SyntaxException("Expected token: " + top.fst() + ", but got: " + token);
                }
            } else {
                // Non-terminal on top of stack
                List<Symbol> production = parsingTable.get(new Pair<>(top.fst(), token.getType()));
                if (production == null) {
                    throw new SyntaxException("Unexpected token: " + token);
                }
                stack.pop();
                for (int i = production.size() - 1; i >= 0; i--) {
                    TreeNode childNode = new TreeNode(toTreeNodeLabel(production.get(i)), top.snd());
                    top.snd().addChild(childNode);
                    stack.push(new Pair<>(production.get(i), childNode));
                }
            }
        }

        if (!stack.isEmpty()) {
            throw new SyntaxException("Unfinished parsing. Remaining stack: " + stack);
        }

        return new ParseTree(rootNode);
	}

	private static TreeNode.Label toTreeNodeLabel(Symbol symbol) {
		if (symbol instanceof Token.TokenType) {
			// For all terminal symbols, return a generic terminal label
			return TreeNode.Label.terminal;
		} else {
			// For non-terminal symbols, convert to lowercase
			return TreeNode.Label.valueOf(symbol.toString().toLowerCase());
		}
	}

}

// The following class may be helpful.

class Pair<A, B> {
	private final A a;
	private final B b;

	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}

	public A fst() {
		return a;
	}

	public B snd() {
		return b;
	}

	@Override
	public int hashCode() {
		return 3 * a.hashCode() + 7 * b.hashCode();
	}

	@Override
	public String toString() {
		return "{" + a + ", " + b + "}";
	}

	@Override
	public boolean equals(Object o) {
		if ((o instanceof Pair<?, ?>)) {
			Pair<?, ?> other = (Pair<?, ?>) o;
			return other.fst().equals(a) && other.snd().equals(b);
		}

		return false;
	}



}
