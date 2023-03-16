package AST;

import IR.IR;
import IR.IRcommand_Declare_Global_Int;
import IR.IRcommand_Declare_Global_Object;
import IR.IRcommand_Declare_Global_String;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TEMP.TEMP;
import TYPES.TYPE;
import TYPES.TYPE_NIL;
import TYPES.TYPE_VOID;

public class AST_VARDEC_SIMPLE extends AST_VARDEC {

    public String inclass;
    public TYPE t;
    public boolean inFunc;

    public AST_VARDEC_SIMPLE(AST_TYPE type, String id, int line) {
        this.type = type;
        this.id = id;
        this.line = line;

        SerialNumber = AST_Node_Serial_Number.getFresh();


        if (type != null) System.out.print("varDec -> type ID SEMICOLON \n");
    }

    public void PrintMe() {

        System.out.print(String.format("AST %s NODE\n", "VARDEC_SIMPLE"));

        if (type != null) type.PrintMe();

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("VARDEC_SIMPLE(%s)", id));


        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
    }

    public TYPE SemantMe() {
        System.out.println("VARDEC SIMPLE - semant me");

        TYPE t1 = findType(type.typeName);
        t = t1;
        if (t1 == null || t1 instanceof TYPE_VOID || t1 instanceof TYPE_NIL) {
            System.out.format(">> ERROR [%d] type " + type.typeName + " does not exist", line);
            printError(line);
        }

        /*finds id in current scope*/
        TYPE t2 = SYMBOL_TABLE.getInstance().findInCurrScope(id);
        if (t2 != null) {
            System.out.format(">> [%d] ERROR variable " + id + " already exist", line);
            printError(line);
        }



        /*check if valid declaration*/
        isOverride();

        SYMBOL_TABLE.getInstance().enter(id, t1);
        scope = SYMBOL_TABLE.getInstance().getScope();
        inclass = SYMBOL_TABLE.getInstance().inClassScope();
        inFunc = SYMBOL_TABLE.getInstance().inFuncScope();
        return t1;
    }

    public TEMP IRme() {
        System.out.println("VARDEC SIMPLE IRme");

        // global
        if (scope.equals("global")) {
            if (type instanceof AST_TYPE_STRING) {
                IR.getInstance().Add_IRcommand(new IRcommand_Declare_Global_String(id, "aaa"));
            } else if (type instanceof AST_TYPE_INT) {
                IR.getInstance().Add_IRcommand(new IRcommand_Declare_Global_Int(id, 0));
            } else { // AST_TYPE_ID
                IR.getInstance().Add_IRcommand(new IRcommand_Declare_Global_Object(id));
            }
        }

        // local
        else {
            if (!inFunc && inclass != null) {
                String namec = inclass + "_" + id;
                IR.getInstance().Add_IRcommand(new IRcommand_Declare_Global_Object(namec));
            }

        }
        return null;
    }
}