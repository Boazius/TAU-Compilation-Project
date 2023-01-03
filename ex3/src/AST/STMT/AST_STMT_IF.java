package AST.STMT;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import AST.EXP.AST_EXP;
import UtilFunctions.HelperFunctions;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_INT;

public class AST_STMT_IF extends AST_STMT {
    public AST_EXP cond;
    public AST_STMT_LIST body;

    public AST_STMT_IF(AST_EXP cond, AST_STMT_LIST body) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        /*IF ′(′ exp ′)′ ′{′ stmt [ stmt ]∗*/
        System.out.print("stmt -> IF (exp) {stmtList} \n");

        this.cond = cond;
        this.body = body;
        left = cond;
        right = body;
    }

    public void PrintMe() {
        /*System.out.print("AST_STMT_IF\n")*/
        ;

        if (cond != null) cond.PrintMe();
        if (body != null) body.PrintMe();

        /**********************************/
        /* PRINT to AST GRAPHVIZ DOT file */
        /**********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "STMT IF");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cond.SerialNumber);
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, body.SerialNumber);
    }

    public TYPE SemantMe() {

        if (cond.SemantMe() != TYPE_INT.getInstance()) {
            System.out.format("ERROR condition in IF is not integer\n");
            HelperFunctions.printError(cond.myLine);
        }

        /*************************/
        /* [1] Begin IF Scope */
        /*************************/
        SYMBOL_TABLE.getInstance().beginScope();

        /***************************/
        /* [2] Semant Data Members */
        /***************************/
        body.SemantMe();

        /*****************/
        /* [3] End Scope */
        /*****************/
        SYMBOL_TABLE.getInstance().endScope();


        /*irrelevant*/
        return null;
    }
}