package AST;

import TEMP.TEMP;
import TYPES.TYPE;

/*class method function declaration*/
public class AST_CFIELD_FUNCDEC extends AST_CFIELD {
    public AST_FUNCDEC func;

    public AST_CFIELD_FUNCDEC(AST_FUNCDEC func) {
        this.func = func;

        SerialNumber = AST_Node_Serial_Number.getFresh();
    }

    public void PrintMe() {

        System.out.format("AST CFIELD_FUNCDEC NODE\n");

        if (func != null) func.PrintMe();

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("CFIELD_FUNCDEC"));

        if (func != null) {
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, func.SerialNumber);
        }
    }

    public TYPE SemantMe() {
        System.out.println("CFIELD_FUNCDEC semant me");
        return func.SemantMe();
    }

    public TEMP IRme() {
        System.out.println("CFIELD_FUNCDEC - IRme");
        func.IRme();
        return null;
    }
}
