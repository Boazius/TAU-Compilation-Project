package AST;

public class AST_DEC_FUNC extends AST_DEC {

    AST_TYPE type;
    String name;
    AST_STMT_LIST stmtList;
    AST_ID_LIST idList;

    /*********************************************************/
    /* The default message for an unknown AST DECLARATION node */

    /*********************************************************/
    public AST_DEC_FUNC(AST_TYPE type, String name, AST_ID_LIST idList, AST_STMT_LIST stmtList) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/

        System.out.format("funcDec -> TYPE NAME(%s)\n", name);

        /*******************************/
        /* COPY INPUT DATA Members ... */
        /*******************************/
        this.type = type;
        this.name = name;
        this.stmtList = stmtList;
        this.idList = idList;
    }

    public void PrintMe() {

        System.out.format("AST_DEC_FUNC %s", name);
        if (type!=null) type.PrintMe();
        if (idList != null) idList.PrintMe();
        if (stmtList != null) stmtList.PrintMe();


        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Func Declaration TYPE NAME(%s)", name));
        if (type != null)
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
        if (idList != null)
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, idList.SerialNumber);
        if (stmtList != null)
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, stmtList.SerialNumber);

    }
}
