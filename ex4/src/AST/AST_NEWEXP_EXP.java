package AST;

import IR.IR;
import IR.IRcommand_New_Array;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;
import TYPES.TYPE;
import TYPES.TYPE_ARRAY;
import TYPES.TYPE_INT;

public class AST_NEWEXP_EXP extends AST_NEWEXP {
    public AST_TYPE t;
    public AST_EXP e;

    public AST_NEWEXP_EXP(AST_TYPE t, AST_EXP e, int line) {
        this.t = t;
        this.e = e;
        this.line = line;

        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print("newExp -> NEW type:t LBRACK exp:e RBRACK \n");
    }


    public void PrintMe() {

        /*************************************/
        /* AST NODE TYPE- change XXX with this class name */
        /*************************************/
        System.out.format("AST %s NODE\n", "NEWEXP_EXP");

        if (t != null) t.PrintMe();
        if (e != null) e.PrintMe();

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "NEWEXP_EXP");


        if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, t.SerialNumber);
        if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, e.SerialNumber);
    }

    public TYPE SemantMe() {
        System.out.println("VARDEC NEWEXP - semant me");

        TYPE t1;
        TYPE t2;

        if (t == null || e == null || t.typeName.equals("nil") || t.typeName.equals("void")) {
            System.out.println(">> ERROR [" + line + "] cant declate type void/nil");
            printError(this.line);
        }

        t1 = t.SemantMe();
        t2 = e.SemantMe();

        if (t1 == null || t2 == null) {
            System.out.format(">> ERROR [%d] non existing type %s %s\n", line, t1, t2);
            printError(line);
        }

        if (!type_equals(t2, TYPE_INT.getInstance())) {
            if (t2 != null) {
                System.out.format(">> ERROR [%d] array subscript type is %s- new type[exp]; (newexp_exp)\n", line, t2.name);
            }
            printError(this.line);
        }

        if ((e instanceof AST_EXP_INT) && (((AST_EXP_INT) e).value <= 0)) {
            System.out.format(">> ERROR [%d] array subscript must be positive; (newexp_exp)\n", line);
            printError(this.line);
        }

        if (e instanceof AST_EXP_MINUS_INT) {
            System.out.format(">> ERROR [%d] array subscript must be positive; (newexp_exp)\n", line);
            printError(this.line);
        }

        if (t1 != null) {
            return new TYPE_ARRAY(t1, t1.name + "[]");
        }
        return null;
    }

    public TEMP IRme() {
        TEMP t1 = e.IRme();

        TEMP t2 = TEMP_FACTORY.getInstance().getFreshTEMP();
        IR.getInstance().Add_IRcommand(new IRcommand_New_Array(t2, t1));

        return t2;
    }
}