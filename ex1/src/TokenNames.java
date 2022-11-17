/*import java.util.HashMap;*/

public interface TokenNames {
    /* terminals */
    public static final int error = -1;
    public static final int LPAREN = 1;
    public static final int RPAREN = 2;
    public static final int LBRACK = 3;
    public static final int RBRACK = 4;
    public static final int LBRACE = 5;
    public static final int RBRACE = 6;
    public static final int NIL = 7;
    public static final int PLUS = 8;
    public static final int MINUS = 9;
    public static final int TIMES = 10;
    public static final int DIVIDE = 11;
    public static final int COMMA = 12;
    public static final int DOT = 13;
    public static final int SEMICOLON = 14;
    public static final int TYPE_INT = 15;
    public static final int TYPE_VOID = 16;
    public static final int ASSIGN = 17;
    public static final int EQ = 18;
    public static final int LT = 19;
    public static final int GT = 20;
    public static final int ARRAY = 21;
    public static final int CLASS = 22;
    public static final int EXTENDS = 23;
    public static final int RETURN = 24;
    public static final int WHILE = 25;
    public static final int IF = 26;
    public static final int NEW = 27;
    public static final int INT = 28;
    public static final int STRING = 29;
    public static final int ID = 30;
    public static final int TYPE_STRING = 31;

    public static final int EOF = 0;
    public static final int COMMENT = 32;

    /* opposite direction now */
    public static String numToName(int num) {
        switch (num) {
            case error:
                return "error";
            case LPAREN:
                return "LPAREN";
            case RPAREN:
                return "RPAREN";
            case LBRACK:
                return "LBRACK";
            case RBRACK:
                return "RBRACK";
            case LBRACE:
                return "LBRACE";
            case RBRACE:
                return "RBRACE";
            case NIL:
                return "NIL";
            case PLUS:
                return "PLUS";
            case MINUS:
                return "MINUS";
            case TIMES:
                return "TIMES";
            case DIVIDE:
                return "DIVIDE";
            case COMMA:
                return "COMMA";
            case DOT:
                return "DOT";
            case SEMICOLON:
                return "SEMICOLON";
            case TYPE_INT:
                return "TYPE_INT";
            case TYPE_VOID:
                return "TYPE_VOID";
            case ASSIGN:
                return "ASSIGN";
            case EQ:
                return "EQ";
            case LT:
                return "LT";
            case GT:
                return "GT";
            case ARRAY:
                return "ARRAY";
            case CLASS:
                return "CLASS";
            case EXTENDS:
                return "EXTENDS";
            case RETURN:
                return "RETURN";
            case WHILE:
                return "WHILE";
            case IF:
                return "IF";
            case NEW:
                return "NEW";
            case INT:
                return "INT";
            case STRING:
                return "STRING";
            case ID:
                return "ID";
            case TYPE_STRING:
                return "TYPE_STRING";
            case EOF:
                return "EOF";
            case COMMENT:
                return "COMMENT";
            default:
                return null;

        }
    }
}
