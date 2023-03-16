package AST;

import IR.IR;
import IR.IRcommand;
import IR.IRcommand_virtual_call;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;
import TEMP.TEMP_LIST;
import TYPES.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AST_Node {

    public static Map<String, String> offsets = new HashMap<>();
    public static Map<String, Integer> classSize = new HashMap<>();
    public static Map<String, Map<String, Integer>> classFuncsOff = new HashMap<>();
    public static Map<String, String> defaultFields = new HashMap<>();
    public static Map<String, ArrayList<String>> classfields = new HashMap<>();
    public static int varsInFunc;
    private static String file;
    /*******************************************/
    public int SerialNumber;
    public int line;
    public String typeName;

    public static int GetOffset(String name) // for local vars only!
    {
        return Integer.parseInt(offsets.get(name));
    }

    public static int getClassSize(String name) {
        return classSize.get(name);
    }

    public void PrintMe() {
        System.out.print("AST NODE UNKNOWN\n");
    }

    public TYPE SemantMe() {
        System.out.println("DEFAULT SEMANTME!!!");
        return null;
    }

    public TYPE_LIST SemantMe(int ignore) {
        System.out.println("DEFAULT SEMANTME!!!");
        return null;
    }

    public TEMP_LIST IRme(int ignore) {
        System.out.println("DEFAULT SEMANTME!!!");
        return null;
    }

    public TEMP IRme() {
        System.out.println("DEFAULT IRme!!!");
        return null;
    }

    public void getFile(String file) {
        AST_Node.file = file;
    }

    public void printError(int line) {
        try {
            PrintWriter file_writer;
            file_writer = new PrintWriter(file);
            file_writer.print("ERROR");
            file_writer.print("(");
            file_writer.print(line);
            file_writer.print(")");
            file_writer.close();
            System.exit(0);
        } catch (Exception ignored) {
        }
    }

    /*t2 is descendant of t1*/
    public boolean isExtendingClass(TYPE t1, TYPE t2) /*can i t1 :=t2*/ {
        if (t2.isClass() && t1.isClass()) {
            if (t1.name.equals(t2.name)) return true;

            TYPE_CLASS father = ((TYPE_CLASS) t2).father;
            while (father != null) {
                if (father.name.equals(t1.name)) return true;
                father = father.father;
            }

            // class wasnt declared yet
            String cllass = SYMBOL_TABLE.getInstance().inClassScope();
            if (cllass != null && cllass.equals(t2.name) && t1.isClass()) {
                String fatherName = SYMBOL_TABLE.getInstance().findExtendsClass(t2.name);
                if (fatherName.equals(t1.name)) return true;
                TYPE_CLASS fatherC = ((TYPE_CLASS) SYMBOL_TABLE.getInstance().find(fatherName)).father;
                while (fatherC != null) {
                    if (fatherC.name.equals(t1.name)) return true;
                    fatherC = fatherC.father;
                }
            }
        }
        return false;
    }

    public boolean isArrayAssignable(TYPE t1, TYPE t2) /*can i t1:=t2*/ {
        if (t1.isArray() && t2.isArray()) {
            if (t1 instanceof TYPE_NIL || t2 instanceof TYPE_NIL) return true;
            if (t1.name.equals(t2.name)) //same type
                return true;
            // array t1 = int[]; t1 = new int[5];
            return t2.name.equals(((TYPE_ARRAY) t1).entryType.name + "[]");

        }
        return false;

    }

    /*can we assign t2 to a variable of t1?*/
    public boolean isAssignable(TYPE t1, TYPE t2) {
        if (t1.isFunc() || t2.isFunc() || t1 instanceof TYPE_VOID || t2 instanceof TYPE_VOID) return false;
        // primitive case
        if (t1 == t2) {
            return true;
        }
        /*not equal but primitive? thats bad*/
        if (t1 == TYPE_INT.getInstance() || t1 == TYPE_STRING.getInstance() || t1 == TYPE_VOID.getInstance() || t2 == TYPE_INT.getInstance() || t2 == TYPE_STRING.getInstance() || t2 == TYPE_VOID.getInstance()) {
            return false;
        }
        if (t2 instanceof TYPE_NIL) {
            return t1.isArray() || t1.isClass();
        }
        if (t1.isArray() && t2.isArray()) {
            if (t1.name.equals(t2.name)) //same type
                return true;
            if (t2.name.equals(((TYPE_ARRAY) t1).entryType.name + "[]")) // array t1 = int[]; t1 = new int[5];
                return true;
        }
        if (t1.isClass() && t2.isClass() && t1.name.equals(t2.name)) return true;

        return isExtendingClass(t1, t2);

    }

    /*t1 is the same type as t2*/
    public boolean type_equals(TYPE t1, TYPE t2) {
        // primitive case
        if (t1 == t2) {
            return true;
        }

        if (t1 == TYPE_INT.getInstance() || t1 == TYPE_STRING.getInstance() || t1 == TYPE_VOID.getInstance() || t2 == TYPE_INT.getInstance() || t2 == TYPE_STRING.getInstance() || t2 == TYPE_VOID.getInstance()) {
            return false;
        }

        // non primitive
        if ((t1 instanceof TYPE_NIL) || (t2 instanceof TYPE_NIL)) {
            return true;
        }

        // array case
        if (t1.isArray() && t2.isArray() && t1.name.equals(t2.name)) {
            return true;
        }


        // class case ...
        return t1.isClass() && t2.isClass() && t1.name.equals(t2.name);
    }

    public TYPE funcSig(String id, AST_EXPLIST list, int line) {
        TYPE type = SYMBOL_TABLE.getInstance().isRealFunc(id, list);

        if (type == null) {
            System.out.format(">> ERROR [%d] " + id + " is not a function or the parameters given are wrong!", line);
            printError(line);
        }
        return type;
    }


    // find only for types!!!
    public TYPE findType(String name) {
        TYPE t = SYMBOL_TABLE.getInstance().find(name);

        // if class was already declared then t!=null
        if (t == null) {
            // check if its equal class boundary before class was declared
            String cl = SYMBOL_TABLE.getInstance().inClassScope();
            if (cl != null && cl.equals(name)) {
                // only comes here BEFORE we declare the class!
                t = new TYPE_CLASS(null, name, null, null);
            }
        }
        return t;
    }

    public TYPE isFuncOfClass(String className, String funcName, AST_EXPLIST funcArgs, int line) {

        if (SYMBOL_TABLE.getInstance().inClassScope() != null && SYMBOL_TABLE.getInstance().inClassScope().equals(className)) { // should find func in current class scope (going
            // up till class and search for func)
            TYPE f = SYMBOL_TABLE.getInstance().findInClassScope(funcName);
            if (f instanceof TYPE_FUNCTION) return funcSig(funcName, funcArgs, line);
            // inheritance case
            String extendClass = SYMBOL_TABLE.getInstance().findExtendsClass(className);
            if (extendClass != null) {
                TYPE_CLASS father = (TYPE_CLASS) (SYMBOL_TABLE.getInstance().find(className));
                while (father != null) {
                    AST_TYPE_NAME_LIST funcs = father.functions;
                    for (AST_TYPE_NAME_LIST it = funcs; it != null; it = it.tail) {
                        if (it.head.name.equals(funcName))
                            return SYMBOL_TABLE.getInstance().compareFuncs((TYPE_FUNCTION) it.head.type, funcArgs, line);
                    }
                    father = father.father;
                }
            }
            return null;
        }

        // class was already declared
        TYPE cl = SYMBOL_TABLE.getInstance().find(className);
        if (cl == null || !(cl.isClass())) // there isnt such a class
        {
            System.out.println(">> ERROR [" + line + "] there isnt such a class!");
            printError(line);
        }

        TYPE_CLASS currClass = (TYPE_CLASS) cl;
        TYPE_FUNCTION a = null;
        while (currClass != null) {
            for (AST_TYPE_NAME_LIST it = currClass.functions; it != null; it = it.tail) {
                if (it.head.name.equals(funcName)) {
                    a = (TYPE_FUNCTION) it.head.type;
                    break;
                }
            }
            currClass = currClass.father;
        }

        if (a == null) // no such func
        {
            System.out.println(">> ERROR [" + line + "] there isnt such a func!");
            printError(line);
        }
        // should compare a params to func args - getting func args type by semant me
        return SYMBOL_TABLE.getInstance().compareFuncs(a, funcArgs, line);

    }

    public int localsInIfOrWhile(AST_STMT scope) {
        // return how many variable declarations we have in an if or while scope
        AST_STMT_LIST body;
        if (scope instanceof AST_STMT_IF) body = ((AST_STMT_IF) scope).body;
        else body = ((AST_STMT_WHILE) scope).body;
        int result = 0;
        for (AST_STMT_LIST it = body; it != null; it = it.tail) {
            if (it.head instanceof AST_STMT_VARDEC) result += 1;

            if (it.head instanceof AST_STMT_IF || it.head instanceof AST_STMT_WHILE) {
                result += localsInIfOrWhile(it.head);
            }
        }
        return result;
    }

    public void ifScope(AST_STMT_LIST body) {
        Map<String, String> temps = new HashMap<>();
        int varCnt = varsInFunc;
        for (AST_STMT_LIST it = body; it != null; it = it.tail) {
            if (it.head instanceof AST_STMT_VARDEC) {
                varCnt += 1;
                AST_STMT_VARDEC a = (AST_STMT_VARDEC) (it.head);
                AST_VARDEC b = a.v;
                if (offsets.get(b.id) != null) temps.put(b.id, offsets.get(b.id));
                offsets.put(b.id, String.valueOf(varCnt * (-4) + -40));
            }
            if (it.head instanceof AST_STMT_IF || it.head instanceof AST_STMT_WHILE) {
                varsInFunc = varCnt;
                varCnt += localsInIfOrWhile(it.head);
            }
            it.head.IRme();
        }

        offsets.putAll(temps);
    }

    public TEMP vardotIR(AST_VAR var, AST_EXPLIST list, TYPE_CLASS tl, String id) {
        TEMP varAddress = var.IRme();
        TEMP_LIST resTempsList = null;

        // set resTempList
        for (AST_EXPLIST it = list; it != null; it = it.tail) {
            TEMP curr = it.head.IRme();
            resTempsList = new TEMP_LIST(curr, resTempsList);
        }

        // reverse list
        if (resTempsList != null) {
            resTempsList = resTempsList.reverseList();
        }

        TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();

        IRcommand ic = new IRcommand_virtual_call(t, varAddress, id, resTempsList);
        ic.offset = classFuncsOff.get(tl.name).get(id);
        IR.getInstance().Add_IRcommand(ic);
        return t;
    }

}
