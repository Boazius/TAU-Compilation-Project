package AST;

import IR.*;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TEMP.TEMP;
import TYPES.*;

public class AST_FUNCDEC extends AST_Node {
    public AST_TYPE returnType;
    public String id;
    public AST_ARG_LIST arglist;
    public AST_STMT_LIST list;
    public String className; // IRme
    public TYPE_FUNCTION func; // IRme

    public AST_FUNCDEC(AST_TYPE returnType, String id, AST_ARG_LIST arglist, AST_STMT_LIST list, int line) {
        this.returnType = returnType;
        this.id = id;
        this.arglist = arglist;
        this.list = list;
        this.line = line;

        SerialNumber = AST_Node_Serial_Number.getFresh();

    }

    public void PrintMe() {

        System.out.format("AST %s NODE\n", "AST_FUNCDEC");

        if (returnType != null) {
            returnType.PrintMe();
        }
        if (arglist != null) {
            arglist.PrintMe();
        }
        if (list != null) {
            list.PrintMe();
        }

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("FUNCDEC(%s)\n return type, func_name", id));


        if (returnType != null) {
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, returnType.SerialNumber);
        }
        if (arglist != null) {
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, arglist.SerialNumber);
        }
        if (list != null) {
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, list.SerialNumber);
        }
    }

    public TYPE SemantMe() {

        System.out.println("FUNCDEC- semantme(" + id + ")");

        TYPE returnTypeType = null;
        TYPE_LIST argListTypes = null;
        TYPE t;

        returnTypeType = findType(returnType.typeName);

        if (returnTypeType == null || returnTypeType instanceof TYPE_NIL) {
            System.out.format(">> ERROR[%d] non existing return type %s\n", line, returnType.typeName);
            printError(line);
        }

        for (AST_ARG_LIST it = arglist; it != null; it = it.tail) {
            t = findType(it.head.type.typeName);

            if (t == null) {
                System.out.format(">> ERROR[%d] non existing type %s\n", line, it.head.type.typeName);
                printError(line);
            }

            if (t instanceof TYPE_NIL || t instanceof TYPE_VOID) {
                System.out.format(">> ERROR[%d] cant declare function with nil/void", line);
                printError(line);
            }
            for (AST_ARG_LIST it2 = arglist; it2 != null && it2 != it; it2 = it2.tail) {
                if (it.head.id.equals(it2.head.id)) {
                    System.out.format(">> ERROR[%d] 2 args with the same name", line);
                    printError(line);
                }
            }

            argListTypes = new TYPE_LIST(t, argListTypes);
        }

        if (argListTypes != null) {
            argListTypes = argListTypes.reverseList();
        }


        this.className = SYMBOL_TABLE.getInstance().inClassScope();


        /*If class function*/
        if (className != null) {
            /*if this function extends a father*/
            String father = SYMBOL_TABLE.getInstance().findExtendsClass(className);
            if (father != null) {
                /*check if this function overloads a valid function in the father classes*/
                boolean isOverloadingFunction = false;
                TYPE_CLASS fatherClass = (TYPE_CLASS) SYMBOL_TABLE.getInstance().find(father);
                while (fatherClass != null && !isOverloadingFunction) {
                    //get all functions, and for each one check if same name.:
                    AST_TYPE_NAME_LIST funcs = fatherClass.functions;
                    for (AST_TYPE_NAME_LIST it = funcs; it != null; it = it.tail) {
                        TYPE_FUNCTION currF = (TYPE_FUNCTION) it.head.type;
                        // check if func name in father class is the same
                        if (currF.name.equals(id)) {
                            if (returnTypeType != null && !(currF.returnType.name.equals(returnTypeType.name))) { //but return type is different
                                System.out.println(">> ERROR [" + line + "] cant overwrite the function!");
                                printError(line);
                            }
                            TYPE_LIST params = currF.params;
                            // check if all function parameters are the same for the overload
                            for (TYPE_LIST it2 = argListTypes; it2 != null; it2 = it2.tail) {
                                if (params == null || params.head == null || !(it2.head.name.equals(params.head.name))) {
                                    System.out.println(">> ERROR [" + line + "] cant overwrite the function!");
                                    printError(line);
                                } else params = params.tail;
                            }
                            // different number of parameters
                            if (params != null) {
                                System.out.println(">> ERROR [" + line + "] cant overwrite the function!");
                                printError(line);
                            }
                            isOverloadingFunction = true; //found an overload.
                            break;
                        }
                    }
                    fatherClass = fatherClass.father;
                }
                /**if not isOverloading function, check all other objects in father(s) class(es) that they dont have same name*/
                if (!isOverloadingFunction) {
                    /*go over all fathers starting from the direct father*/
                    fatherClass = (TYPE_CLASS) SYMBOL_TABLE.getInstance().find(father);
                    while (fatherClass != null) {
                        /*get all objects of this father class*/
                        AST_ARG_LIST objsInFather = fatherClass.data_members;
                        /*check if any object has same name*/
                        for (AST_ARG_LIST it = objsInFather; it != null; it = it.tail) {
                            /*found an object with the same name!*/
                            if (it.head.id.equals(id)) {
                                System.out.println(">> ERROR [" + line + "] function cannot be the same name as data member in extended class");
                                printError(line);
                            }
                        }
                        fatherClass = fatherClass.father;
                    }
                }
            }
            /**check all objects in class scope that they are not the same name*/
            TYPE t2 = SYMBOL_TABLE.getInstance().findInClassScope(id);
            if (t2 != null) {
                System.out.format(">> [%d] ERROR function " + id + " overloading is illegal", line);
                printError(line);
            }
        } else //this is not a class function
        {
            /**check all objects in global scope that they are not the same name*/
            TYPE t3 = SYMBOL_TABLE.getInstance().findInCurrScope(id);
            if (t3 != null) {
                System.out.format(">> [%d] ERROR function " + id + " overloading is illegal", line);
                printError(line);
            }
        }

        this.func = new TYPE_FUNCTION(returnTypeType, id, argListTypes);
        SYMBOL_TABLE.getInstance().enter(id, func);

        /****************************/
        /* [1] Begin Function Scope */
        /****************************/
        if (returnTypeType != null) {
            SYMBOL_TABLE.getInstance().beginScope("func-" + id + "-" + returnTypeType.name);
        }

        for (AST_ARG_LIST it = arglist; it != null; it = it.tail) {
            t = findType(it.head.type.typeName);
            SYMBOL_TABLE.getInstance().enter(it.head.id, t);
        }

        list.SemantMe();

        /*****************/
        /* [4] End Scope */
        /*****************/
        SYMBOL_TABLE.getInstance().endScope();

        return func;
    }

    public TEMP IRme() {
        System.out.println("FUNCDEC" + "- IRme(" + id + ")");

        if (id.equals("main")) {
            this.id = "user_main";
        }

        int argCnt = 0; // number of arguments
        if (className != null) argCnt += 1;

        for (AST_ARG_LIST it = arglist; it != null; it = it.tail) {
            String off = String.valueOf(8 + 4 * argCnt);
            offsets.put(it.head.id, off);
            argCnt += 1;
        }

        /*local vars*/
        int varCnt = 0;
        for (AST_STMT_LIST it = list; it != null; it = it.tail) {
            if (it.head instanceof AST_STMT_VARDEC) {
                varCnt += 1;
                continue;
            }
            if (it.head instanceof AST_STMT_IF || it.head instanceof AST_STMT_WHILE) {
                varCnt += localsInIfOrWhile(it.head);
            }
        }

        String labelStart;
        if (id.equals("user_main")) {
            labelStart = id;
        } else {
            if (className != null) labelStart = className + "_" + id;
            else {
                labelStart = IRcommand.getFreshLabel("start_" + id);
                offsets.put(id, labelStart);
            }
        }

        this.func.startLabel = labelStart;

        IR.getInstance().Add_IRcommand(new IRcommand_Label(labelStart));
        IR.getInstance().Add_IRcommand(new IRcommand_Prologue(varCnt));

        // body
        varCnt = 0;
        for (AST_STMT_LIST it = list; it != null; it = it.tail) {
            if (it.head instanceof AST_STMT_VARDEC) {
                varCnt += 1;
                AST_STMT_VARDEC a = (AST_STMT_VARDEC) (it.head);
                AST_VARDEC b = a.v;
                String off = String.valueOf(varCnt * (-4) + -40);
                offsets.put(b.id, off);
            }
            if (it.head instanceof AST_STMT_IF || it.head instanceof AST_STMT_WHILE) {
                varsInFunc = varCnt;
                varCnt += localsInIfOrWhile(it.head);
            }
            it.head.IRme();
        }

        IR.getInstance().Add_IRcommand(new IRcommand_Epilogue());

        System.out.println(offsets);
        return null;
    }
}
