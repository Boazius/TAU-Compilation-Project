package AST;

import TEMP.TEMP;
import TYPES.TYPE;
import TYPES.TYPE_CLASS;

public class AST_STMT_VARDOT_EXPLIST extends AST_STMT {
    public AST_VAR var;
    public String id;
    public AST_EXPLIST list;
    public TYPE_CLASS tl;


    public AST_STMT_VARDOT_EXPLIST(AST_VAR var, String id, AST_EXPLIST list, int line) {
        this.var = var;
        this.id = id;
        this.list = list;
        this.line = line;

        System.out.print("stmt -> var.ID(expList);\n");

        SerialNumber = AST_Node_Serial_Number.getFresh();

    }

    public void PrintMe() {

        /*************************************/
        /* AST NODE TYPE- change XXX with this class name */
        /*************************************/
        System.out.format("AST %s NODE\n", "VARDOT_EXPLIST");

        if (var != null) var.PrintMe();
        if (list != null) list.PrintMe();

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("VARDOT_EXPLIST(%s)", id));


        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
        if (list != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, list.SerialNumber);
    }

    public TYPE SemantMe() {
        System.out.println("STMT VARDOT EXPLIST - semant me not completed!");
        TYPE t1 = var.SemantMe();
        if (t1 == null || !(t1 instanceof TYPE_CLASS)) // not a class
        {
            System.out.println(">> ERROR [" + line + "] var.dot is of wrong class");
            printError(line);
        }
        tl = (TYPE_CLASS) t1;
        TYPE t2 = isFuncOfClass(t1.name, id, list, this.line);
        if (t2 == null) {
            System.out.println(">> ERROR [" + line + "] var.dot is wrong");
            printError(line);
        }

        return t2;
    }

    public TEMP IRme() {
        System.out.println("EXP VARDOT - IRme");
        return vardotIR(var, list, tl, id);
    }
}
