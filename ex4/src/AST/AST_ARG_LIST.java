package AST;

public class AST_ARG_LIST extends AST_Node {
    public AST_ARG head;
    public AST_ARG_LIST tail;

    /* list of arguments in function argument list*/
    public AST_ARG_LIST(AST_ARG head, AST_ARG_LIST tail) {
        SerialNumber = AST_Node_Serial_Number.getFresh();

        if (tail != null) System.out.print("args -> arg, args\n");
        if (tail == null) System.out.print("args -> arg\n");

        this.head = head;
        this.tail = tail;
    }

    public void PrintMe() {
        /*print node*/
        System.out.print("AST ARG_LIST NODE\n");

        if (head != null) head.PrintMe();
        if (tail != null) tail.PrintMe();

        /*print to ast*/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "ARG_LIST");

        if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, head.SerialNumber);
        if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
    }

    /*prints entire list of arguments, for class printing.*/
    public void printArgList() {
        AST_ARG_LIST data_members = this;
        for (AST_ARG_LIST it = data_members; it != null; it = it.tail) {
            System.out.print(it.head.type.typeName + ", " + it.head.id + " ");
        }
    }
}
