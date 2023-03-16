package AST;

import IR.IR;
import IR.IRcommand_Call_Func;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;
import TYPES.TYPE;
import TYPES.TYPE_FUNCTION;

public class AST_EXP_ID extends AST_EXP {
    public String id;
    public TYPE_FUNCTION func;


    public AST_EXP_ID(String id, int line) {
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print(" exp -> id()\n");

        this.id = id;
        this.line = line;
    }

    public void PrintMe() {

        System.out.format("AST %s NODE\n", "EXP_ID");

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("EXP_ID(%s)", id));

    }

    public TYPE SemantMe() {
        System.out.println("EXP ID - semant me");
        TYPE t = funcSig(id, null, this.line);

        TYPE foundId = SYMBOL_TABLE.getInstance().find(id);
        if (!foundId.isFunc()) {
            System.out.format(">> ERROR [%d] %s is not a function in this scope\n", line, id);
            printError(this.line);
        }

        this.func = (TYPE_FUNCTION) (SYMBOL_TABLE.getInstance().find(id));

        return t;
    }

    public TEMP IRme() {
        System.out.println("EXP ID - IRme");

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
