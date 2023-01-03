package AST.VAR;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import AST.EXP.AST_EXP;
import UtilFunctions.HelperFunctions;
import TYPES.TYPE;
import TYPES.TYPE_ARRAY;
import TYPES.TYPE_INT;

public class AST_VAR_SUBSCRIPT extends AST_VAR {
    public AST_VAR var;
    public AST_EXP subscript;

    public AST_VAR_SUBSCRIPT(AST_VAR var, AST_EXP subscript) {
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print("var -> var [ exp ]\n");

        this.var = var;
        this.subscript = subscript;
        left = var;
        right = subscript;
    }

    public void PrintMe() {
        /*System.out.print("AST_VAR_SUBSCRIPT\n");*/

        if (var != null) var.PrintMe();
        if (subscript != null) subscript.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("SUBSCRIPT left[right]"));

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
        if (subscript != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, subscript.SerialNumber);
    }


    @Override
    public TYPE SemantMe() {
        TYPE expType = subscript.SemantMe();
        if (expType != TYPE_INT.getInstance()) {
            System.out.println("Error: array subscript is not with int value");
            HelperFunctions.printError(this.myLine);
        }

        TYPE_ARRAY array = (TYPE_ARRAY) var.SemantMe();
        return array.type;
    }

    @Override
    public String getName() {
        return this.var.getName();
    }
}
