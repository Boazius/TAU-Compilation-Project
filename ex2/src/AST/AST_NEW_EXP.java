package AST;

public class AST_NEW_EXP extends AST_EXP {
    public AST_TYPE t;
    public AST_EXP exp;


    public AST_NEW_EXP(AST_TYPE t, AST_EXP exp) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        /*TODO check class maybe of type somehow */
        if (exp != null)
            System.out.format("exp -> NEW type [exp]\n");
        else
            System.out.format("exp -> NEW type \n");


        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.t = t;
        this.exp = exp;
    }

    public void PrintMe() {
        /*******************************/
        /* AST NODE TYPE = AST ID EXP */
        /*******************************/
        System.out.format("AST_NEW_EXP\n");
        if (t != null) t.PrintMe();
        if (exp != null) exp.PrintMe();

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/


        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("New Exp"));
        if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, t.SerialNumber);
        if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
    }
}
