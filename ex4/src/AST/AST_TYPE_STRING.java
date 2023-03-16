package AST;

import TYPES.TYPE;
import TYPES.TYPE_STRING;

public class AST_TYPE_STRING extends AST_TYPE {

    public AST_TYPE_STRING(int line) {

        this.line = line;
        this.typeName = "string";

        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print("type -> TYPE_STRING \n");
    }

    public void PrintMe() {

        System.out.format("AST %s NODE\n", "TYPE_STRING");


        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "string");
    }

    public TYPE SemantMe() {
        System.out.format("TYPE_STRING" + "- semant me\n");
        return TYPE_STRING.getInstance();
    }
}