package AST.DEC;

import AST.AST_GRAPHVIZ;
import AST.AST_LIST;
import AST.AST_Node_Serial_Number;

public class AST_DEC_LIST extends AST_LIST {

    public AST_DEC head;
    public AST_DEC_LIST tail;

    public AST_DEC_LIST(AST_DEC head, AST_DEC_LIST tail) {

        SerialNumber = AST_Node_Serial_Number.getFresh();

        if (tail != null) System.out.print("declist -> dec declist\n");
        else System.out.print("declist -> dec\n");

        this.head = head;
        this.tail = tail;

        left = head;
        right = tail;
    }

    public void PrintMe() {
        /*print the entire list of decs to out and to DOT.*/
        /*System.out.print("AST_DEC_LIST\n");*/



        /**********************************/
        /* PRINT to AST GRAPHVIZ DOT file */
        /**********************************/
        if (head != null) head.PrintMe();
        if (tail != null) tail.PrintMe();
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber,
                "DecList", "box");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, head.SerialNumber);
        if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
    }

    @Override
    public AST_DEC getHead() {
        return this.head;
    }

    @Override
    public AST_DEC_LIST getTail() {
        return this.tail;
    }

    /*semantMe, we use the one in AST_LIST*/

}
