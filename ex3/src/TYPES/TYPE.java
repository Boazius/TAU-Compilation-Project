package TYPES;

public abstract class TYPE {
    /******************************/
    /*  Every type has a name ... */
    /******************************/
    public String name;

    public abstract String getType();


    public boolean isArray() {
        return false;
    }

    public boolean isClass() {
        return false;
    }

    public boolean isFunction() {
        return false;
    }

    public static boolean eqByString(TYPE t1, String s) {
        return t1.getType().equals(s);
    }
}
