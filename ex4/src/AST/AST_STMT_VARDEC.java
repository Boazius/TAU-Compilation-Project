package AST;

import TEMP.TEMP;
import TYPES.TYPE;

public class AST_STMT_VARDEC extends AST_STMT {
    public AST_VARDEC v;

    public AST_STMT_VARDEC(AST_VARDEC v) {
        this.v = v;

        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print("stmt -> varDec\n");
    }

    public void PrintMe() {


        System.out.format("AST %s NODE\n", "STMT_VARDEC");


        if (v != null) v.PrintMe();

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "STMT_VARDEC");


        if (v != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, v.SerialNumber);

    }

    public TYPE SemantMe() {
        System.out.println("STMT VARDEC - semant me");
        v.SemantMe();
        return null;
    }

    public TEMP IRme() {
        System.out.println("STMT VARDEC - ir me");
        v.IRme();
        return null;
    }
}