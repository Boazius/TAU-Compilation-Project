package AST.EXP;

import AST.AST_Node;
import TYPES.TYPE;

public abstract class AST_EXP extends AST_Node {
    public void PrintMe() {
        System.out.print("CANNOT PRINT AST_EXP");
    }

    public abstract TYPE getExpType();

    public boolean isConstantInt() {
        return false; /*we will override it, if possibly true.*/
    }

    /*a stupid solution. will override it in AST_EXP_INT*/
    public int getValue() {
        return -1;
    }

    //	public abstract TYPE SemantMe();
}