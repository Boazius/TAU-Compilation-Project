package AST;

import IR.IR;
import IR.IRcommand;
import IR.IRcommand_Const_String;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;
import TYPES.TYPE;
import TYPES.TYPE_STRING;

public class AST_EXP_STRING extends AST_EXP {
    public String s;
    public String scope;
    public String label;


    public AST_EXP_STRING(Object s) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.s = ((String) s).split("\"")[1];

        System.out.print("exp -> STRING\n");
    }

    public void PrintMe() {


        System.out.format("AST %s NODE\n", "EXP_STRING");


        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("%s", s));
    }

    public TYPE SemantMe() {
        System.out.println("EXP STRING (recoginzed string)- semant me");
        scope = SYMBOL_TABLE.getInstance().getScope();
        return TYPE_STRING.getInstance();
    }

    public TEMP IRme() {
        System.out.println("EXP STRING (recoginzed string)- IRme");

        TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();

        this.label = IRcommand.getFreshLabel("const_string");

        IR.getInstance().Add_IRcommand(new IRcommand_Const_String(t, label, s));

        return t;
    }

}