package AST.EXP;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import UtilFunctions.HelperFunctions;
import TYPES.*;

public class AST_EXP_BINOP extends AST_EXP {
    int OP;
    public AST_EXP leftExp;
    public AST_EXP rightExp;

    public AST_EXP_BINOP(AST_EXP leftExp, AST_EXP rightExp, int OP) {

        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print("exp -> exp BINOP exp\n");

        this.leftExp = leftExp;
        this.rightExp = rightExp;
        this.OP = OP;
    }

    public void PrintMe() {
        String sOP = "";

        /*********************************/
        /* CONVERT OP to a printable sOP */
        /*********************************/
        if (OP == 0) {
            sOP = "+";
        }
        if (OP == 1) {
            sOP = "-";
        }
        if (OP == 2) {
            sOP = "*";
        }
        if (OP == 3) {
            sOP = "/";
        }
        if (OP == 4) {
            sOP = "<";
        }
        if (OP == 5) {
            sOP = ">";
        }
        if (OP == 6) {
            sOP = "=";
        }


        /* System.out.print("AST_EXP_BINOP\n");*/
        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        if (leftExp != null) leftExp.PrintMe();
        if (rightExp != null) rightExp.PrintMe();


        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("BINOP(%s)", sOP));

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (leftExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, leftExp.SerialNumber);
        if (rightExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, rightExp.SerialNumber);
    }

    /*test t1 <>t2  */
    /*type is INT unless we compare strings*/
    public TYPE SemantMe() {
        /*t1 <> t2*/
        TYPE t1 = null;
        TYPE t2 = null;

        if (leftExp != null) t1 = leftExp.SemantMe();
        if (rightExp != null) t2 = rightExp.SemantMe();

        if (t1 == null) {
            HelperFunctions.printError(leftExp.myLine);
            return null;
        }
        if (t2 == null) {
            HelperFunctions.printError(rightExp.myLine);
            return null;
        }
        /*cannot do binop on functions*/
        if (t1.isFunction() || t2.isFunction()) {
            System.out.println("Error: invalid arguments in binop");
            HelperFunctions.printError(this.myLine);
        }
        /*get the type in a loop (if array of array of array...)*/
        while (t1.isArray() && t2.isArray()) {
            t2 = ((TYPE_ARRAY) t2).type;
            t1 = ((TYPE_ARRAY) t1).type;

        }

        /*equal types*/
        if (t1 == t2) {
            if ((t1 == TYPE_INT.getInstance()) && (t2 == TYPE_INT.getInstance())) {
                return TYPE_INT.getInstance();
            }
            if (t1 == TYPE_STRING.getInstance() && t2 == TYPE_STRING.getInstance() && OP == 0)
                return TYPE_STRING.getInstance();
            if (OP == 6) { /*"="*/
                return TYPE_INT.getInstance();
            }
        } else if (OP == 6) { /*"="*/
            if (t1.isArray() && t2.isArray()) {
                t1 = ((TYPE_ARRAY) t1).type;
                t2 = ((TYPE_ARRAY) t2).type;
            }
            if (t1 == TYPE_NIL.getInstance() && t2 == TYPE_NIL.getInstance()) { /*both nil*/
                return TYPE_INT.getInstance();
            }
            if (TYPE.eqByString(t1, "TYPE_NIL")) { /*not same type*/
                /*if one is nil and other is class*/
                if (TYPE.eqByString(t2, "TYPE_ARRAY") || TYPE.eqByString(t2, "TYPE_CLASS")) {
                    return TYPE_INT.getInstance();
                }
            } else if (TYPE.eqByString(t2, "TYPE_NIL")) {
                if (TYPE.eqByString(t1, "TYPE_ARRAY") || TYPE.eqByString(t1, "TYPE_CLASS")) {
                    return TYPE_INT.getInstance();
                }
            } else if (HelperFunctions.biDirectionalIsSonOf(t1, t2)) {
                return TYPE_INT.getInstance();
            }

        }
        System.out.println("Error: invalid parameters for binop");
        HelperFunctions.printError(this.myLine);
        return null;
    }

    /*do we need this?*/
    @Override
    public TYPE getExpType() {
        return null;
    }
}
