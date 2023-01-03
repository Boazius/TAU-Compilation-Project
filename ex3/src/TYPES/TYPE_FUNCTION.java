package TYPES;

public class TYPE_FUNCTION extends TYPE {

    public TYPE returnType;
    public String name;
    public TYPE_LIST arguments; /*useful to compare functions*/

    public TYPE_FUNCTION(TYPE returnType, String name, TYPE_LIST arguments) {
        this.arguments = arguments;
        this.name = name;
        this.returnType = returnType;
    }

    @Override
    public boolean isFunction() {
        return true;
    }

    public String getType() {
        return "TYPE_FUNCTION";
    }

}
