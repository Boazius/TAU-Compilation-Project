package AST;

import UtilFunctions.HelperFunctions;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_VOID;

public class AST_TYPE extends AST_Node {
    public String type; /*"int" or "void" or "string" or ID */
    public Boolean isID; /*is true iff not TYPE_INT, TYPE_VOID, TYPE_STRING*/

    public AST_TYPE(String type,Boolean isID) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if(isID)
        {
            System.out.format("type -> ID(%s)\n", type);
        }
        else
        {
            System.out.format("type -> %s\n", type);
        }


        this.type = type;
        this.isID = isID;
    }

    public void PrintMe() {
        if(isID)
        {
            System.out.format("AST_TYPE ID(%s)\n", type);
        }
        else
        {
            System.out.format("AST_TYPE %s \n", type);
        }


        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        if(isID)
        {
            AST_GRAPHVIZ.getInstance().logNode(
                    SerialNumber,
                    String.format("TYPE ID(%s)", type), "cds");
        }
        else /*primitive type*/
        {
            AST_GRAPHVIZ.getInstance().logNode(
                    SerialNumber,
                    String.format("TYPE %s", type), "cds");
        }

    }

    @Override
    /*can be called multiple times without side effects*/
    public TYPE SemantMe()
    {
        TYPE t;
        /****************************/
        /* [1] Check If Type exists */
        /****************************/
        if(type.equals("void"))
            return TYPE_VOID.getInstance();
        t = SYMBOL_TABLE.getInstance().find(type);
        if (t == null)
        {
            System.out.format("ERROR non existing type %s\n",type);
            HelperFunctions.printError(myLine);
        }
        return t;
    }


}
