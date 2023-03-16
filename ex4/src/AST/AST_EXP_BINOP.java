package AST;

import TEMP.TEMP;
import TYPES.TYPE;

public class AST_EXP_BINOP extends AST_EXP {
    public AST_BINOP binop;


    public AST_EXP_BINOP(AST_BINOP binop) {

        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print("exp -> exp BINOP exp\n");

        this.binop = binop;
    }

    public void PrintMe() {


        System.out.print("AST EXP_BINOP NODE\n");


        if (binop != null) binop.PrintMe();

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "EXP_BINOP");


        if (binop != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, binop.SerialNumber);

    }

    public TYPE SemantMe() {
        System.out.println("EXP BINOP - semant me");
        return binop.SemantMe();
    }

    public TEMP IRme() {
        System.out.println("EXP BINOP - IRme");

        return binop.IRme();
    }
}