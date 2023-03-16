package AST;

import SYMBOL_TABLE.SYMBOL_TABLE;
import TEMP.TEMP;
import TYPES.TYPE;
import TYPES.TYPE_ARRAY;
import TYPES.TYPE_NIL;
import TYPES.TYPE_VOID;

public class AST_ATD extends AST_Node {
    public AST_TYPE type;
    public String id;

    public AST_ATD(String id, AST_TYPE type, int line) {
        this.type = type;
        this.id = id;
        this.line = line;

        System.out.format("arrayTypedef ::= array %s = type[]; \n", id);

        SerialNumber = AST_Node_Serial_Number.getFresh();
    }

    public void PrintMe() {
        /*print node*/
        System.out.format("AST ATD NODE\n");

        if (type != null) type.PrintMe();

        /*print to ast*/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("ATD(%s)", id));

        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);

    }


    public TYPE SemantMe() {
        System.out.println("ATD" + " - semantme");

        TYPE typeType = type.SemantMe();

        /*array type def must be global*/
        if (!(SYMBOL_TABLE.getInstance().getScope().equals("global"))) {
            System.out.format(">> ERROR [%d] array declared not in the global scope\n", line);
            printError(line);
        }

        /*array cannot be of voids,nils, or null*/
        if (typeType == null || typeType instanceof TYPE_VOID || typeType instanceof TYPE_NIL) {
            System.out.format(">> ERROR [%d] non existing type\n", line);
            printError(line);
        }

        /*check declaration*/
        if (SYMBOL_TABLE.getInstance().find(id) != null) {
            System.out.format(">> ERROR [%d] %s is already declared.\n", line, id);
            printError(line);
        }

        /*push to SYMBOLTABLE*/
        TYPE_ARRAY thisTYPE = new TYPE_ARRAY(typeType, id);
        SYMBOL_TABLE.getInstance().enter(id, thisTYPE);

        return thisTYPE;
    }

    public TEMP IRme() {
        System.out.println("ATD - IRme");
        return null;
    }
}