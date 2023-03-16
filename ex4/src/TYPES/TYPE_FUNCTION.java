package TYPES;

public class TYPE_FUNCTION extends TYPE {
    /***********************************/
    /* The return type of the function */
    /***********************************/
    public TYPE returnType;

    /*************************/
    /* types of input params */
    /*************************/
    public TYPE_LIST params;

    // start label of this func in mips code
    public String startLabel;

    /****************/
    /* CTROR(S) ... */

    /****************/
    public TYPE_FUNCTION(TYPE returnType, String name, TYPE_LIST params) {
        this.name = name;
        this.returnType = returnType;
        this.params = params;
    }

    public boolean isFunc() {
        return true;
    }
}
