package AST;

import TEMP.TEMP;
import TYPES.TYPE;

public class AST_EXP_VAR extends AST_EXP {
    public AST_VAR var;

    public AST_EXP_VAR(AST_VAR var) {

        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print("exp -> var\n");

        this.var = var;
    }

    public void PrintMe() {

        System.out.print("AST EXP_VAR NODE\n");

        if (var != null) var.PrintMe();

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "EXP_VAR");

        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);

    }

    public TYPE SemantMe() {
        System.out.println("EXP VAR - semant me");

        return var.SemantMe();
    }

    public TEMP IRme() {
        System.out.println("EXP VAR - IRme");
        return var.IRme();
    }
}
