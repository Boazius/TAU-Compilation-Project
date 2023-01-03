package AST.STMT;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import AST.EXP.AST_EXP_LIST;
import AST.EXP.AST_EXP_METHOD;
import AST.VAR.AST_VAR;
import UtilFunctions.HelperFunctions;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_CLASS;
import TYPES.TYPE_FUNCTION;
import TYPES.TYPE_LIST;

public class AST_STMT_METHOD extends AST_STMT {

    public String id;
    public AST_VAR var;
    public AST_EXP_LIST args;

    public AST_STMT_METHOD(AST_VAR var, String id, AST_EXP_LIST args) {
        SerialNumber = AST_Node_Serial_Number.getFresh();

        if ((var != null) && (args != null))
            System.out.printf("stmt -> var. %s (expList)\n", id);
        else if (var != null)
            System.out.printf("stmt -> var. %s (expList)\n", id);
        else if (args != null)
            System.out.printf("stmt -> %s (expList)\n", id);
        else
            System.out.printf("stmt -> %s ()\n", id);

        this.var = var;
        this.id = id;
        this.args = args;

        left = var;
        right = args;

    }

    public void PrintMe() {
        /*System.out.format("AST_STMT_METHOD( %s )\n", id);*/
        if (var != null) var.PrintMe();
        if (args != null) args.PrintMe();

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("call for Method: NAME(%s)", id));
        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
        if (args != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, args.SerialNumber);
    }

    @Override
    public TYPE SemantMe() {
        /*if class has the function*/
        TYPE_FUNCTION func = null;
        if (var != null) {
            TYPE c = var.SemantMe();
            /*no class*/
            if (!c.isClass()) {
                System.out.format("Error: calling a method from non-class\n");
                HelperFunctions.printError(this.myLine);
                return null;
            }
            TYPE_CLASS classType = (TYPE_CLASS) c;

            /*compare function args*/
            for (TYPE_LIST runner = classType.function_list; runner != null; runner = runner.tail) {

                TYPE_FUNCTION runnerF = (TYPE_FUNCTION) runner.head;
                if (runnerF.name.equals(this.id))
                    func = (TYPE_FUNCTION) runner.head;
            }

            if (func == null) {
                System.out.format("ERROR: no such func %s in class %s" ,id, classType.name);
                HelperFunctions.printError(this.myLine);
                return null;
            }
        } else {
            func = (TYPE_FUNCTION) SYMBOL_TABLE.getInstance().find(this.id);
            if (func == null) {
                System.out.format("ERROR: no such func %s",id);
                HelperFunctions.printError(this.myLine);
                return null;
            }
        }

        /*no arguments*/
        if (args == null) {
            if (func.arguments != null) {
                System.out.format("Error: not enough arguments sent to function\n");
                HelperFunctions.printError(myLine);
            }
        }
        if (func.arguments == null) {
            if (args != null) {
                System.out.format("Error: too many arguments sent to function\n");
                HelperFunctions.printError(myLine);
            }
        }
        if (!(func.arguments == null && args == null))
            AST_EXP_METHOD.funcCallSemanter(args, func);
        return func.returnType;
    }
}
