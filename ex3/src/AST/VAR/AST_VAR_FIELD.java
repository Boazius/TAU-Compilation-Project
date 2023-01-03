package AST.VAR;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import UtilFunctions.HelperFunctions;
import TYPES.*;

public class AST_VAR_FIELD extends AST_VAR {
    public AST_VAR var;
    public String fieldName;

    public AST_VAR_FIELD(AST_VAR var, String fieldName) {
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.format("var -> var.ID( %s )\n", fieldName);

        this.var = var;
        this.fieldName = fieldName;
        right = var;
    }

    public void PrintMe() {
        /*System.out.print("AST_VAR_FIELD\n");*/
        /*System.out.format("FIELD NAME( %s )\n", fieldName);*/
        /**********************************************/
        /* RECURSIVELY PRINT VAR, then FIELD NAME ... */
        /**********************************************/
        if (var != null) var.PrintMe();


        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("FIELD .%s", fieldName));

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
    }

    public TYPE SemantMe() {
        TYPE t = null;
        TYPE_CLASS tc = null;

        if (var != null) t = var.SemantMe();

        /*********************************/
        /* [2] Make sure type is a class */
        /*********************************/
        if (t.isArray())
            t = ((TYPE_ARRAY) t).type;
        if (!(t.isClass())) {
            System.out.format("ERROR access %s field of a non-class variable\n", fieldName);
            HelperFunctions.printError(this.myLine);
        } else {
            tc = (TYPE_CLASS) t;
        }

        /*check field name in t*/
        for (TYPE_LIST it = tc.data_members; it != null; it = it.tail) {
            TYPE_CLASS_VAR_DEC dec = (TYPE_CLASS_VAR_DEC) it.head;
            if (dec.name.equals(fieldName)) {
                return ((TYPE_CLASS_VAR_DEC) it.head).t;
            }
        }


        /*********************************************/
        /* [4] fieldName does not exist in class var */
        /*********************************************/
        System.out.format("ERROR field %s does not exist in class\n", fieldName);
        HelperFunctions.printError(this.myLine);
        return null;
    }


    @Override
    public String getName() {
        return fieldName;
    }
}
