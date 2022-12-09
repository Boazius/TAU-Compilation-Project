package AST;

public class AST_DEC_SINGLE extends AST_DEC {
    public AST_DEC dec;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
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
        /*************************************/
        /* AST NODE TYPE = AST SUBSCRIPT VAR */
        /*************************************/
        System.out.print("AST NODE SINGLE dec\n");

        /**************************************/
        /* RECURSIVELY PRINT left + right ... */
        /**************************************/
        if (dec != null) dec.PrintMe();


        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "Single Declaration");
        if (dec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, dec.SerialNumber);
    }
}
