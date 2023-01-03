package TYPES;

public class TYPE_CLASS_VAR_DEC extends TYPE {
    public TYPE t;
    public String name;

    public TYPE_CLASS_VAR_DEC(TYPE t, String name) {
        this.t = t;
        this.name = name;
    }

    @Override
    public String toString() {
        return t.name + " ->  " + name;
    }

    public String getType() {
        return "TYPE_CLASS_VAR_DEC";
    }
}
