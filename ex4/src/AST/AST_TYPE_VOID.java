package AST;

import TYPES.TYPE;
import TYPES.TYPE_VOID;

public class AST_TYPE_VOID extends AST_TYPE {
    public AST_TYPE_VOID(int line) {
        this.line = line;
        this.typeName = "void";

        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print("type -> TYPE_VOID \n");
    }

    public void PrintMe() {

        System.out.format("AST %s NODE\n", "TYPE_VOID");


        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "void");
    }

    public TYPE SemantMe() {
        System.out.format("TYPE_VOID" + "- semant me\n");
        return TYPE_VOID.getInstance();
    }
}