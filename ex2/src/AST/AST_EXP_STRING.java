package AST;

public class AST_EXP_STRING extends AST_EXP {
    public String value;

    public AST_EXP_STRING(String str) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format(" exp -> String( %s )\n", str);

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.value = str;
    }

    public void PrintMe() {
        /*******************************/
        /* AST NODE TYPE = AST STRING EXP */
        /*******************************/
        System.out.format("AST_EXP_STRING( %s )\n", value);

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("STRING(%s)", value));
    }
}
