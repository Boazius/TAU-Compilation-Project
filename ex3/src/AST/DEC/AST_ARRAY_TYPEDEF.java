package AST.DEC;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import AST.AST_TYPE;
import UtilFunctions.HelperFunctions;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_ARRAY;

public class AST_ARRAY_TYPEDEF extends AST_DEC {
    String name;
    AST_TYPE type;

    public AST_ARRAY_TYPEDEF(String name, AST_TYPE type) {

        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.format("arrayTypeDef -> ARRAY ID(%s) = TYPE(%s)[]\n", name,type.type);

        this.name = name;
        this.type = type;
    }

    public void PrintMe() {
        /*System.out.format("AST_ARRAY_TYPEDEF ID( %s ) = TYPE[]", name);*/
        if (type != null) type.PrintMe();
        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Array Def %s", name));
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);

    }

    /*check correct semantics*/
    @Override
    public TYPE_ARRAY SemantMe()
    {
        TYPE t = type.SemantMe(); /*get TYPE from AST_TYPE*/
        if (t == null) {
            HelperFunctions.printError(this.myLine);
            return null;
        }
        /*create new TYPE_ARRAY, push it into symbol table, and return it*/
        TYPE_ARRAY newType = new TYPE_ARRAY(t, this.name);
        SYMBOL_TABLE.getInstance().enter(this.name, newType);
        return newType;
    }
}
