package AST;

import TEMP.TEMP_LIST;
import TYPES.TYPE_LIST;

/*list of class fields*/
public class AST_CFIELD_LIST extends AST_Node {
    public AST_CFIELD head;
    public AST_CFIELD_LIST tail;

    public AST_CFIELD_LIST(AST_CFIELD head, AST_CFIELD_LIST tail) {
        SerialNumber = AST_Node_Serial_Number.getFresh();

        if (tail != null) System.out.print("cfields -> cfield cfields\n");
        if (tail == null) System.out.print("cfields -> cfield\n");

        this.head = head;
        this.tail = tail;
    }

    public void PrintMe() {
        /* print node*/
        System.out.format("AST CFIELD_LIST NODE\n");

        if (head != null) head.PrintMe();
        if (tail != null) tail.PrintMe();

        /*print ast*/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "CFIELD_LIST");

        if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, head.SerialNumber);
        if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);

    }

    public TYPE_LIST SemantMe(int ignore) {
        System.out.println("CFIELD LIST - semant me");

        if (tail == null) {
            return new TYPE_LIST(head.SemantMe(), null);
        } else {
            return new TYPE_LIST(head.SemantMe(), tail.SemantMe(0));
        }
    }

    public TEMP_LIST IRme(int ignore) {
        System.out.println("CFIELD_LIST - IRme");

        if ((head == null) && (tail == null)) {
            return null;
        } else if ((head != null) && (tail == null)) {
            return new TEMP_LIST(head.IRme(), null);
        } else {
            if (head != null) {
                return new TEMP_LIST(head.IRme(), tail.IRme(0));
            }
        }
        return null;
    }
}
