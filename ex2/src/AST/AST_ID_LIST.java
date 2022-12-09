package AST;

public class AST_ID_LIST extends AST_Node {
    /****************/
    /* DATA MEMBERS */
    /****************/
    public AST_TYPE type;
    public String head;
    public AST_ID_LIST tail;

    /******************/
    /* CONSTRUCTOR(S) */

    /******************/
    public AST_ID_LIST(AST_TYPE type, String head, AST_ID_LIST tail) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if (tail != null) System.out.print("ids -> type ID ids\n");
        if (tail == null) System.out.print("ids -> type ID     \n");

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.type = type;
        this.head = head;
        this.tail = tail;
    }

    /******************************************************/
    /* The printing message for a statement list AST node */
    /* TODO type.type is bad, u need to printme on the node AST_TYPE*/
    /******************************************************/
    public void PrintMe() {
        /**************************************/
        /* AST NODE TYPE = AST STATEMENT LIST */
        /**************************************/
        System.out.format("AST NODE ID LIST: TYPE(&s) ID(&s)\n", type.type, head);

        /*************************************/
        /* RECURSIVELY PRINT HEAD + TAIL ... */
        /*************************************/
        if (tail != null) tail.PrintMe();

        /**********************************/
        /* PRINT to AST GRAPHVIZ DOT file */
        /**********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "ID LIST");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("TYPE(%s) ID2(%s)", type.type, head));
        if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
    }

}
