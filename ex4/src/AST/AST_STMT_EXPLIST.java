package AST;

import IR.IR;
import IR.IRcommand_Call_Func;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;
import TEMP.TEMP_LIST;
import TYPES.TYPE;
import TYPES.TYPE_FUNCTION;

/*this is a function usage statement. i.e foo(var1,var2); */

public class AST_STMT_EXPLIST extends AST_STMT {
    public String id;
    public AST_EXPLIST list;
    public TYPE_FUNCTION func; // for IRme

    public AST_STMT_EXPLIST(String id, AST_EXPLIST list, int line) {
        this.id = id;
        this.list = list;
        this.line = line;

        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print("stmt -> ID(expList);\n");
    }

    public void PrintMe() {

        /*************************************/
        /* AST NODE TYPE- change XXX with this class name */
        /*************************************/
        System.out.format("AST %s NODE\n", "STMT_EXPLIST");


        if (list != null) list.PrintMe();

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("STMT_EXPLIST(%s)", id));


        if (list != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, list.SerialNumber);

    }

    public TYPE SemantMe() {
        System.out.println("STMT EXPLIST - semant me");

        TYPE t = funcSig(id, list, this.line);

        this.func = (TYPE_FUNCTION) (SYMBOL_TABLE.getInstance().find(id));

        return t;
    }

    public TEMP IRme() {
        System.out.println("STMT EXPLIST - IRme");

        TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
        TEMP_LIST resTempsList = null;

        // set resTempList
        for (AST_EXPLIST it = list; it != null; it = it.tail) {
            TEMP curr = it.head.IRme();
            resTempsList = new TEMP_LIST(curr, resTempsList);
        }

        // reverse list
        if (resTempsList != null) {
            resTempsList = resTempsList.reverseList();
        }

        String startLabel;
        if (id.equals("PrintInt")) {
            startLabel = "PrintInt";
        } else if (id.equals("PrintString")) {
            startLabel = "PrintString";
        } else {
            startLabel = this.func.startLabel;
        }

        IR.getInstance().Add_IRcommand(new IRcommand_Call_Func(t, startLabel, resTempsList));

        return t;
    }
}