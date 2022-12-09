package AST;

public class AST_STMT_METHOD extends AST_STMT {

    public String id;
    public AST_VAR var;
    public AST_EXP_LIST args;

    public AST_STMT_METHOD(AST_VAR var, String id, AST_EXP_LIST args) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if ((var != null) && (args != null))
            System.out.printf("stmt -> var. %s (exps)\n", id);
        else if ((var != null) && (args == null))
            System.out.printf("stmt -> var. %s (exps)\n", id);
        else if ((var == null) && (args != null))
            System.out.printf("stmt -> %s (exps)\n", id);
        else
            System.out.printf("stmt -> %s ()\n", id);

        this.var = var;
        this.id = id;
        this.args = args;

    }

    public void PrintMe() {
        /*******************************/
        /* AST NODE TYPE = AST INT METHOD */
        /*******************************/
        System.out.format("AST NODE METHOD( %s )\n", id);
        if (var != null) var.PrintMe();
        if (args != null) args.PrintMe();

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("call for Method: NAME(%s)", id));
        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
        if (args != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, args.SerialNumber);
    }
}
