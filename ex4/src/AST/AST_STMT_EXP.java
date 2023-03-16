package AST;

import IR.IR;
import IR.IRcommand_Return;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TEMP.TEMP;
import TYPES.TYPE;

public class AST_STMT_EXP extends AST_STMT {
    public AST_EXP e;

    /*******************/
    /* CONSTRUCTOR(S) */

    /*******************/
    public AST_STMT_EXP(AST_EXP e, int line) {
        this.e = e;
        this.line = line;
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.print("stmt -> RETURN exp SEMICOLON	\n");
    }

    public void PrintMe() {

        /*************************************/
        /* AST NODE TYPE- change XXX with this class name */
        /*************************************/
        System.out.format("AST %s NODE\n", "STMT_EXP");

        if (e != null) e.PrintMe();
        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /* print node name and optional string (maybe only needed in binop nodes) */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "STMT_EXP");

        if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, e.SerialNumber);

    }

    /*return e*/
    public TYPE SemantMe() {
        System.out.println("STMT EXP - semant me");
        TYPE ty = e.SemantMe();
        if (ty == null) {
            return null;
        }

        /*check that return type is the same as exp type*/
        TYPE retTypeOfFunc = SYMBOL_TABLE.getInstance().getReturnTypeOfFunc();
        if (!isAssignable(retTypeOfFunc, ty)) {
            System.out.println("=======Error in return statement!");
            printError(line);
        }
        return ty;
    }

    public TEMP IRme() {
        TEMP retVal = e.IRme();
        IR.getInstance().Add_IRcommand(new IRcommand_Return(retVal));
        return null;
    }
}