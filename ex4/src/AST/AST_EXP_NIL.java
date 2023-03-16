package AST;

import IR.IR;
import IR.IRcommand_Set_Nil;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;
import TYPES.TYPE;
import TYPES.TYPE_NIL;

public class AST_EXP_NIL extends AST_EXP {

    public AST_EXP_NIL() {
        SerialNumber = AST_Node_Serial_Number.getFresh();
    }

    public void PrintMe() {
        System.out.format("AST %s NODE\n", "EXP_NIL");

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "EXP_NIL");

    }

    public TYPE SemantMe() {
        System.out.println("EXP NIL - semant me");
        return TYPE_NIL.getInstance();
    }

    public TEMP IRme() {
        System.out.println("EXP NIL - IRme");

        TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
        IR.getInstance().Add_IRcommand(new IRcommand_Set_Nil(t));
        return t;
    }
}