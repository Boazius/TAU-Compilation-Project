package AST;

public class AST_EXP_METHOD extends AST_EXP {

    public String id;
    public AST_VAR var;
    public AST_EXP_LIST args;

    public AST_EXP_METHOD(AST_VAR var, String id, AST_EXP_LIST args) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if ((var != null) && (args != null))
            System.out.printf("exp -> var. %s (exps)\n", id);
        else if ((var != null) && (args == null))
            System.out.printf("exp -> var. %s ()\n", id);
        else if ((var == null) && (args != null))
            System.out.printf("exp -> %s (exps)\n", id);
        else
            System.out.printf("exp -> %s ()\n", id);

        this.var = var;
        this.id = id;
        this.args = args;

    }

    public void PrintMe() {
        System.out.format("AST_EXP_METHOD( %s )\n", id);

        if (var != null) var.PrintMe();
        if (args != null) args.PrintMe();
        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Call for method: NAME(%s)", id));
        if (var != null) {
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
        }
        if (args != null) {
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, args.SerialNumber);
        }
    }
}
