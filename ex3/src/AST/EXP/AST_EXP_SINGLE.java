package AST.EXP;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import TYPES.TYPE;

public class AST_EXP_SINGLE extends AST_EXP {
    public AST_EXP exp;

    public AST_EXP_SINGLE(AST_EXP son) {
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print("exp -> (exp)\n");

        this.exp = son;
    }

    public void PrintMe() {
        /*System.out.print("AST_EXP_SINGLE\n");*/


        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        if (exp != null) exp.PrintMe();
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("exp"));

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
    }

    public TYPE SemantMe() {
        return exp.SemantMe();
    }


    /*TODO USELESS REMOVE*/
    @Override
    public TYPE getExpType() {
        return exp.getExpType();
    }

}
