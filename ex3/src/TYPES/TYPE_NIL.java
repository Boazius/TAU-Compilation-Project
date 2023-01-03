package TYPES;

public class TYPE_NIL extends TYPE {
    /**************************************/
    /* USUAL SINGLETON IMPLEMENTATION ... */
    /**************************************/
    private static TYPE_NIL instance = null;

    /*****************************/
    /* PREVENT INSTANTIATION ... */

    /*****************************/
    protected TYPE_NIL() {
        this.name = "NIL";
    }

    public static TYPE_NIL getInstance() {
        if (instance == null) {
            instance = new TYPE_NIL();
        }
        return instance;
    }

    public String getType() {
        return "TYPE_NIL";
    }
}
