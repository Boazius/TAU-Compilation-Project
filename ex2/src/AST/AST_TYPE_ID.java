package AST;

public class AST_TYPE_ID extends AST_TYPE {
    /************************/
    /* simple variable name */
    /************************/
    public String name;

    /******************/
    /* CONSTRUCTOR(S) */

    /******************/
    public AST_TYPE_ID(String name) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("type -> ID( %s )\n", name);

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.name = name;
    }

    /**************************************************/
    /* The printing message for a simple var AST node */

    /**************************************************/
    public void PrintMe() {
        /**********************************/
        /* AST NODE TYPE = AST TYPE ID */
        /**********************************/
        System.out.format("AST NODE TYPE ID( %s )\n", name);

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("TYPE ID(%s)", name));
    }
}
