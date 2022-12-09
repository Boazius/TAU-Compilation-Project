package AST;

public class AST_DEC_SINGLE extends AST_DEC {
    public AST_DEC dec;
    public AST_DEC_SINGLE(AST_DEC dec) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.print("dec ->  varDec | funcDec | classDec | arrayTypeDef \n");

        /*******************************/
        /* COPY INPUT DATA Members ... */
        /*******************************/
        this.dec = dec;
    }

    public void PrintMe() {
        System.out.print("AST_DEC_SINGLE\n");
        /*print the dec*/
        if (dec != null) dec.PrintMe();


        /****************************************/
        /* PRINT node and add edge to the dec to AST GRAPHVIZ DOT file */
        /****************************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "Single Declaration");
        if (dec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, dec.SerialNumber);
    }
}
