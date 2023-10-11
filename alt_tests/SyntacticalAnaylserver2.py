public class SyntacticAnalyser {

    private static List<Token> tokens;
    private static int currentToken;

    public static ParseTree parse(List<Token> tokens) throws SyntaxException {

        if(tokens.isEmpty()) {
            throw new SyntaxException("Empty input provided");
        }
        SyntacticAnalyser.tokens = tokens;
        currentToken = 0;

        TreeNode root = prog();



        if (currentToken < tokens.size()) {
            throw new SyntaxException("Unexpected token: " + tokens.get(currentToken));
        }

        return new ParseTree(root);
    }

    private static TreeNode prog() throws SyntaxException {
        TreeNode node = new TreeNode(TreeNode.Label.prog, null);

        consume(node, Token.TokenType.PUBLIC);
        consume(node, Token.TokenType.CLASS);
        consume(node, Token.TokenType.ID);
        consume(node, Token.TokenType.LBRACE);
        consume(node, Token.TokenType.PUBLIC);
        consume(node, Token.TokenType.STATIC);
        consume(node, Token.TokenType.VOID);
        consume(node, Token.TokenType.MAIN);
        consume(node, Token.TokenType.LPAREN);
        consume(node, Token.TokenType.STRINGARR);
        consume(node, Token.TokenType.ARGS);
        consume(node, Token.TokenType.RPAREN);
        consume(node, Token.TokenType.LBRACE);
        TreeNode losNode = los();
        node.addChild(losNode);
        consume(node, Token.TokenType.RBRACE);
        consume(node, Token.TokenType.RBRACE);

        return node;
    }

    
    private static TreeNode los() throws SyntaxException {
        TreeNode node = new TreeNode(TreeNode.Label.los, null);

        if (tokens.get(currentToken).getType() == Token.TokenType.RBRACE) {
            TreeNode epsilonNode = new TreeNode(TreeNode.Label.epsilon, node);
            node.addChild(epsilonNode);
            return node;
        }

        TreeNode statNode = stat();
        node.addChild(statNode);

        TreeNode losNode = los();
        node.addChild(losNode);

        return node;
    }

    private static TreeNode stat() throws SyntaxException {
        // Stub: return an empty stat node or throw an exception
        throw new UnsupportedOperationException("stat() not implemented yet");
    }

    /*

    
    private static TreeNode stat() throws SyntaxException {
        TreeNode node = new TreeNode(TreeNode.Label.stat, null);

        Token.TokenType type = tokens.get(currentToken).getType();
        switch (type) {
            case WHILE:
                TreeNode whileNode = whileStat();
                node.addChild(whileNode);
                break;
            case FOR:
                TreeNode forNode = forStat();
                node.addChild(forNode);
                break;
            case IF:
                TreeNode ifNode = ifStat();
                node.addChild(ifNode);
                break;
            case ID:
                if (tokens.get(currentToken + 1).getType() == Token.TokenType.ASSIGN) {
                    TreeNode assignNode = assign();
                    node.addChild(assignNode);
                    consume(node, Token.TokenType.SEMICOLON);
                } else {
                    // Handle other cases related to ID, like function calls, etc.
                }
                break;
            case TYPE:
                TreeNode declNode = decl();
                node.addChild(declNode);
                consume(node, Token.TokenType.SEMICOLON);
                break;
            case SEMICOLON:
                consume(node, Token.TokenType.SEMICOLON);
                break;
            default:
                throw new SyntaxException("Unexpected token in stat: " + tokens.get(currentToken));
        }

        return node;
    }

    private static TreeNode whileStat() throws SyntaxException {
        TreeNode node = new TreeNode(TreeNode.Label.whilestat, null);
        consume(node, Token.TokenType.WHILE);
        consume(node, Token.TokenType.LPAREN);
        TreeNode relExprNode = relExpr();
        node.addChild(relExprNode);
        TreeNode boolExprNode = boolExpr();
        node.addChild(boolExprNode);
        consume(node, Token.TokenType.RPAREN);
        consume(node, Token.TokenType.LBRACE);
        TreeNode losNode = los();
        node.addChild(losNode);
        consume(node, Token.TokenType.RBRACE);
        return node;
    }

    private static TreeNode forStat() throws SyntaxException {
        TreeNode node = new TreeNode(TreeNode.Label.forstat, null);
        consume(node, Token.TokenType.FOR);
        consume(node, Token.TokenType.LPAREN);
        TreeNode forStartNode = forStart();
        node.addChild(forStartNode);
        consume(node, Token.TokenType.SEMICOLON);
        TreeNode relExprNode = relExpr();
        node.addChild(relExprNode);
        TreeNode boolExprNode = boolExpr();
        node.addChild(boolExprNode);
        consume(node, Token.TokenType.SEMICOLON);
        TreeNode forArithNode = forArith();
        node.addChild(forArithNode);
        consume(node, Token.TokenType.RPAREN);
        consume(node, Token.TokenType.LBRACE);
        TreeNode losNode = los();
        node.addChild(losNode);
        consume(node, Token.TokenType.RBRACE);
        return node;
    }

    private static TreeNode ifStat() throws SyntaxException {
        TreeNode node = new TreeNode(TreeNode.Label.ifstat, null);
        consume(node, Token.TokenType.IF);
        consume(node, Token.TokenType.LPAREN);
        TreeNode relExprNode = relExpr();
        node.addChild(relExprNode);
        TreeNode boolExprNode = boolExpr();
        node.addChild(boolExprNode);
        consume(node, Token.TokenType.RPAREN);
        consume(node, Token.TokenType.LBRACE);
        TreeNode losNode = los();
        node.addChild(losNode);
        consume(node, Token.TokenType.RBRACE);
        TreeNode elseIfNode = elseIf();
        node.addChild(elseIfNode);
        return node;
    }

    private static TreeNode assign() throws SyntaxException {
        TreeNode node = new TreeNode(TreeNode.Label.assign, null);
        consume(node, Token.TokenType.ID);
        consume(node, Token.TokenType.ASSIGN);
        TreeNode exprNode = expr();
        node.addChild(exprNode);
        return node;
    }

    */

    private static TreeNode consume(TreeNode parent, Token.TokenType expected) throws SyntaxException {
        if (tokens.get(currentToken).getType() == expected) {
            TreeNode node = new TreeNode(TreeNode.Label.terminal, tokens.get(currentToken), parent);
            parent.addChild(node);
            currentToken++;
            return node;
        } else {
            throw new SyntaxException("Expected: " + expected + ", but found: " + tokens.get(currentToken).getType());
        }
    }
}
