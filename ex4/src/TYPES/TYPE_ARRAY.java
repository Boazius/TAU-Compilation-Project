package TYPES;

public class TYPE_ARRAY extends TYPE {
    public TYPE entryType;

    /****************/
    /* CTROR(S) ... */

    /****************/
    public TYPE_ARRAY(TYPE entryType, String name) {
        this.entryType = entryType;
        this.name = name;
    }

    public boolean isArray() {
        return true;
    }

    public boolean isPrimitiveArray() {
        return (name.endsWith("[]") &&
                (entryType instanceof TYPE_INT || entryType instanceof TYPE_STRING));
    }
}
