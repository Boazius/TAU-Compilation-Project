package AST;

import TEMP.TEMP;
import TYPES.TYPE;

public class AST_DEC_ATD extends AST_DEC {
    public AST_ATD array;

    public AST_DEC_ATD(AST_ATD array) {
        this.array = array;
        SerialNumber = AST_Node_Serial_Number.getFresh();

        if (array != null) System.out.print("dec -> ATD\n");
    }

    public void PrintMe() {

        System.out.format("AST %s NODE\n", "DEC_ATD");

        if (array != null) array.PrintMe();

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "DEC_ATD");

        if (array != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, array.SerialNumber);
    }

    public TYPE SemantMe() {

        System.out.println("DEC_ATD" + "- semantme");

        array.SemantMe();
        return null;
    }

    public TEMP IRme() {
        System.out.println("DEC_ATD" + "- IRme");

        array.IRme();
        return null;
    }

}