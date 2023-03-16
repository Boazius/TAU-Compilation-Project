package AST;

import TEMP.TEMP;
import TYPES.TYPE;

public class AST_DEC_FUNCDEC extends AST_DEC {
    public AST_FUNCDEC func;

    public AST_DEC_FUNCDEC(AST_FUNCDEC func) {
        this.func = func;
        SerialNumber = AST_Node_Serial_Number.getFresh();

        if (func != null) System.out.print("dec -> funcDec\n");
    }

    public void PrintMe() {

        System.out.format("AST %s NODE\n", "DEC_FUNCDEC");

        if (func != null) func.PrintMe();
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "DEC_FUNCDEC");

        if (func != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, func.SerialNumber);

    }

    public TYPE SemantMe() {
        System.out.println("DEC_FUNCDEC" + "- semantme");
        func.SemantMe();
        return null;
    }

    public TEMP IRme() {
        System.out.println("DEC_FUNCDEC" + "- IRme");

        func.IRme();

        return null;
    }
}
