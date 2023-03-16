package AST;

import IR.IR;
import IR.IRcommandConstInt;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;
import TYPES.TYPE;
import TYPES.TYPE_INT;

public class AST_EXP_MINUS_INT extends AST_EXP {
    public int value;


    public AST_EXP_MINUS_INT(int value) {

        SerialNumber = AST_Node_Serial_Number.getFresh();

        this.value = -value;

        System.out.format("exp -> -INT(%d)\n", value);

    }


    public void PrintMe() {

        System.out.format("AST NODE INT( %d )\n", value);

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("%d", value));
    }

    public TYPE SemantMe() {
        System.out.println("EXP MINUS INT - semant me");
        return TYPE_INT.getInstance();
    }

    public TEMP IRme() {
        System.out.println("EXP MINUS INT - IRme");

        TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
        IR.getInstance().Add_IRcommand(new IRcommandConstInt(t, value));
        return t;
    }
}
