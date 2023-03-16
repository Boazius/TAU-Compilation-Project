package AST;

import IR.*;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;
import TYPES.TYPE;
import TYPES.TYPE_CLASS;

public class AST_VAR_SIMPLE extends AST_VAR {

    public String name;
    public String className = null;
    public TYPE thisT;
    public boolean cfgVar = false;
    int inGlobal = 0; // needed for IRme

    public AST_VAR_SIMPLE(String name, int line) {
        this.line = line;
        this.name = name;

        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.format("var -> ID\n");
    }

    public void PrintMe() {
        System.out.format("AST NODE SIMPLE_VAR( %s )\n", name);

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("SIMPLE_VAR(%s)", name));
    }

    public TYPE SemantMe() {

        String currScope = SYMBOL_TABLE.getInstance().getScope();
        className = SYMBOL_TABLE.getInstance().inClassScope();
        TYPE res = null;
        /* if current scope is global*/
        if (currScope.equals("global")) {
            /* find var in global scope and check that it isnt a func/class name */
            res = SYMBOL_TABLE.getInstance().findInstance(name);
            inGlobal = 1;
        }
        /* if current scope is class*/
        else if (className != null) {
            /*if we are in class method*/
            if (SYMBOL_TABLE.getInstance().inFuncScope()) {
                res = SYMBOL_TABLE.getInstance().findInFuncScope(name);
                if (res == null) {
                    /*find variable in current class and return the type*/
                    res = SYMBOL_TABLE.getInstance().findInClassScope(name);
                    if (res == null) {
                        /*if not found, find variable in some ancestor*/
                        String fatherName = SYMBOL_TABLE.getInstance().findExtendsClass(className);
                        if (fatherName != null) {
                            TYPE_CLASS fatherClass = (TYPE_CLASS) SYMBOL_TABLE.getInstance().find(fatherName);
                            while (fatherClass != null) {
                                for (AST_ARG_LIST it = fatherClass.data_members; it != null; it = it.tail) {
                                    if (it.head.id.equals(name)) {
                                        String resName = it.head.type.typeName;
                                        return SYMBOL_TABLE.getInstance().find(resName);
                                    }
                                }
                                fatherClass = fatherClass.father;
                            }
                        }
                        /*if not found, find var in global and check that it isnt a func/class name */
                        res = SYMBOL_TABLE.getInstance().findInstance(name);
                        inGlobal = 1;
                    }

                }
            } else /*just class, not a class method*/ {
                /*find variable in current class and return the type*/
                res = SYMBOL_TABLE.getInstance().findInClassScope(name);
                if (res == null) {
                    /*if not found, find variable in some ancestor*/
                    String fatherName = SYMBOL_TABLE.getInstance().findExtendsClass(className);
                    if (fatherName != null) {
                        TYPE_CLASS fatherClass = (TYPE_CLASS) SYMBOL_TABLE.getInstance().find(fatherName);
                        while (fatherClass != null) {
                            for (AST_ARG_LIST it = fatherClass.data_members; it != null; it = it.tail) {
                                if (it.head.id.equals(name)) {
                                    String resName = it.head.type.typeName;
                                    return SYMBOL_TABLE.getInstance().find(resName);
                                }
                            }
                            fatherClass = fatherClass.father;
                        }
                    }
                    /*if not found, find var in global and check that it isnt a func/class name */
                    res = SYMBOL_TABLE.getInstance().findInstance(name);
                    inGlobal = 1;
                }
            }
        }
        /* if current scope is func*/
        else if (SYMBOL_TABLE.getInstance().inFuncScope()) {
            /*find variable in current func and return the type*/

            res = SYMBOL_TABLE.getInstance().findInFuncScope(name);
            if (res == null) {
                /*if not found, find var in global and check that it isnt a func/class name */
                res = SYMBOL_TABLE.getInstance().findInstance(name);
                inGlobal = 1;

            }
        }

        /*still haven't found this var*/
        if (res == null) {
            System.out.println(">> ERROR[" + line + "] unknown variable");
            printError(line);
        } else if (res.isFunc()) {
            System.out.println(">> ERROR[" + line + "] variable name cant be function");
            printError(line);
        }
        thisT = res;
        return res;
    }

    public TEMP IRme() {
        System.out.format("VAR SIMPLE - IRme (%s)\n", name);

        TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();

        if (inGlobal == 1) {
            IR.getInstance().Add_IRcommand(new IRcommand_Load_Global(t, name));
        } else {
            String realN = name;
            boolean c = false;
            IRcommand command;
            if (className != null && offsets.get(name) == null) {
                c = true;
                realN = className + "_" + name;
            }
            if (c && (!(thisT instanceof TYPE_CLASS) || !(thisT.name.equals(className)))) {                                                                                                                                                                                                            // itself
                command = new IRcommand_ThisDotField(realN, t);
                ((IRcommand_ThisDotField) command).cfg = cfgVar;
            } else
                command = new IRcommand_Load_Local(realN, t);

            command.offset = GetOffset(realN);
            IR.getInstance().Add_IRcommand(command);
        }
        return t;
    }
}