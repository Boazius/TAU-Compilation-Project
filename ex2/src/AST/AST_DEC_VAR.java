package AST;

public class AST_DEC_VAR extends AST_DEC {

    AST_TYPE typeNode;
    String id;
    AST_EXP exp;

    /*********************************************************/
    /* The default message for an unknown AST DECLERATION node */

    /*********************************************************/
    public AST_DEC_VAR(AST_TYPE typeNode, String id, AST_EXP exp) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();
        /* TODO type is not a string, but AST_TYPE. fix it in several places here!*/
        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if (exp != null)
            System.out.format(" varDec -> TYPE ID(%s)::= EXP;\n", id);
        else
            System.out.format(" varDec -> TYPE ID(%s);\n", id);


        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.typeNode = typeNode;
        this.id = id;
        this.exp = exp;
    }

    /************************************************/
    /* The printing message for an INT EXP AST node */

    /************************************************/
    public void PrintMe() {
        /*******************************/
        /* AST NODE TYPE = AST ID EXP */
        /*******************************/
        /*TODO test this*/
        if (typeNode!= null) typeNode.PrintMe();
        System.out.format("AST_DEC_VAR ID(%s)\n", id);
        if (exp != null) exp.PrintMe();

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        /*variable declare node*/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Var Declaration: NAME(%s)", id));
        /*edge to type*/
        if (typeNode != null)
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, typeNode.SerialNumber);
        if (exp != null)
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
    }
}
