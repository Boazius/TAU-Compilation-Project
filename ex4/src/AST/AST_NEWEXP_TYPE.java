package AST;

import IR.IR;
import IR.IRcommand_New_Class_Object;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;
import TYPES.TYPE;
import TYPES.TYPE_INT;
import TYPES.TYPE_STRING;

public class AST_NEWEXP_TYPE extends AST_NEWEXP {
    public AST_TYPE t;

    public AST_NEWEXP_TYPE(AST_TYPE t, int line) {
        this.t = t;
        this.line = line;

        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print("newExp -> NEW type:t \n");
    }


    public void PrintMe() {


        System.out.format("AST %s NODE\n", "NEWEXP_TYPE");

        if (t != null) t.PrintMe();

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "NEWEXP_TYPE");

        if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, t.SerialNumber);

    }

    public TYPE SemantMe() {
        System.out.println("NEWEXP TYPE - semant me");

        if (t.typeName.equals("void") || t.typeName.equals("nil")) {
            System.out.println(">> ERROR [" + line + "] cant declare type void/nil");
            printError(line);
        }
        // should return type and not ast type!!
        if (t.typeName.equals("int")) {
            return TYPE_INT.getInstance();
        }
        if (t.typeName.equals("string")) {
            return TYPE_STRING.getInstance();
        }

        TYPE cl = SYMBOL_TABLE.getInstance().findClass(t.typeName);
        if (cl == null) {
            System.out.println(">> ERROR [" + line + "] cant declare " + t.typeName + " type");
            printError(line);
        }
        return cl;
    }

    public TEMP IRme() {
        System.out.println("NEWEXP TYPE- IRme");

        TEMP t1 = TEMP_FACTORY.getInstance().getFreshTEMP();
        IR.getInstance().Add_IRcommand(new IRcommand_New_Class_Object(t1, t.typeName));
        return t1;
    }
}