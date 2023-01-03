package AST.EXP;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import AST.VAR.AST_VAR;
import UtilFunctions.HelperFunctions;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

public class AST_EXP_METHOD extends AST_EXP {

    public String id;
    public AST_VAR var;
    public AST_EXP_LIST args;

    public AST_EXP_METHOD(AST_VAR var, String id, AST_EXP_LIST args) {
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if ((var != null) && (args != null))
            System.out.printf("exp -> var. %s (expList)\n", id);
        else if (var != null)
            System.out.printf("exp -> var. %s ()\n", id);
        else if (args != null)
            System.out.printf("exp -> %s (expList)\n", id);
        else
            System.out.printf("exp -> %s ()\n", id);

        this.var = var;
        this.id = id;
        this.args = args;
        left = var;
        right = args;

    }

    public void PrintMe() {
        /*        System.out.format("AST_EXP_METHOD( %s )\n", id);*/

        if (var != null) var.PrintMe();
        if (args != null) args.PrintMe();
        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Call for method: NAME(%s)", id));
        if (var != null) {
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
        }
        if (args != null) {
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, args.SerialNumber);
        }
    }


    /*check semantics*/
    @Override
    public TYPE SemantMe() {
        TYPE_FUNCTION func;
        if (var != null) { /*var.ID(args)'*/
            TYPE c = var.SemantMe();
            if (!c.isClass()) {
                System.out.println("Error: calling a method from non-class\n");
                HelperFunctions.printError(this.myLine);
                return null;
            }
            TYPE_CLASS classType = (TYPE_CLASS) c;
            /*find the function named id in class var*/
            func = (TYPE_FUNCTION) classType.function_list.findInList(this.id);

            if (func == null) {
                System.out.format("ERROR: no such function %s in class %s\n", id, classType.name);
                HelperFunctions.printError(this.myLine);
                return null;
            }
        } else { /*id(args) */
            func = (TYPE_FUNCTION) SYMBOL_TABLE.getInstance().find(this.id);
            if (func == null) {
                System.out.format("ERROR: no such func %s\n", id);
                HelperFunctions.printError(this.myLine);
                return null;
            }
        }

        /*check arguuments of function that we found*/
        if (args == null) {
            if (func.arguments != null) {
                System.out.println("Error: not enough arguments sent to function\n");
                HelperFunctions.printError(myLine);
            }
        }
        if (func.arguments == null) {
            if (args != null) {
                System.out.println("Error: too many arguments sent to function\n");
                HelperFunctions.printError(myLine);
            }
        }

        /*check arguments according to sig*/
        if (!(func.arguments == null && args == null))
            funcCallSemanter(args, func); /*will printError if fails*/

        return func.returnType;

    }

    /*validate the args according to function signature*/
    public static void funcCallSemanter(AST_EXP_LIST args, TYPE_FUNCTION func) {
        AST_EXP_LIST argRunner = args;
        TYPE_LIST funcRunner = func.arguments;
        while (argRunner != null && funcRunner != null) {
            /*check if curr arg is compatible with the curr func arg*/
            TYPE currArgType = argRunner.head.SemantMe();
            TYPE currFuncType = funcRunner.head;
            /*compare array types*/
            if (currArgType.isArray() && currFuncType.isArray()) {
                currArgType = ((TYPE_ARRAY) currArgType).type;
                currFuncType = ((TYPE_ARRAY) currFuncType).type;
            }
            if (currArgType != currFuncType) { /*check inheritane or nil*/
                if (!HelperFunctions.isSameOrInheritorOrValidNil(currArgType, currFuncType)) {  /*father or nil checks*/
                    System.out.println("Error: invalid parameter type sent to function\n");
                    HelperFunctions.printError(argRunner.head.myLine);
                }

            }
            /*get next arg*/
            argRunner = argRunner.tail;
            funcRunner = funcRunner.tail;
        }

        /*if we got to the end on both at the same time we have the same number of args*/
        if (argRunner != null) {
            System.out.println("Error: too many arguments sent to function");
            HelperFunctions.printError(args.myLine);
        }
        if (funcRunner != null) {
            System.out.println("Error: too few arguments sent to function");
            HelperFunctions.printError(args.myLine);
        }

    }

    /*TODO useless remove*/
    @Override
    public TYPE getExpType() {
        return null;
    }
}
