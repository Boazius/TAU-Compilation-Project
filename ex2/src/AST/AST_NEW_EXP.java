package AST;

public class AST_NEW_EXP extends AST_EXP {
    public AST_TYPE t;
    public AST_EXP exp;

    /******************/
    /* CONSTRUCTOR(S) */

    /******************/
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
            System.out.format("exp -> NEW type(%s) exp\n", t);
        else
            System.out.format("exp -> NEW type(%s) \n", t);


        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.t = t;
        this.exp = exp;
    }

    /************************************************/
    /* The printing message for an INT EXP AST node */

    /************************************************/
    public void PrintMe() {
        /*******************************/
        /* AST NODE TYPE = AST ID EXP */
        /*******************************/
        System.out.format("AST NODE NEW type( %s )\n", t);
        if (exp != null) exp.PrintMe();

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/


        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("NEW type(%s)", t));
        if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
    }
}
