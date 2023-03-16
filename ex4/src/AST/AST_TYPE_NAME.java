package AST;

import TYPES.TYPE;

public class AST_TYPE_NAME extends AST_Node {
    public TYPE type;
    public String name;

    public AST_TYPE_NAME(TYPE type, String name) {
        SerialNumber = AST_Node_Serial_Number.getFresh();

        this.type = type;
        this.name = name;
        this.typeName = type.name;
    }

    public void PrintMe() {

        System.out.format("NAME(%s):TYPE(%s)\n", name, typeName);

        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("NAME:TYPE\n%s:%s", name, typeName));
    }


}
