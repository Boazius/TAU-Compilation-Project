package TYPES;

public abstract class TYPE {
    /******************************/
    /*  Every type has a name ... */
    /******************************/
    public String name;

    /*************/
    /* isClass() */

    /*************/
    public boolean isClass() {
        return false;
    }

    /*************/
    /* isArray() */

    /*************/
    public boolean isArray() {
        return false;
    }

    public boolean isPrimitiveArray() {
        return false;
    }

    public boolean isFunc() {
        return false;
    }
}
