package AST;

import TYPES.TYPE;

public class AST_TYPE_ID extends AST_TYPE {

    public AST_TYPE_ID(String id, int line) {
        this.typeName = id;
        this.line = line;

        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print("type -> ID \n");
    }

    public void PrintMe() {

        /*************************************/
        /* AST NODE TYPE- change XXX with this class name */
        /*************************************/
        System.out.format("AST %s NODE\n", "TYPE_ID");

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("%s", typeName));

    }

    public TYPE SemantMe() {
        System.out.println("TYPE ID - semant me");
        TYPE res = findType(typeName);
        if (res == null) {
            System.out.format(">> ERROR(%d) non existing type %s (type_id)\n", line, res);
            printError(this.line);
        }

        // if this happens its a bug in the compiler, not in the input
        if (!res.name.equals(typeName)) {
            System.out.format(">> ERROR [%d]- type name isn't declared correctly! %s %s", line, res.name, typeName);
            printError(this.line);
        }

        return res;
    }

}