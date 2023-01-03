package AST.STMT;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import AST.EXP.AST_EXP;
import AST.VAR.AST_VAR;
import UtilFunctions.HelperFunctions;
import TYPES.*;

public class AST_STMT_ASSIGN extends AST_STMT {
    public AST_VAR var;
    public AST_EXP exp;

    public AST_STMT_ASSIGN(AST_VAR var, AST_EXP exp) {
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print("stmt -> var:= exp;\n");

        this.var = var;
        this.exp = exp;
        left = var;
        right = exp;
    }

    public void PrintMe() {

        /* System.out.print("AST_STMT_ASSIGN\n");*/

        if (var != null) var.PrintMe();
        if (exp != null) exp.PrintMe();

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "ASSIGN left := right");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
    }


    /*semant var and exp, and check the assignment */
    public TYPE SemantMe() {
        TYPE t1 = null;
        TYPE t2 = null;

        if (var != null) t1 = var.SemantMe();
        if (exp != null) t2 = exp.SemantMe();

        if (t1 == null || t2 == null)
            return null;

        if (!assignmentChecker(t1, t2)) {
            System.out.format("ERROR type mismatch for var := exp\n");
            HelperFunctions.printError(var.myLine);
        }
        return null;
    }


    /*check if assignment is valid*/
    //t1 := t2
    public static boolean assignmentChecker(TYPE t1, TYPE t2) {
        /*cannot assign to NIL*/
        if (t1 == TYPE_NIL.getInstance())
            return false;

        if (t1 != t2) {
            /*allowed only if father/son, or NIL into object/array*/

            /*t1 is an array*/
            if (t1.isArray()) {
                TYPE_ARRAY t1Arr = (TYPE_ARRAY) t1;
                if (t2 == TYPE_NIL.getInstance())
                    return true;
                if (t2 instanceof TYPE_ARRAY) {
                    return t1Arr.type == ((TYPE_ARRAY) t2).type; /*their type must be the same type of array*/
                }
                return false;
            } else if (t2 == TYPE_NIL.getInstance()) {
                /*cannot assign NIL into primitive*/
                if (t1 != TYPE_INT.getInstance() && t1 != TYPE_STRING.getInstance()) {
                    return true;
                }
            }

            else return HelperFunctions.isSonOf(t2, t1);
        }
        return true;
    }

}
