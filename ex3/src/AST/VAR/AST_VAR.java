package AST.VAR;

import AST.AST_Node;

public abstract class AST_VAR extends AST_Node {

    public void PrintMe() {
        System.out.print("CANNOT PRINT AST_VAR");
    }

    public abstract String getName();
    //public abstract TYPE SemantMe();
}
