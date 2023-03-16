package AST;

import IR.IR;
import IR.IRcommand_declareClass;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TEMP.TEMP;
import TYPES.TYPE;
import TYPES.TYPE_CLASS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AST_CLASSDEC_SIMPLE extends AST_CLASSDEC {


    public AST_CLASSDEC_SIMPLE(String id, AST_CFIELD_LIST data_members, int line) {
        this.data_members = data_members;
        this.id = id;
        this.line = line;
        this.father = null;


        SerialNumber = AST_Node_Serial_Number.getFresh();

        if (data_members != null) System.out.format("classDec -> CLASS ID:%s LBRACE cFieldList RBRACE\n", id);
    }


    public void PrintMe() {


        System.out.format("AST %s NODE\n", "CLASSDEC_SIMPLE");

        if (data_members != null) data_members.PrintMe();
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("CLASSDEC_SIMPLE(%s)", id));

        if (data_members != null) {
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, data_members.SerialNumber);
        }
    }

    public TYPE SemantMe() {
        System.out.format("CLASSDEC SIMPLE(%s) - semant me\n", id);

        TYPE isExist = SYMBOL_TABLE.getInstance().findInCurrScope(id);
        if (isExist != null) { // already has a varible with the same name
            System.out.format(">> ERROR [%d] already exist a variable with the (class) name " + id + " in the same scope", line);
            printError(line);
        }
        SYMBOL_TABLE.getInstance().beginScope("class-" + id);


        AST_ARG_LIST fields = null;
        AST_TYPE_NAME_LIST funcs = null;
        TYPE t;

        for (AST_CFIELD_LIST it = data_members; it != null; it = it.tail) {
            t = it.head.SemantMe();
            AST_TYPE currType;

            if (it.head instanceof AST_CFIELD_VARDEC) {
                switch (t.name) {
                    case "int": {
                        currType = new AST_TYPE_INT(line);
                        break;
                    }
                    case "string": {
                        currType = new AST_TYPE_STRING(line);
                        break;
                    }
                    case "void": {
                        System.out.println(">> ERROR [" + line + "] void variable is illegal");
                        printError(line);
                    }
                    default: {
                        currType = new AST_TYPE_ID(t.name, line);
                        break;
                    }
                }
                AST_ARG curr = new AST_ARG(currType, ((AST_CFIELD_VARDEC) it.head).varDec.id);
                fields = new AST_ARG_LIST(curr, fields);
            }

            if (it.head instanceof AST_CFIELD_FUNCDEC) {
                AST_TYPE_NAME curr = new AST_TYPE_NAME(t, ((AST_CFIELD_FUNCDEC) it.head).func.id);
                funcs = new AST_TYPE_NAME_LIST(curr, funcs);
            }
        }

        SYMBOL_TABLE.getInstance().cleanGarbage();
        TYPE_CLASS classType = new TYPE_CLASS(father, id, fields, funcs);
        SYMBOL_TABLE.getInstance().enterClassDec(id, classType);
        SYMBOL_TABLE.getInstance().beginScope("class-" + id);

        for (AST_CFIELD_LIST it = data_members; it != null; it = it.tail) {
            it.head.SemantMe();
        }

        SYMBOL_TABLE.getInstance().endScope();

        return null;
    }

    public TEMP IRme() {
        ArrayList<ArrayList<String>> funclist = new ArrayList<>();
        Map<String, Integer> funcOff = new HashMap<>();
        int fieldCnt = 0;
        int funcCnt = 0;
        for (AST_CFIELD_LIST it = data_members; it != null; it = it.tail) {
            AST_CFIELD field = it.head;
            if (field instanceof AST_CFIELD_VARDEC) { // field
                fieldCnt += 1;
                continue;
            }
            if (field instanceof AST_CFIELD_FUNCDEC) { // func
                // funcCnt += 1;
                AST_CFIELD_FUNCDEC a = (AST_CFIELD_FUNCDEC) field;
                AST_FUNCDEC b = a.func;
                offsets.put(id + "_" + b.id, id + "_" + b.id);
            }

            AST_CFIELD_FUNCDEC a = null;
            if (field instanceof AST_CFIELD_FUNCDEC) {
                a = (AST_CFIELD_FUNCDEC) field;
            }
            AST_FUNCDEC b = null;
            if (a != null) {
                b = a.func;
            }

            ArrayList<String> function = new ArrayList<>();
            if (b != null) {
                function.add(b.id);
            }
            function.add(id);
            funclist.add(function);
            if (b != null) {
                funcOff.put(b.id, funcCnt * 4);
            }
            funcCnt += 1;
        }
        classFuncsOff.put(id, funcOff);

        ArrayList<ArrayList<ArrayList<String>>> fields = new ArrayList<>();

        fieldCnt = 0;
        ArrayList<String> fieldslist = new ArrayList<>();
        for (AST_CFIELD_LIST it = data_members; it != null; it = it.tail) {
            AST_CFIELD field = it.head;

            if (field instanceof AST_CFIELD_VARDEC) { // field
                AST_CFIELD_VARDEC var = (AST_CFIELD_VARDEC) field;
                AST_VARDEC b = var.varDec;
                String off = String.valueOf(fieldCnt * 4 + 4);
                offsets.put(id + "_" + b.id, off);
                fieldslist.add(b.id);
                fieldCnt += 1;

                ArrayList<String> field1 = new ArrayList<>();
                field1.add(b.id);
                if (b.type instanceof AST_TYPE_STRING) field1.add("1");
                else field1.add("0");
                ArrayList<ArrayList<String>> fieldandclass = new ArrayList<>();
                fieldandclass.add(field1);
                ArrayList<String> classname = new ArrayList<>();
                classname.add(id);
                fieldandclass.add(classname);
                fields.add(fieldandclass);
            }
        }

        for (AST_CFIELD_LIST it = data_members; it != null; it = it.tail) {
            if (it.head instanceof AST_CFIELD_FUNCDEC) it.head.IRme();
        }
        classSize.put(id, fields.size() * 4 + 4);
        IR.getInstance().Add_IRcommand(new IRcommand_declareClass(id, funclist, fields));
        for (AST_CFIELD_LIST it = data_members; it != null; it = it.tail) {
            if (it.head instanceof AST_CFIELD_VARDEC) it.head.IRme();
        }
        classfields.put(id, fieldslist);
        return null;
    }
}