package AST;

public class AST_EXP_NIL extends AST_EXP {

    public AST_EXP_NIL() {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("exp -> NIL\n");

    }

    public void PrintMe() {
        /*******************************/
        /* AST NODE TYPE = AST ID EXP */
        /*******************************/
        System.out.format("AST_EXP_NIL \n");

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("NIL"));
    }
}
