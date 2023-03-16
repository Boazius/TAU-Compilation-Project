package AST;

import TEMP.TEMP;
import TYPES.TYPE;

public class AST_DEC_VARDEC extends AST_DEC {
    public AST_VARDEC v;

    public AST_DEC_VARDEC(AST_VARDEC v) {
        this.v = v;

        SerialNumber = AST_Node_Serial_Number.getFresh();

        if (v != null) System.out.print("====================== dec -> varDec\n");
    }

    public void PrintMe() {

        System.out.printf("AST %s NODE\n", "VARDEC");


        if (v != null) v.PrintMe();

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "VARDEC");

        if (v != null) {
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, v.SerialNumber);
        }
    }

    public TYPE SemantMe() {
        System.out.println("DEC VARDEC" + "- semantme");
        if (v != null) {
            return v.SemantMe();
        }

        return null;
    }

    public TEMP IRme() {
        System.out.println("DEC_VARDEC" + "- IRme");
        if (v != null) {
            return v.IRme();
        }
        return null;
    }

}