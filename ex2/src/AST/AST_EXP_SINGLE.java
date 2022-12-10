package AST;

public class AST_EXP_SINGLE extends AST_EXP {
    public AST_EXP exp;

    public AST_EXP_SINGLE(AST_EXP son) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.print("exp -> (exp)\n");

        /*******************************/
        /* COPY INPUT DATA Members ... */
        /*******************************/
        this.exp = son;
    }

    public void PrintMe() {
        System.out.print("AST_EXP_SINGLE\n");

        if (exp != null) exp.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("Single Expression"));

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
    }
}
