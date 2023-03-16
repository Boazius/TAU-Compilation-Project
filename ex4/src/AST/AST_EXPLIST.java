package AST;

import TEMP.TEMP_LIST;
import TYPES.TYPE_LIST;

public class AST_EXPLIST extends AST_Node {
    public AST_EXP head;
    public AST_EXPLIST tail;

    public AST_EXPLIST(AST_EXP head, AST_EXPLIST tail, int line) {
        SerialNumber = AST_Node_Serial_Number.getFresh();

        if (tail != null) System.out.print("explist -> exp, exps\n");
        if (tail == null) System.out.print("explist -> exp      \n");

        this.head = head;
        this.tail = tail;
        this.line = line;
    }

    public void PrintMe() {

        System.out.print("AST EXPLIST NODE\n");


        if (head != null) head.PrintMe();
        if (tail != null) tail.PrintMe();


        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "EXPLIST");


        if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, head.SerialNumber);
        if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
    }

    public TYPE_LIST SemantMe(int ignore) {
        System.out.println("EXPLIST - semant me");

        if (tail == null) {
            return new TYPE_LIST(head.SemantMe(), null);
        } else {
            return new TYPE_LIST(head.SemantMe(), tail.SemantMe(0));
        }
    }

    public TEMP_LIST IRme(int ignore) {
        System.out.println("EXPLIST - IRme");
        if ((head == null) && (tail == null)) {
            return null;
        } else if (tail == null) {
            return new TEMP_LIST(head.IRme(), null);
        } else {
            if (head != null) {
                return new TEMP_LIST(head.IRme(), tail.IRme(0));
            }
        }
        return null;
    }
}
