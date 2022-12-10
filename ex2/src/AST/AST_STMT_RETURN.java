package AST;

public class AST_STMT_RETURN extends AST_STMT {
    public AST_EXP res;

    public AST_STMT_RETURN(AST_EXP res) {

        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if (res != null) System.out.print("stmt -> RETURN exp;\n");
        if (res == null) System.out.print("stmt -> RETURN;\n");

        this.res = res;

    }

    public void PrintMe() {
        System.out.print("AST_STMT_RETURN\n");
        if (res != null) res.PrintMe();
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "STMT RETURN");
        if (res != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, res.SerialNumber);
    }
}