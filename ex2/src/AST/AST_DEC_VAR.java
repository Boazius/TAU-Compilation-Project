package AST;

public class AST_DEC_VAR extends AST_DEC {

    AST_TYPE typeNode;
    String name;
    AST_EXP exp;

    /*********************************************************/
    /* The default message for an unknown AST DECLERATION node */

    /*********************************************************/
    public AST_DEC_VAR(AST_TYPE typeNode, String name, AST_EXP exp) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();
        /* TODO type is not a string, but AST_TYPE. fix it in several places here!*/
        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if (exp != null)
            System.out.format(" decVar -> TYPE( %s ) NAME(%s) ASSIGN EXP SEMICOLON\n", typeNode.type, name);
        else
            System.out.format(" decVar -> TYPE( %s ) NAME(%s)SEMICOLON\n", typeNode.type, name);


        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.typeNode = typeNode;
        this.name = name;
        this.exp = exp;
    }

    /************************************************/
    /* The printing message for an INT EXP AST node */

    /************************************************/
    public void PrintMe() {
        /*******************************/
        /* AST NODE TYPE = AST ID EXP */
        /*******************************/
        System.out.format("AST NODE decVar ( %s ) (%s)\n", typeNode.type, name);
        if (exp != null) exp.PrintMe();

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Variable Deceleration: TYPE(%s) NAME(%s)", typeNode.type, name));
        if (exp != null)
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
    }
}
