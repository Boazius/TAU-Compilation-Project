package AST.VAR;

import AST.AST_GRAPHVIZ;
import AST.AST_LIST;
import AST.AST_Node_Serial_Number;
import AST.DEC.AST_DEC_VAR;
import TYPES.TYPE_LIST;

public class AST_VAR_LIST extends AST_LIST {
    public AST_DEC_VAR head;
    public AST_VAR_LIST tail;

    public AST_VAR_LIST(AST_DEC_VAR head, AST_VAR_LIST tail) {
        SerialNumber = AST_Node_Serial_Number.getFresh();

        if (tail != null) System.out.print("decs -> dec decs\n");
        else System.out.print("decs -> dec\n");

        this.head = head;
        this.tail = tail;
        left = head;
        right = tail;
    }

    public void PrintMe() {
        /*System.out.print("AST NODE DEC LIST\n");*/

        if (head != null) head.PrintMe();
        if (tail != null) tail.PrintMe();

        /**********************************/
        /* PRINT to AST GRAPHVIZ DOT file */
        /**********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "DEC LIST");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, head.SerialNumber);
        if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
    }

    public TYPE_LIST cSemantMe(String name) {
        TYPE_LIST t = new TYPE_LIST(head.cSemantMe(name), null);
        if (tail != null) t.tail = tail.cSemantMe(name);
        return t;
    }

    @Override
    public AST_DEC_VAR getHead() {
        return this.head;
    }

    @Override
    public AST_VAR_LIST getTail() {
        return this.tail;
    }
}
