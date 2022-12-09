package AST;

public class AST_ID_LIST extends AST_Node {
    /****************/
    /* DATA MEMBERS */
    /****************/
    public String head1;
    public String head2;
    public AST_ID_LIST tail;

    /******************/
    /* CONSTRUCTOR(S) */

    /******************/
    public AST_ID_LIST(String head1, String head2, AST_ID_LIST tail) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if (tail != null) System.out.print("ids -> ID ID ids\n");
        if (tail == null) System.out.print("ids -> ID ID     \n");

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.head1 = head1;
        this.head2 = head2;
        this.tail = tail;
    }

    /******************************************************/
    /* The printing message for a statement list AST node */

    /******************************************************/
    public void PrintMe() {
        /**************************************/
        /* AST NODE TYPE = AST STATEMENT LIST */
        /**************************************/
        System.out.format("AST NODE ID LIST: ID1(&s) ID2(&s)\n", head1, head2);

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
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("ID1(%s) ID2(%s)", head1, head2));
        if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
    }

}
