package AST.DEC;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import TYPES.TYPE;

public class AST_DEC_SINGLE extends AST_DEC {
    public AST_DEC dec;

    public AST_DEC_SINGLE(AST_DEC dec) {
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print("dec ->  varDec | funcDec | classDec | arrayTypeDef \n");

        this.dec = dec;
    }

    public void PrintMe() {
        /*System.out.print("AST_DEC_SINGLE\n");*/

        /****************************************/
        /* PRINT to AST GRAPHVIZ DOT file */
        /****************************************/
        if (dec != null) dec.PrintMe();
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "Dec", "invtriangle");
        if (dec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, dec.SerialNumber);
    }

    @Override
    public TYPE SemantMe() {
        return this.dec.SemantMe();
    }
}
