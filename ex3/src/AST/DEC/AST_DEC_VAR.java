package AST.DEC;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import AST.AST_TYPE;
import AST.EXP.AST_EXP;
import AST.EXP.AST_EXP_INT;
import AST.EXP.AST_EXP_NIL;
import AST.EXP.AST_EXP_STRING;
import AST.STMT.AST_STMT_ASSIGN;
import UtilFunctions.HelperFunctions;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

public class AST_DEC_VAR extends AST_DEC {

    AST_TYPE typeNode;
    String id;
    AST_EXP exp;

    public AST_DEC_VAR(AST_TYPE typeNode, String id, AST_EXP exp) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        /*print derivation rules*/
        if (exp != null)
            System.out.format("varDec -> TYPE ID(%s) := EXP;\n", id);
        else
            System.out.format("varDec -> TYPE ID(%s);\n", id);


        this.typeNode = typeNode;
        this.id = id;
        this.exp = exp;
    }

    public void PrintMe() {
        /*System.out.format("AST_DEC_VAR ID(%s)\n", id);*/


        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        if (typeNode != null) typeNode.PrintMe();
        if (exp != null) exp.PrintMe();

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("VarDec ID(%s)", id));

        if (typeNode != null)
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, typeNode.SerialNumber);
        if (exp != null)
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
    }


    /*TODO*/
    public TYPE SemantMe() {
        /****************************/
        /* [1] Check If Type exists */
        /****************************/
        TYPE t = typeNode.SemantMe();

        /*check if type is not void, var cannot be void*/
        if (t instanceof TYPE_VOID) {
            System.out.println("Error: variable cannot be type void\n");
            HelperFunctions.printError(myLine);
        }

        /*check if variable name is already a type (like "string" or something)*/
        TYPE nameType = HelperFunctions.findTypeByString(this.id);
        if (nameType != null) {
            if (nameType.name.equals(this.id)) {
                System.out.println("Error: illegal name var\n");
                HelperFunctions.printError(myLine);
            }
        }

        /**************************************/
        /* [2] Check That Name does NOT exist */
        /**************************************/
        if (SYMBOL_TABLE.getInstance().findInCurrScope(id) != null) {
            System.out.format("ERROR variable %s already exists in scope\n", id);
            HelperFunctions.printError(this.myLine);
        }

        if (exp != null) {
            /*check if we can assign the exp into the dec*/
            TYPE expType = exp.SemantMe();
            if (!AST_STMT_ASSIGN.assignmentChecker(t, expType)) {
                System.out.format("ERROR type mismatch for var %s := exp\n", id);
                HelperFunctions.printError(exp.myLine);
            }
        }

        SYMBOL_TABLE.getInstance().enter(id, t);

        return t;
    }

    public TYPE cSemantMe(String containingClassName) {
        /****************************/
        /* [1] Check If Type exists */
        /****************************/
        TYPE t = typeNode.SemantMe();
        TYPE_CLASS_VAR_DEC newDec = null;

        /*check if type is not void, var cannot be void*/
        if (t instanceof TYPE_VOID) {
            System.out.println("Error: variable cannot be type void\n");
            HelperFunctions.printError(myLine);
        }

        /**************************************/
        /* [2] Check That Name does NOT exist */
        /**************************************/
        if (SYMBOL_TABLE.getInstance().findInCurrScope(this.id) != null) {
            System.out.format("ERROR variable %s already exists in scope\n", this.id);
            HelperFunctions.printError(this.myLine);

        }

        /*get the class that contains this var declaration*/
        TYPE_CLASS containingClass = (TYPE_CLASS) SYMBOL_TABLE.getInstance().find(containingClassName);

        /*if class has this variable already*/
        for (TYPE_LIST typeList = containingClass.data_members; typeList != null; typeList = typeList.tail) {
            TYPE_CLASS_VAR_DEC varDec = (TYPE_CLASS_VAR_DEC) typeList.head;
            if (varDec != null) {
                if (varDec.name.equals(this.id)) {
                    System.out.println("Error: variable shadowing is not allowed\n");
                    HelperFunctions.printError(this.myLine);
                }
            }
        }

        /*check exp is valid type*/
        if (exp instanceof AST_EXP_INT) {
            if (!(t instanceof TYPE_INT)) { /*primitive int assignment*/
                System.out.format("ERROR assignment to variable %s is invalid type\n", this.id);
                HelperFunctions.printError(exp.myLine);
            } else {
                newDec = new TYPE_CLASS_VAR_DEC(TYPE_INT.getInstance(), this.id);
            }
        } else if (exp instanceof AST_EXP_STRING) {
            if (!(t instanceof TYPE_STRING)) { /*primitive string assignment*/
                System.out.format("ERROR assignment to variable %s is invalid type\n", this.id);
                HelperFunctions.printError(exp.myLine);
            } else {
                newDec = new TYPE_CLASS_VAR_DEC(TYPE_STRING.getInstance(), this.id);
            }
        } else if (exp instanceof AST_EXP_NIL) { /*Nil assign */
            if (HelperFunctions.isIntOrString(t)) {
                System.out.format("ERROR assignment to variable %s is invalid type\n", this.id);
                HelperFunctions.printError(exp.myLine);
            } else {
                newDec = new TYPE_CLASS_VAR_DEC(t, this.id);
            }
        } else if (exp == null) {
            newDec = new TYPE_CLASS_VAR_DEC(t, this.id);
        }
        containingClass.data_members.add(newDec);


        /***************************************************/
        /* [3] Enter the Variable to the Symbol Table */
        /***************************************************/
        SYMBOL_TABLE.getInstance().enter(this.id, t);

        return newDec;
    }


}
