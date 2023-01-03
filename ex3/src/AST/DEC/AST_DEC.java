package AST.DEC;

import AST.AST_Node;
import TYPES.TYPE;

public abstract class AST_DEC extends AST_Node {

    public TYPE SemantMe() {
        return null;
    }

    public void PrintMe() {
        System.out.print("CANNOT PRINT AST_DEC");
    }

}
