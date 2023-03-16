package AST;

import TEMP.TEMP;
import TYPES.TYPE;
import TYPES.TYPE_CLASS;

public class AST_EXP_VARDOT extends AST_EXP {
    public AST_VAR var;
    public String id;
    public TYPE_CLASS tl;

    public AST_EXP_VARDOT(AST_VAR var, String id, int line) {

        SerialNumber = AST_Node_Serial_Number.getFresh();


        System.out.print("exp -> vardot\n");


        this.var = var;
        this.id = id;
        this.line = line;
    }


    public void PrintMe() {

        System.out.print("AST NODE EXP_VARDOT\n");


        if (var != null) var.PrintMe();


        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("EXP_VARDOT(%s)", id));

        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
    }

    public TYPE SemantMe() {
        ///a.g();
        System.out.println("EXP VARDOT - semant me");
        TYPE t1 = var.SemantMe();
        if (!(t1 instanceof TYPE_CLASS)) //not a class
        {
            System.out.println(">> ERROR [" + line + "] var.dot is of wrong class");
            printError(line);
        }
        if (t1 instanceof TYPE_CLASS) {
            tl = (TYPE_CLASS) t1;
        }

        TYPE t2 = isFuncOfClass(t1.name, id, null, this.line);
        if (t2 == null) {
            System.out.println(">> ERROR [" + line + "] var.dot is wrong");
            printError(line);
        }

        return t2;

    }

    public TEMP IRme() {
        System.out.println("EXP VARDOT - IRme");
        return vardotIR(var, null, tl, id);
    }
}