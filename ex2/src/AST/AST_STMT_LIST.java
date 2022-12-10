package AST;

public class AST_STMT_LIST extends AST_Node {
    /****************/
    /* DATA MEMBERS */
    /****************/
    public AST_STMT head;
    public AST_STMT_LIST tail;

    public AST_STMT_LIST(AST_STMT head, AST_STMT_LIST tail) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if (tail != null) System.out.print("stmtList -> stmt stmtList\n");
        if (tail == null) System.out.print("stmtList -> stmt      \n");

        this.head = head;
        this.tail = tail;
    }

    public void PrintMe() {
        /**************************************/
        /* AST NODE TYPE = AST STATEMENT LIST */
        /**************************************/
        System.out.print("AST_STMT_LIST\n");

        /*************************************/
        /* RECURSIVELY PRINT HEAD + TAIL ... */
        /*************************************/
        if (head != null) head.PrintMe();
        if (tail != null) tail.PrintMe();

        /**********************************/
        /* PRINT to AST GRAPHVIZ DOT file */
        /**********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "STMT LIST");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, head.SerialNumber);
        if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
    }

}
