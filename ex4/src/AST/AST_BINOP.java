package AST;

import IR.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;
import TYPES.*;

/* a op b */
public class AST_BINOP extends AST_Node {

    public AST_EXP left;
    public AST_EXP right;
    public TYPE leftType;
    int number;

    public AST_BINOP(int number, AST_EXP left, AST_EXP right, int line) {
        this.number = number;
        this.left = left;
        this.right = right;
        this.line = line;

        SerialNumber = AST_Node_Serial_Number.getFresh();
    }

    /****************** outside CONSTRUCTOR code *******************/

    /*************************************************/
    /* The printing message for a XXX node */

    /*************************************************/
    public void PrintMe() {
        System.out.format("AST BINOP NODE\n");

        String s = "";
        switch (number) {
            case 0:
                s = "+";
            case 1:
                s = "-";
            case 2:
                s = "*";
            case 3:
                s = "/";
            case 4:
                s = ">";
            case 5:
                s = "<";
            case 6:
                s = "=";
        }
        if (left != null) left.PrintMe();
        if (right != null) right.PrintMe();

        if (left != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, left.SerialNumber);
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("%s", s));
        if (right != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, right.SerialNumber);
    }

    public TYPE SemantMe() {
        System.out.format("BINOP - semant me\n");

        TYPE leftType = null;
        TYPE rightType = null;

        if (left != null) leftType = left.SemantMe();
        if (right != null) rightType = right.SemantMe();

        /*check if we got left and right*/
        if (leftType == null || rightType == null || left == null || right == null) {
            System.out.format(">> ERROR [%d] BINOP has invalid arguments", line);
            printError(line);
            return null;
        }

        /*for IR*/
        this.leftType = leftType;

        /* = */
        if (number == 6) {

            if (leftType == TYPE_VOID.getInstance() || rightType == TYPE_VOID.getInstance()) {
                System.out.format(">> ERROR [%d] can't compare void types", line);
                printError(line);
            }

            /*types are equal*/
            if (type_equals(leftType, rightType) || type_equals(rightType, leftType)) return TYPE_INT.getInstance();

            /*we can compare Father with Son*/
            if (isExtendingClass(leftType, rightType) || isExtendingClass(rightType, leftType))
                return TYPE_INT.getInstance();

            /*we can compare Nil with stuff*/
            if ((leftType.isClass() || leftType.isArray()) && rightType instanceof TYPE_NIL)
                return TYPE_INT.getInstance();
            if ((rightType.isClass() || rightType.isArray()) && leftType instanceof TYPE_NIL)
                return TYPE_INT.getInstance();

            if ((leftType == TYPE_INT.getInstance()) || (rightType == TYPE_STRING.getInstance()) || (leftType == TYPE_STRING.getInstance()) || (rightType == TYPE_INT.getInstance())) {
                System.out.format(">> ERROR [%d] can't compare primitive to non-primitive", line);
                printError(line);
            }

            System.out.format(">> ERROR [%d] can't compare different types", line);
            printError(line);
        }
        if (number == 0) /* + */ {
            if ((leftType == TYPE_INT.getInstance()) && (rightType == TYPE_INT.getInstance())) /* plus */
                return TYPE_INT.getInstance();

            if ((leftType == TYPE_STRING.getInstance()) && (rightType == TYPE_STRING.getInstance())) /*concat strings*/
                return TYPE_STRING.getInstance();

            System.out.format(String.format(">> ERROR [%d] cannot add (+) non int/string types", line));
            printError(line);
        }

        /* not + or = */
        if ((leftType != TYPE_INT.getInstance()) || (rightType != TYPE_INT.getInstance())) {
            System.out.format(">> ERROR [%d] trying binop between wrong types %s %s", line, leftType.name, rightType.name);
            printError(line);
        }

        /*check division by 0*/
        if ((right instanceof AST_EXP_INT) && ((AST_EXP_INT) right).value == 0 && number == 3) {
            System.out.format(">> ERROR [%d] division by 0", line);
            printError(line);
        }
        return TYPE_INT.getInstance();
    }

    public TEMP IRme() {
        System.out.println("BINOP IRme");
        TEMP leftIR = null;
        TEMP rightIR = null;

        if (left != null) leftIR = left.IRme();
        if (right != null) rightIR = right.IRme();

        TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();

        if (number == 0)  /* + */ {
            /*concat strings*/
            if (leftType == TYPE_STRING.getInstance()) {
                IR.getInstance().Add_IRcommand(new IRcommand_Binop_Concat_Strings(dst, leftIR, rightIR));
            }

            /*add integers*/
            else {
                IR.getInstance().Add_IRcommand(new IRcommand_Binop_Add_Integers(dst, leftIR, rightIR));
            }
        }
        if (number == 2) /* multiply * */ {
            IR.getInstance().Add_IRcommand(new IRcommand_Binop_Mul_Integers(dst, leftIR, rightIR));
        }
        if (number == 6) /*equality = */ {
            if (leftType == TYPE_STRING.getInstance()) {// strings
                IR.getInstance().Add_IRcommand(new IRcommand_Binop_EQ_Strings(dst, leftIR, rightIR));
            } else IR.getInstance().Add_IRcommand(new IRcommand_Binop_EQ_Contents(dst, leftIR, rightIR));

        }

        /* <*/
        if (number == 5) {
            IR.getInstance().Add_IRcommand(new IRcommand_Binop_LT_Integers(dst, leftIR, rightIR));
        }
        /* >*/
        if (number == 4) {
            IR.getInstance().Add_IRcommand(new IRcommand_Binop_GT_Integers(dst, leftIR, rightIR));
        }
        /* - */
        if (number == 1) {
            IR.getInstance().Add_IRcommand(new IRcommand_Binop_SUB_Integers(dst, leftIR, rightIR));
        }
        /* division / */
        if (number == 3) {
            IR.getInstance().Add_IRcommand(new IRcommand_Binop_DIV_Integers(dst, leftIR, rightIR));
        }

        return dst;
    }

}