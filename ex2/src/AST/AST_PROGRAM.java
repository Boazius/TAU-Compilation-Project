package AST;

public class AST_PROGRAM extends AST_Node {
    /************************/
    /* simple variable name */
    /************************/
    public AST_DEC_LIST decList;

    /******************/
    /* CONSTRUCTOR(S) */

    /******************/
    public AST_PROGRAM(AST_DEC_LIST decList) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("Program -> [decList]+\n");

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.decList = decList;
    }

    public void PrintMe() {

        System.out.format("AST_PROGRAM\n");


        decList.PrintMe();
        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("START OF PROGRAM"));

        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, decList.SerialNumber);
    }
}
