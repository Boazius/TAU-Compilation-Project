package AST;

public class AST_VAR_SIMPLE extends AST_VAR {
    public String name;

    public AST_VAR_SIMPLE(String name) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("var -> ID( %s )\n", name);

        /*******************************/
        /* COPY INPUT DATA Members ... */
        /*******************************/
        this.name = name;
    }

    public void PrintMe() {
        /**********************************/
        /* AST NODE TYPE = AST SIMPLE VAR */
        /**********************************/
        System.out.format("AST_VAR_SIMPLE ID( %s )\n", name);

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("SIMPLE VAR(%s)", name));
    }
}
