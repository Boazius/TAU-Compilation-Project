package AST;

import IR.*;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TEMP.TEMP;
import TYPES.TYPE;

public class AST_STMT_ASSIGN extends AST_STMT {
    public AST_VAR var;
    public AST_EXP exp;
    public TYPE scope; // for irme
    public String inclass; // for irme
    public String ty;

    public AST_STMT_ASSIGN(AST_VAR var, AST_EXP exp, int line) {
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print("stmt -> var ASSIGN exp SEMICOLON\n");

        this.var = var;
        this.exp = exp;
        this.line = line;
    }


    public void PrintMe() {

        System.out.print("AST NODE ASSIGN STMT\n");


        if (var != null) var.PrintMe();
        if (exp != null) exp.PrintMe();


        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "ASSIGN\nleft := right\n");


        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
        if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
    }

    public TYPE SemantMe() {
        System.out.println("STMT_ASSIGN - semant me");

        TYPE t1;
        TYPE t2;

        if (var == null || exp == null) {
            printError(this.line);
        }

        t1 = var.SemantMe();
        t2 = exp.SemantMe();
        ty = t2.name;

        if (t1 == null || t2 == null) {
            System.out.format(">> ERROR [%d] non existing type\n", line);
            printError(line);
        }

        if (!(type_equals(t1, t2))) {
            /*cannot assign t2 to t1 TODO*/
            if (!isArrayAssignable(t1, t2) && !isExtendingClass(t1, t2)) {
                System.out.format(">> ERROR [%d] type mismatch for var := exp (stmt assgn)\n", line);
                printError(this.line);
            }
        }

        if (var instanceof AST_VAR_SIMPLE && SYMBOL_TABLE.getInstance().inFuncScope()) {
            scope = SYMBOL_TABLE.getInstance().findInFuncScope(((AST_VAR_SIMPLE) var).name);
        }
        inclass = SYMBOL_TABLE.getInstance().inClassScope();

        return null;
    }

    public TEMP IRme() {
        System.out.println("STMT_ASSIGN - IRme");

        if (var instanceof AST_VAR_SIMPLE) {
            TEMP value = exp.IRme();
            ((AST_VAR_SIMPLE) var).cfgVar = true;
            String name = ((AST_VAR_SIMPLE) var).name;
            // global
            if (((AST_VAR_SIMPLE) var).inGlobal == 1)
                IR.getInstance().Add_IRcommand(new IRcommand_Store_Global(value, name));
                // local
            else {
                if (scope != null) // inside func scope and local of func
                {
                    String varName = ((AST_VAR_SIMPLE) var).name;
                    IRcommand cRcommand = new IRcommand_Store_Local(varName, value);
                    IR.getInstance().Add_IRcommand(cRcommand);
                    cRcommand.offset = GetOffset(varName);
                } else if (inclass != null) { // can be field in func
                    String varName = inclass + "_" + ((AST_VAR_SIMPLE) var).name;
                    IRcommand c = new IRcommand_store_field(inclass, varName, value);
                    c.offset = GetOffset(varName);
                    IR.getInstance().Add_IRcommand(c);

                }

            }
        } else if (var instanceof AST_VAR_FIELD) {
            TEMP t1 = ((AST_VAR_FIELD) var).var.IRme();
            String f_name = ((AST_VAR_FIELD) var).fieldName;
            String c = ((AST_VAR_FIELD) var).classN;
            TEMP value = exp.IRme();
            IRcommand r = new IRcommand_field_set(t1, f_name, value);
            r.offset = GetOffset(c + "_" + f_name);
            IR.getInstance().Add_IRcommand(r);
            if (((AST_VAR_FIELD) var).var instanceof AST_VAR_SIMPLE)
                ((AST_VAR_SIMPLE) ((AST_VAR_FIELD) var).var).cfgVar = true;

        } else {

            AST_VAR_SUBSCRIPT subVar = (AST_VAR_SUBSCRIPT) var;
            TEMP array = subVar.var.IRme();
            TEMP index = subVar.subscript.IRme();
            TEMP value = exp.IRme();
            IR.getInstance().Add_IRcommand(new IRcommand_array_set(array, index, value));
        }

        return null;
    }

}