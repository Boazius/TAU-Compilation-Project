/***********/
/* PACKAGE */
/***********/
package SYMBOL_TABLE;

/*******************/
/* GENERAL IMPORTS */
/*******************/

import AST.AST_EXPLIST;
import AST.AST_TYPE_VOID;
import TYPES.*;

import java.io.PrintWriter;
import java.util.Objects;

/****************/
/* SYMBOL TABLE */

/****************/
public class SYMBOL_TABLE {
    public static int n = 0;

    /**********************************************/
    /* The actual symbol table data structure ... */
    /**************************************/
    private static SYMBOL_TABLE instance = null;
    private int hashArraySize = 13;
    /**********************************************/
    private SYMBOL_TABLE_ENTRY[] table = new SYMBOL_TABLE_ENTRY[hashArraySize];

    /**************************************************************/
    /* A very primitive hash function for exposition purposes ... */
    private SYMBOL_TABLE_ENTRY top;

    /****************************************************************************/
    /* Enter a variable, function, class type or array type to the symbol table */
    private int top_index = 0;

    /****************************************************************************/
    /* Enter a class definition to the symbol table. SAME as enter but isInstance = false */

    /*****************************/
    protected SYMBOL_TABLE() {
    }

    /***********************************************/
    /* Find the inner-most scope element with name */

    /******************************/
    public static SYMBOL_TABLE getInstance() {
        if (instance == null) {
            /*******************************/
            /* [0] The instance itself ... */
            /*******************************/
            instance = new SYMBOL_TABLE();

            /*****************************************/
            /* [1] Enter primitive types int, string */
            /*****************************************/
            instance.enter("int", TYPE_INT.getInstance());
            instance.enter("string", TYPE_STRING.getInstance());

            /*************************************/
            /* [2] How should we handle void ??? */
            /*************************************/
            instance.enter("void", TYPE_VOID.getInstance());
            /***************************************/
            /* [3] Enter library function PrintInt */
            /***************************************/
            instance.enter(
                    "PrintInt",
                    new TYPE_FUNCTION(
                            TYPE_VOID.getInstance(),
                            "PrintInt",
                            new TYPE_LIST(
                                    TYPE_INT.getInstance(),
                                    null)));

            instance.enter(
                    "PrintString",
                    new TYPE_FUNCTION(
                            TYPE_VOID.getInstance(),
                            "PrintString",
                            new TYPE_LIST(
                                    TYPE_STRING.getInstance(),
                                    null)));
        }
        return instance;
    }

    /**************************************************************/
    private int hash(String s) {
        if (s.charAt(0) == 'l') {
            return 1;
        }
        if (s.charAt(0) == 'm') {
            return 1;
        }
        if (s.charAt(0) == 'r') {
            return 3;
        }
        if (s.charAt(0) == 'i') {
            return 6;
        }
        if (s.charAt(0) == 'd') {
            return 6;
        }
        if (s.charAt(0) == 'k') {
            return 6;
        }
        if (s.charAt(0) == 'f') {
            return 6;
        }
        if (s.charAt(0) == 'S') {
            return 6;
        }
        return 12;
    }


    /***************************************************************************/
    /* begine scope = Enter the <SCOPE-BOUNDARY> element to the data structure */

    /****************************************************************************/
    public void enter(String name, TYPE t) {
        /*************************************************/
        /* [1] Compute the hash value for this new entry */
        /*************************************************/
        int hashValue = hash(name);

        /******************************************************************************/
        /* [2] Extract what will eventually be the next entry in the hashed position */
        /* NOTE: this entry can very well be null, but the behaviour is identical */
        /******************************************************************************/
        SYMBOL_TABLE_ENTRY next = table[hashValue];

        /**************************************************************************/
        /* [3] Prepare a new symbol table entry with name, type, next and prevtop */
        /**************************************************************************/
        SYMBOL_TABLE_ENTRY e = new SYMBOL_TABLE_ENTRY(name, t, hashValue, next, top, top_index++);

        /**********************************************/
        /* [4] Update the top of the symbol table ... */
        /**********************************************/
        top = e;

        /****************************************/
        /* [5] Enter the new entry to the table */
        /****************************************/
        table[hashValue] = e;

        /**************************/
        /* [6] Print Symbol Table */
        /**************************/
        PrintMe();
    }

    /********************************************************************************/
    /* end scope = Keep popping elements out of the data structure, */
    /*
     * from most recent element entered, until a <NEW-SCOPE> element is encountered
     */

    /****************************************************************************/
    public void enterClassDec(String name, TYPE t) {
        int hashValue = hash(name);
        SYMBOL_TABLE_ENTRY next = table[hashValue];
        SYMBOL_TABLE_ENTRY e = new SYMBOL_TABLE_ENTRY(name, t, hashValue, next, top, top_index++);
        e.isInstance = false; /*only difference*/
        top = e;
        table[hashValue] = e;
        PrintMe();
    }

    /***********************************************/
    public TYPE find(String name) {
        SYMBOL_TABLE_ENTRY e;

        for (e = table[hash(name)]; e != null; e = e.next) {
            if (name.equals(e.name)) {
                return e.type;
            }
        }

        return null;
    }

    /*same as find, but only returns if isInstance==true*/
    public TYPE findInstance(String name) {
        SYMBOL_TABLE_ENTRY e;
        for (e = table[hash(name)]; e != null; e = e.next) {
            if (name.equals(e.name) && e.isInstance) {
                return e.type;
            }
        }
        return null;
    }

    /**************************************/
    /* USUAL SINGLETON IMPLEMENTATION ... */

    /***************************************************************************/
    public void beginScope(String name) {
        /************************************************************************/
        /* Though <SCOPE-BOUNDARY> entries are present inside the symbol table, */
        /* they are not really types. In order to be ablt to debug print them, */
        /* a special TYPE_FOR_SCOPE_BOUNDARIES was developed for them. This */
        /* class only contain their type name which is the bottom sign: _|_ */
        /************************************************************************/
        String boundary = "SCOPE-BOUNDARY-" + name;
        enter(
                boundary,
                new TYPE_FOR_SCOPE_BOUNDARIES("NONE"));

        /*********************************************/
        /* Print the symbol table after every change */
        /*********************************************/
        PrintMe();
    }

    /*****************************/
    /* PREVENT INSTANTIATION ... */

    /********************************************************************************/
    public void endScope() {
        /**************************************************************************/
        /* Pop elements from the symbol table stack until a SCOPE-BOUNDARY is hit */
        /**************************************************************************/
        while (!(top.name.startsWith("SCOPE-BOUNDARY"))) {
            table[top.index] = top.next;
            top_index = top_index - 1;
            top = top.prevtop;
        }
        /**************************************/
        /* Pop the SCOPE-BOUNDARY sign itself */
        /**************************************/
        table[top.index] = top.next;
        top_index = top_index - 1;
        top = top.prevtop;

        /*********************************************/
        /* Print the symbol table after every change */
        /*********************************************/
        PrintMe();
    }

    /******************************/
    /* GET SINGLETON INSTANCE ... */
    public void PrintMe() {
        int i = 0;
        int j = 0;
        String dirname = "./output/";
        String filename = String.format("SYMBOL_TABLE_%d_IN_GRAPHVIZ_DOT_FORMAT.txt", n++);

        try {
            /*******************************************/
            /* [1] Open Graphviz text file for writing */
            /*******************************************/
            PrintWriter fileWriter = new PrintWriter(dirname + filename);

            /*********************************/
            /* [2] Write Graphviz dot prolog */
            /*********************************/
            fileWriter.print("digraph structs {\n");
            fileWriter.print("rankdir = LR\n");
            fileWriter.print("node [shape=record];\n");

            /*******************************/
            /* [3] Write Hash Table Itself */
            /*******************************/
            fileWriter.print("hashTable [label=\"");
            for (i = 0; i < hashArraySize - 1; i++) {
                fileWriter.format("<f%d>\n%d\n|", i, i);
            }
            fileWriter.format("<f%d>\n%d\n\"];\n", hashArraySize - 1, hashArraySize - 1);

            /****************************************************************************/
            /* [4] Loop over hash table array and print all linked lists per array cell */
            /****************************************************************************/
            for (i = 0; i < hashArraySize; i++) {
                if (table[i] != null) {
                    /*****************************************************/
                    /* [4a] Print hash table array[i] -> entry(i,0) edge */
                    /*****************************************************/
                    fileWriter.format("hashTable:f%d -> node_%d_0:f0;\n", i, i);
                }
                j = 0;
                for (SYMBOL_TABLE_ENTRY it = table[i]; it != null; it = it.next) {
                    /*******************************/
                    /* [4b] Print entry(i,it) node */
                    /*******************************/
                    fileWriter.format("node_%d_%d ", i, j);
                    fileWriter.format("[label=\"<f0>%s|<f1>%s|<f2>prevtop=%d|<f3>next\"];\n",
                            it.name,
                            it.type.name,
                            it.prevtop_index);

                    if (it.next != null) {
                        /***************************************************/
                        /* [4c] Print entry(i,it) -> entry(i,it.next) edge */
                        /***************************************************/
                        fileWriter.format(
                                "node_%d_%d -> node_%d_%d [style=invis,weight=10];\n",
                                i, j, i, j + 1);
                        fileWriter.format(
                                "node_%d_%d:f3 -> node_%d_%d:f0;\n",
                                i, j, i, j + 1);
                    }
                    j++;
                }
            }
            fileWriter.print("}\n");
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getScope() {
        SYMBOL_TABLE_ENTRY curr = top;

        while (curr != null) {
            if (curr.name.startsWith("SCOPE-BOUNDARY")) {
                return curr.name.split("-")[2];
            }
            curr = curr.prevtop;
        }
        return "global";
    }

    public String findExtendsClass(String className) {
        SYMBOL_TABLE_ENTRY curr = top;
        while (curr != null) {
            if (curr.name.startsWith("SCOPE-BOUNDARY-class")) {
                String[] splited = curr.name.split("-");
                if (splited.length > 5 && splited[3].equals(className))
                    return curr.name.split("-")[5];
                return null;
            }
            curr = curr.prevtop;
        }
        return null;
    }

    // if not, returns null. if yes, returns class' name
    public String inClassScope() {
        SYMBOL_TABLE_ENTRY curr = top;

        while (curr != null) {
            if (curr.name.startsWith("SCOPE-BOUNDARY-class"))
                return curr.name.split("-")[3];

            curr = curr.prevtop;
        }
        return null;
    }

    public boolean inFuncScope() {
        SYMBOL_TABLE_ENTRY curr = top;

        while (curr != null) {
            if (curr.name.startsWith("SCOPE-BOUNDARY-func"))
                return true;

            curr = curr.prevtop;
        }
        return false;
    }

    public TYPE findInFuncScope(String name) {
        SYMBOL_TABLE_ENTRY curr = top;
        // TYPE res = null;
        while (curr != null && !curr.name.startsWith("SCOPE-BOUNDARY-func")) {
            if (curr.name.equals(name)) {
                return curr.type;
            }
            curr = curr.prevtop;
        }
        return null;
    }

    // assumes stack is currently inside a class
    public TYPE findInClassScope(String name) {
        SYMBOL_TABLE_ENTRY curr = top;
        TYPE res = null;

        while (curr != null && !curr.name.startsWith("SCOPE-BOUNDARY-class")) {
            if (curr.name.equals(name)) {
                res = curr.type;
            }
            curr = curr.prevtop;
        }
        return res;
    }

    public TYPE findInCurrScope(String name) {
        SYMBOL_TABLE_ENTRY curr = top;
        TYPE res = null;

        while (curr != null && !curr.name.startsWith("SCOPE-BOUNDARY")) {
            if (curr.name.equals(name)) {
                res = curr.type;
            }
            curr = curr.prevtop;
        }
        return res;
    }

    /*when returning from function, check that return type is valid TODO*/
    public int findFunc(String returnType) {
        SYMBOL_TABLE_ENTRY a = top;
        AST_TYPE_VOID af = new AST_TYPE_VOID(-1);
        while (a != null) {
            if (a.name.startsWith("SCOPE-BOUNDARY-func")) {
                String[] splited = a.name.split("-");
                if (splited[splited.length - 1].equals(returnType))
                    return 1;

                TYPE name = af.findType(returnType);
                if (name.isClass()) {
                    // was declared already
                    TYPE_CLASS fa = ((TYPE_CLASS) name).father;
                    while (fa != null) {
                        if (fa.name.equals(splited[splited.length - 1]))
                            return 1;
                        fa = fa.father;
                    }
                    // was not declare
                    {
                        String f = findExtendsClass(name.name);
                        TYPE_CLASS ex = (TYPE_CLASS) SYMBOL_TABLE.getInstance().find(f);
                        fa = ex.father;
                        while (fa != null) {
                            if (fa.name.equals(splited[splited.length - 1]))
                                return 1;
                            fa = fa.father;
                        }

                    }

                }

            }
            if (a.name.startsWith("SCOPE-BOUNDARY-class"))
                return 0;

            a = a.prevtop;
        }
        return 0;
    }

    /*if in func scope, returns TYPE of return type*/
    public TYPE getReturnTypeOfFunc() {
        String retTypeName = null;
        SYMBOL_TABLE_ENTRY a = top;
        AST_TYPE_VOID af = new AST_TYPE_VOID(-1);
        while (a != null) {
            if (a.name.startsWith("SCOPE-BOUNDARY-func")) {
                String[] splited = a.name.split("-");
                retTypeName = splited[splited.length - 1];

                return af.findType(retTypeName);


            }
            if (a.name.startsWith("SCOPE-BOUNDARY-class"))
                return null;

            a = a.prevtop;

        }
        return null;
    }

    /*checks if name(params) is a valid usage of the function name*/
    public TYPE isRealFunc(String name, AST_EXPLIST calledFuncParamExpList) {
        SYMBOL_TABLE_ENTRY currSymbol = top;
        while (currSymbol != null && currSymbol.name != null) {
            if (currSymbol.type.isFunc()) {
                /***** check if the function name exist****/
                if (!(currSymbol.name.equals(name))) {
                    currSymbol = currSymbol.prevtop;
                    continue;
                }

                /**** check if the parameters match*/
                TYPE_LIST declaredFuncParams = ((TYPE_FUNCTION) currSymbol.type).params;
                for (AST_EXPLIST currCalledParam = calledFuncParamExpList; currCalledParam != null; currCalledParam = currCalledParam.tail) {
                    if (declaredFuncParams == null)
                        return null; /* too many parameters in input */
                    TYPE currDeclaredParam = declaredFuncParams.head;
                    if (currDeclaredParam == null)
                        return null; /* too many parameters in input */

                    TYPE calledFuncParam = currCalledParam.head.SemantMe();

                    /*if function called with NIL, it must be declared with class or array*/
                    if (calledFuncParam.name.equals("nil") && (currDeclaredParam.isClass() || currDeclaredParam.isArray())) {
                        declaredFuncParams = declaredFuncParams.tail;
                        continue;
                    }
                    /*type names must be the same*/
                    if (Objects.equals(calledFuncParam.name, currDeclaredParam.name)) {
                        declaredFuncParams = declaredFuncParams.tail;
                        continue;
                    }
                    boolean isGoodParam = false;
                    /*if type names are not the same, then called Param must be a class inheriting from Declared Param*/
                    if (calledFuncParam.isClass() && currDeclaredParam.isClass()) {
                        TYPE_CLASS father = ((TYPE_CLASS) calledFuncParam).father;
                        while (father != null) {
                            if (father.name.equals(currDeclaredParam.name)) {
                                isGoodParam = true;
                                break;
                            }
                            father = father.father;
                        }
                        if (isGoodParam) {
                            declaredFuncParams = declaredFuncParams.tail;
                            continue;
                        }
                    }
                    /*bad param*/
                    return null;
                }
                if (declaredFuncParams == null || declaredFuncParams.head == null)
                    return ((TYPE_FUNCTION) currSymbol.type).returnType;

                return null;
            }
            currSymbol = currSymbol.prevtop;
        }
        return null;
    }

    public TYPE compareFuncs(TYPE_FUNCTION realFunc, AST_EXPLIST params, int line) {
        TYPE_LIST args = realFunc.params;
        for (AST_EXPLIST it = params; it != null; it = it.tail) {
            if (args == null)
                return null;

            TYPE argType = args.head;
            if (argType == null)
                return null;

            TYPE paramType = it.head.SemantMe();
            if (!(params.type_equals(argType, paramType)))
                return null;

            args = args.tail;

        }
        if (args == null || args.head == null)
            return realFunc.returnType;

        return null;
    }

    public void cleanGarbage() {
        /**************************************************************************/
        /*
         * Pop elements from the symbol table stack until a SCOPE-BOUNDARY-class is hit
         */
        /**************************************************************************/
        while (!(top.name.startsWith("SCOPE-BOUNDARY-class"))) {
            table[top.index] = top.next;
            top_index = top_index - 1;
            top = top.prevtop;
        }
        /**************************************/
        /* Pop the SCOPE-BOUNDARY sign itself */
        /**************************************/
        table[top.index] = top.next;
        top_index = top_index - 1;
        top = top.prevtop;
    }

    public TYPE findClass(String name) {
        SYMBOL_TABLE_ENTRY a = top;
        while (a.name != null) {
            if (a.name.equals(name))
                return a.type;
            if (a.name.startsWith("SCOPE-BOUNDARY-class"))
                if (a.name.split("-")[3].equals(name))
                    return new TYPE_CLASS(null, name, null, null);
            a = a.prevtop;
        }
        return null;
    }
}
