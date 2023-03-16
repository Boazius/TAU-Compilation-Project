package AST;

import TEMP.TEMP;
import TYPES.TYPE;
import TYPES.TYPE_CLASS;

public class AST_STMT_VARDOT extends AST_STMT {
    public AST_VAR var;
    public String id;
    public TYPE_CLASS tl;

    public AST_STMT_VARDOT(AST_VAR var, String id, int line) {
        this.var = var;
        this.id = id;
        this.line = line;

        SerialNumber = AST_Node_Serial_Number.getFresh();

    }

    public void PrintMe() {


        System.out.format("AST %s NODE\n", "STMT_VARDOT");

        if (var != null) var.PrintMe();

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("STMT_VARDOT(%s)", id));

        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
    }

    public TYPE SemantMe() {
        ///a.g(); or a.g(a,b);
        System.out.println("STMT VARDOT - semant me");
        TYPE t1 = var.SemantMe();
        if (t1 == null || !(t1 instanceof TYPE_CLASS)) //not a class
        {
            System.out.format(">> ERROR [%d] var.dot is of wrong class", line);
            printError(line);
        }
        tl = (TYPE_CLASS) t1;

        TYPE t2 = isFuncOfClass(t1.name, id, null, this.line);
        if (t2 == null) {
            System.out.format(">> ERROR [%d] var.dot is wrong", line);
            printError(line);
        }

        return t2;

    }

    public TEMP IRme() {
        System.out.println("STMT VARDOT - IRme");
        return vardotIR(var, null, tl, id);
    }
}