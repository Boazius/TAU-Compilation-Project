package AST;

public class AST_CFIELD extends AST_Node {
    public AST_DEC dec;
    public AST_CFIELD(AST_DEC dec) {

        System.out.println("cField -> varDec | funcDec ");


        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.dec = dec;
    }

    public void PrintMe() {
        System.out.print("AST_CFIELD\n");

        if (dec != null) dec.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "CFIELD");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, dec.SerialNumber);
    }
}
