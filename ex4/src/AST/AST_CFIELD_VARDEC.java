package AST;

import TEMP.TEMP;
import TYPES.TYPE;

public class AST_CFIELD_VARDEC extends AST_CFIELD {
    public AST_VARDEC varDec;

    public AST_CFIELD_VARDEC(AST_VARDEC varDec) {
        this.varDec = varDec;
        SerialNumber = AST_Node_Serial_Number.getFresh();

        if (varDec != null) System.out.print("cfield -> varDec\n");
    }

    public void PrintMe() {

        /*print node*/
        System.out.format("AST CFIELD_VARDEC NODE\n");

        if (varDec != null) varDec.PrintMe();

        /*print ast*/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "CFIELD_VARDEC");
        if (varDec != null) {
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, varDec.SerialNumber);
        }
    }

    public TYPE SemantMe() {
        System.out.println("CFIELD VARDEC - semant me");
        return varDec.SemantMe();

    }

    public TEMP IRme() {
        System.out.println("CFIELD_VARDEC" + "- IRme");
        varDec.IRme();
        return null;
    }

}
