package AST;

import TYPES.TYPE;
import TYPES.TYPE_INT;

public class AST_TYPE_INT extends AST_TYPE {

    public AST_TYPE_INT(int line) {
        this.line = line;
        this.typeName = "int";
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print("type -> TYPE_INT \n");
    }

    public void PrintMe() {

        System.out.format("AST %s NODE\n", "TYPE_INT");

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "int");
    }

    public TYPE SemantMe() {
        System.out.format("TYPE_INT" + "- semant me\n");
        return TYPE_INT.getInstance();
    }
}