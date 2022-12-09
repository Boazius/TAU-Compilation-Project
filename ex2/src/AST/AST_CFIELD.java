package AST;

public class AST_CFIELD extends AST_Node {
    public AST_DEC dec;

    public AST_CFIELD(AST_DEC v) {

        System.out.println("cField -> varDec | funcDec ");

        this.dec = v;
        SerialNumber = AST_Node_Serial_Number.getFresh();
    }

    public void PrintMe() {
        /********************************************/
        /* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
        /********************************************/
        System.out.print("AST NODE cField\n");

        /***********************************/
        /* print the AST_DEC subtree */
        /***********************************/
        if (dec != null) dec.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "CFIELD ");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, dec.SerialNumber);
    }
}
