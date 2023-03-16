package AST;

import IR.IR;
import IR.IRcommand_Return;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TEMP.TEMP;
import TYPES.TYPE;
import TYPES.TYPE_VOID;

public class AST_STMT_RETURN extends AST_STMT {


    public AST_STMT_RETURN(int line) {

        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.line = line;

        System.out.print("stmt -> return;\n");

    }

    public void PrintMe() {


        System.out.format("AST %s NODE\n", "STMT_RETURN");


        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "STMT_RETURN");
    }

    public TYPE SemantMe() {
        System.out.println("STMT RETURN - semant me");
        int a = SYMBOL_TABLE.getInstance().findFunc("void");
        if (a == 0) {
            System.out.format("Error[%d] in return statement!", line);
            printError(line);
        }
        return TYPE_VOID.getInstance();
    }

    public TEMP IRme() {
        IR.getInstance().Add_IRcommand(new IRcommand_Return(null));
        return null;
    }
}