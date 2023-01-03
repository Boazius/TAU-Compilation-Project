package AST.DEC;

import AST.*;
import UtilFunctions.HelperFunctions;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_CLASS;
import TYPES.TYPE_FUNCTION;
import TYPES.TYPE_LIST;

public class AST_FUNC_SIG extends AST_Node {

    public AST_TYPE type;
    public String name;
    public AST_ID_LIST idList;

    public AST_FUNC_SIG(AST_TYPE type, String name, AST_ID_LIST idList) {
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.format("funcDec -> TYPE( %s ) NAME(%s)\n", type.type, name);

        this.type = type;
        this.name = name;
        this.idList = idList;
        left = idList;

    }


    public void PrintMe() {

        /*System.out.format("FuncDecSig ( %s ) (%s)\n", type.type, name);*/

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        if (type != null) type.PrintMe();
        if (idList != null) idList.PrintMe();
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Function Signature %s", name));
        if (type != null)
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
        if (idList != null)
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, idList.SerialNumber);

    }

    /*semantically check func signature for global function*/
    @Override
    public TYPE SemantMe() {

        TYPE typeOfCurrNode;
        AST_TYPE astType;
        TYPE_LIST type_list = null;

        /*test type is valid*/
        TYPE returnType = type.SemantMe();

        /*test if global function exists already*/
        if (SYMBOL_TABLE.getInstance().find(name) != null) {
            System.out.format("ERROR function %s has already been declared\n", name);
            HelperFunctions.printError(myLine);
        }

        /*TODO check if redundant later by tests*/
        if (returnType == null) {
            System.out.format("ERROR non existing return type for function %s\n", name);
            HelperFunctions.printError(myLine);
        }

        /*function arguments check*/
        /*check if types are valid, and TODO variable names do not shadow, and then add them..*/
        if (idList != null) {
            type_list = new TYPE_LIST(null, null);
            /*check every id in signature*/
            for (AST_Node node : idList) {
                AST_DEC_VAR var = (AST_DEC_VAR) node;
                astType = var.typeNode;
                typeOfCurrNode = astType.SemantMe(); /*get type of var, and throw error if bad type*/
                type_list.add(typeOfCurrNode);
                SYMBOL_TABLE.getInstance().enter(var.id, typeOfCurrNode);
            }
        }

        /***************************************************/
        /* [5] Enter the Function Type to the Symbol Table */
        /***************************************************/

        TYPE_FUNCTION newFuncDec = new TYPE_FUNCTION(returnType, this.name, type_list);

        SYMBOL_TABLE.getInstance().enter(this.name, newFuncDec);

        /*return TYPE_FUNCTION*/
        return newFuncDec;

    }

    public TYPE cSemantMe(String className) {

        TYPE typeOfCurrNode;
        AST_TYPE astType;
        TYPE_LIST type_list = null;

        /*test type is valid*/
        TYPE returnType = type.SemantMe();

        /*test if class/global function exists already*/
        if (SYMBOL_TABLE.getInstance().find(name) != null) {
            System.out.format("ERROR function of name %s has already been declared\n", name);
            HelperFunctions.printError(myLine);
        }

        /*TODO check if redundant later using tests*/
        if (returnType == null) {
            System.out.format("ERROR non existing return type for function %s\n", name);
            HelperFunctions.printError(myLine);
        }

        /*function arguments check*/
        /*check if types are valid, and TODO variable names do not shadow, and then add them..*/
        if (idList != null) {
            type_list = new TYPE_LIST(null, null);
            /*check every node in signature */
            for (AST_Node node : idList) {
                AST_DEC_VAR var = (AST_DEC_VAR) node;
                astType = var.typeNode;
                typeOfCurrNode = astType.SemantMe(); /*get type of var, and throw error if bad type*/
                type_list.add(typeOfCurrNode);
                SYMBOL_TABLE.getInstance().enter(var.id, typeOfCurrNode);
            }
        }

        /*if the function exists in father*/
        TYPE_CLASS containingClass = (TYPE_CLASS) SYMBOL_TABLE.getInstance().find(className);

        if (containingClass.localFuncs.findInList(this.name) != null) {
            System.out.format("Error same function %s declaration twice\n",name);
            HelperFunctions.printError(this.myLine);
        }


        /*check overloading from father class*/
        TYPE_FUNCTION overloadedFunc = (TYPE_FUNCTION) containingClass.function_list.findInList(this.name);
        if (overloadedFunc != null) { /*this function overrides a father function*/
            /*different return types*/
            if (overloadedFunc.returnType != returnType) {
                System.out.format("ERROR function overloading %s different return types\n",name);
                HelperFunctions.printError(myLine);
            } else {
                /*different args*/
                if (type_list == null) {
                    if (overloadedFunc.arguments != null) {
                        System.out.format("ERROR function overloading %s different args\n", name);
                        HelperFunctions.printError(myLine);
                    }
                } else if (!type_list.compareFuncArgsByType(containingClass.function_list)) {
                    System.out.format("ERROR function overloading %s different args\n",name);
                    HelperFunctions.printError(myLine);
                }
            }
        }


        /***************************************************/
        /* [5] Enter the Function Type to the Symbol Table */
        /***************************************************/

        TYPE_FUNCTION newFuncDec = new TYPE_FUNCTION(returnType, this.name, type_list);

        SYMBOL_TABLE.getInstance().enter(name, newFuncDec);
        containingClass.localFuncs.add(newFuncDec);

        /*return TYPE_FUNCTION*/
        return newFuncDec;

    }
}
