package AST.EXP;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import TYPES.TYPE;
import TYPES.TYPE_NIL;

public class AST_EXP_NIL extends AST_EXP {

    public AST_EXP_NIL() {
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.format("exp -> NIL\n");

    }

    public void PrintMe() {
        /*System.out.format("AST_EXP_NIL \n");*/

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "NIL");
    }


    public TYPE SemantMe() {
        return TYPE_NIL.getInstance();
    }

    @Override
    public TYPE getExpType() {
        return TYPE_NIL.getInstance();
    }
}
