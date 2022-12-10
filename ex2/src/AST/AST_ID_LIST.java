package AST;

public class AST_ID_LIST extends AST_Node {
    /****************/
    /* DATA MEMBERS */
    /****************/
    public AST_TYPE type;
    public String head;
    public AST_ID_LIST tail;

    public AST_ID_LIST(AST_TYPE type, String head, AST_ID_LIST tail) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if (tail != null) System.out.print("IDList -> type ID, IDList\n");
        if (tail == null) System.out.print("IDList -> type ID\n");

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
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

        if(type != null)
            type.PrintMe();

        System.out.format("AST_ID_LIST: TYPE ID( %s )\n", head);

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
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("TYPE ID(%s)", head));
        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
        if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
    }

}
