package TYPES;

public class TYPE_CLASS extends TYPE {
    /*********************************************************************/
    /* If this class does not extend a father class this should be null  */
    /*********************************************************************/
    public TYPE_CLASS father;

    /*******************************************************************/
    /* Keep the name for debug purposes ... it won'type harm anything ... */
    /*******************************************************************/
    /**************************************************/
    /* Gather up all data members in one place        */
    /* Note that data members coming from the AST are */
    /* packed together with the class methods         */
    /**************************************************/
    public TYPE_LIST data_members;
    public TYPE_LIST function_list;
    public TYPE_LIST members;
    private TYPE_CLASS_VAR_DEC_LIST lastDataMember;
    public TYPE_LIST localFuncs;

    /****************/
    /* CTROR(S) ... */

    /****************/
    public TYPE_CLASS(String name, TYPE_CLASS father, TYPE_LIST members) {
        this.father = father;
        this.members = members;
        this.name = name;
        data_members = new TYPE_LIST(null, null);
        function_list = new TYPE_LIST(null, null);
        localFuncs = new TYPE_LIST(null, null);
    }

    @Override
    public boolean isClass() {
        return true;
    }

    public String getType() {
        return "TYPE_CLASS";
    }
}
