package AST;

public class AST_ARRAY_TYPEDEF extends AST_DEC {

    String name;
    AST_TYPE type;

    public AST_ARRAY_TYPEDEF(String name, AST_TYPE type) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        /*TODO handle type*/
        System.out.format("arrayTypeDef ->  ARRAY NAME( %s ) = type[]\n", name);

        /*******************************/
        /* COPY INPUT DATA MEMBERS      */
        /*******************************/
        this.name = name;
        this.type = type;
    }

    /************************************************/
    /* printing to console and dot file */
    /************************************************/
    public void PrintMe() {
        System.out.format("AST_ARRAY_TYPEDEF ID( %s ) = TYPE[]", name);
        if (type!=null) type.PrintMe();
        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Array Declaration NAME(%s)", name));
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);

    }
}
