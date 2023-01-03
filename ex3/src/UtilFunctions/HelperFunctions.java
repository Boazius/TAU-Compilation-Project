package UtilFunctions;

import AST.AST_CFIELD_LIST;
import AST.AST_FUNC_LIST;
import AST.AST_Node;
import AST.AST_PROGRAM;
import AST.DEC.*;
import AST.EXP.AST_EXP_SINGLE;
import AST.VAR.AST_VAR_LIST;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

import java.io.PrintWriter;

public class HelperFunctions {
    public static PrintWriter file_writer = null;

    public static void setFileWriter(PrintWriter writer) {
        HelperFunctions.file_writer = writer;
    }

    public static void printError(int lineNum) {
        System.out.println("ERROR(" + lineNum + ")");
        file_writer.write("ERROR(" + lineNum + ")\n");
        file_writer.close();
        System.exit(0);
    }


    /*splits the list of fields in a AST_DEC_CLASS object into variables and methods */
    public static void organizeClassDecsInAst(AST_PROGRAM root) {
        AST_DEC_LIST runner = root.decList;
        AST_CFIELD_LIST classRunner;
        while (runner != null) {
            if (runner.head instanceof AST_DEC_CLASS) {
                AST_DEC_CLASS head = (AST_DEC_CLASS) runner.head;
                classRunner = head.cfieldList;

                AST_VAR_LIST varList = head.varList;
                AST_FUNC_LIST funcList = head.funcList;

                while (classRunner != null) {

                    AST_DEC currDec = classRunner.head.dec;

                    if (currDec instanceof AST_DEC_VAR) {
                        AST_VAR_LIST newVarList = new AST_VAR_LIST((AST_DEC_VAR) currDec, null);
                        if (varList == null) {
                            varList = newVarList;
                            head.varList = varList;
                        } else {
                            varList.tail = newVarList;
                            varList = varList.tail;
                        }
                    } else {
                        AST_FUNC_LIST newFuncList = new AST_FUNC_LIST((AST_DEC_FUNC) currDec, null);
                        if (funcList == null) {
                            funcList = newFuncList;
                            head.funcList = funcList;
                        } else {
                            funcList.tail = newFuncList;
                            funcList = funcList.tail;
                        }
                    }

                    classRunner = classRunner.tail;
                }
                head.left = head.varList;
                head.right = head.funcList;
            }
            runner = runner.tail;
        }
    }


    /*remove empty decs from AST*/
    private static void reduceSingleNodeDec(AST_PROGRAM root) {
        AST_DEC_LIST runner = root.decList;
        while (runner != null) {
            if (runner.head instanceof AST_DEC_SINGLE)
                runner.head = ((AST_DEC_SINGLE) runner.head).dec;
            runner = runner.tail;
        }
    }

    public static void reduceDecs(AST_PROGRAM root) {
        reduceSingleNodeDec(root);
        for (AST_Node node : root.decList) {
            reduceDecsRec(node);
        }
    }

    private static void reduceDecsRec(AST_Node node) {
        if (node == null) return;
        AST_Node left = node.left;
        AST_Node right = node.right;
        if (left != null) {
            if (left instanceof AST_EXP_SINGLE) {
                node.left = ((AST_EXP_SINGLE) left).exp;
            }
        }
        if (right != null) {
            if (right instanceof AST_EXP_SINGLE) {
                node.right = ((AST_EXP_SINGLE) right).exp;
            }
        }
        reduceDecsRec(node.left);
        reduceDecsRec(node.right);
    }


    /*convert str to TYPE for int, string, or symbol table thing*/
    public static TYPE findTypeByString(String str) {
        TYPE t;
        if (str.equals("int"))
            return TYPE_INT.getInstance();
        else if (str.equals("string"))
            return TYPE_STRING.getInstance();
        else {
            t = SYMBOL_TABLE.getInstance().find(str);
            return t;
        }
    }

    /*class inheritance functions*/
    public static boolean isSonOf(TYPE son, TYPE father) {
        if (son == null || father == null)
            return false;
        if (!(son.isClass()) || !(father.isClass()))
            return false;
        TYPE_CLASS sonClass = (TYPE_CLASS) son;
        TYPE_CLASS fatherClass = (TYPE_CLASS) father;
        TYPE_CLASS ext = sonClass.father;
        while (ext != null) {
            if (ext == fatherClass)
                return true;
            ext = ext.father;
        }
        return false;
    }

    public static boolean biDirectionalIsSonOf(TYPE a, TYPE b) {
        return isSonOf(a, b) || isSonOf(b, a);
    }


    public static boolean isIntOrString(TYPE a) {
        return a == TYPE_INT.getInstance() || a == TYPE_STRING.getInstance();
    }

    public static boolean isSameOrInheritorOrValidNil(TYPE a, TYPE b) {
        if (a == b) return true;
        if (isSonOf(a, b)) return true;
        return a == TYPE_NIL.getInstance() && !isIntOrString(b);

    }


}
