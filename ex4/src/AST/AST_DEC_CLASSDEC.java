package AST;

import TEMP.TEMP;
import TYPES.TYPE;

public class AST_DEC_CLASSDEC extends AST_DEC {
    public AST_CLASSDEC cd;

    public AST_DEC_CLASSDEC(AST_CLASSDEC cd) {
        this.cd = cd;
        SerialNumber = AST_Node_Serial_Number.getFresh();

        if (cd != null) System.out.print("dec -> classDec\n");

    }

    public void PrintMe() {

        System.out.format("AST %s NODE\n", "DEC_CLASSDEC");

        if (cd != null) cd.PrintMe();

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "DEC_CLASSDEC");

        if (cd != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cd.SerialNumber);

    }

    public TYPE SemantMe() {
        System.out.println("DEC CLASSDEC - semant me");
        return cd.SemantMe();
    }

    public TEMP IRme() {
        System.out.println("DEC_CLASSDEC" + "- IRme");
        cd.IRme();
        return null;
    }
}
