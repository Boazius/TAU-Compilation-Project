package TYPES;

public class TYPE_ARRAY extends TYPE {

    public TYPE type;

    public TYPE_ARRAY(TYPE type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public boolean isArray() {
        return true;
    }

    public String getType() {
        return "TYPE_ARRAY";
    }
}
