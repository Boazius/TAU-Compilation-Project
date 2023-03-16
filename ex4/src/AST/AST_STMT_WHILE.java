package AST;

import IR.*;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TEMP.TEMP;
import TYPES.TYPE;
import TYPES.TYPE_INT;

public class AST_STMT_WHILE extends AST_STMT {
    public AST_EXP cond;
    public AST_STMT_LIST body;
    public boolean inFunc;

    public AST_STMT_WHILE(AST_EXP cond, AST_STMT_LIST body, int line) {
        this.cond = cond;
        this.body = body;
        this.line = line;
        SerialNumber = AST_Node_Serial_Number.getFresh();

    }

    public void PrintMe() {


        System.out.format("AST %s NODE\n", "STMT_WHILE");

        if (cond != null) cond.PrintMe();
        if (body != null) body.PrintMe();

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "STMT_WHILE");

        if (cond != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cond.SerialNumber);
        if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, body.SerialNumber);
    }

    public TYPE SemantMe() {
        System.out.format("AST_STMT_WHILE" + "- semant me\n");

        if (cond.SemantMe() != TYPE_INT.getInstance()) {
            System.out.format(">> ERROR [%d:%d] condition inside WHILE is not integral\n", 2, 2);
            printError(this.line);
        }

        SYMBOL_TABLE.getInstance().beginScope("while");

        body.SemantMe();

        SYMBOL_TABLE.getInstance().endScope();

        inFunc = SYMBOL_TABLE.getInstance().inFuncScope();

        return TYPE_INT.getInstance();
    }

    public TEMP IRme() {
        System.out.format("AST_STMT_WHILE" + "- IRme\n");


        String label_end = IRcommand.getFreshLabel("end");
        String label_start = IRcommand.getFreshLabel("start");

        IR.getInstance().Add_IRcommand(new IRcommand_Label(label_start));

        TEMP cond_temp = cond.IRme();

        IR.getInstance().Add_IRcommand(new IRcommand_Jump_beqz(cond_temp, label_end));

        if (inFunc) ifScope(body);
        else body.IRme();

        IR.getInstance().Add_IRcommand(new IRcommand_Jump_Label(label_start));


        IR.getInstance().Add_IRcommand(new IRcommand_Label(label_end));

        return null;
    }

}
