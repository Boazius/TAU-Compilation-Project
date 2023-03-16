package AST;

import IR.IR;
import IR.IRcommand_Call_Func;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;
import TYPES.TYPE;
import TYPES.TYPE_FUNCTION;

public class AST_STMT_ID extends AST_STMT {
    public String id;
    public TYPE_FUNCTION func; // for IRme

    public AST_STMT_ID(String id, int line) {
        this.id = id;
        this.line = line;

        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print("stmt -> ID();\n");
    }


    public void PrintMe() {


        System.out.format("AST %s NODE\n", "STMT_ID");


        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("STMT_ID(%s)", id));
    }

    public TYPE SemantMe() {
        System.out.println("STMT_ID - semant me");
        TYPE t = funcSig(id, null, this.line);

        this.func = (TYPE_FUNCTION) (SYMBOL_TABLE.getInstance().find(id));

        return t;
    }

    public TEMP IRme() {
        System.out.println("STMT_ID - IRme");

        TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();

        String startLabel;
        if (id.equals("PrintInt")) {
            startLabel = "PrintInt";
        } else if (id.equals("PrintString")) {
            startLabel = "PrintString";
        } else {
            startLabel = this.func.startLabel;
        }

        IR.getInstance().Add_IRcommand(new IRcommand_Call_Func(t, startLabel, null));

        return t;
    }
}