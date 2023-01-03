package AST;

import AST.DEC.AST_DEC_VAR;

public class AST_ID_LIST extends AST_LIST {
    public AST_DEC_VAR head;
    public AST_ID_LIST tail;

    public AST_ID_LIST(AST_TYPE type, String name, AST_ID_LIST tail) {

        SerialNumber = AST_Node_Serial_Number.getFresh();


        if (tail != null) System.out.print("idlist -> type ID idlist\n");
        if (tail == null) System.out.print("idlist -> type ID     \n");


        this.head = new AST_DEC_VAR(type, name, null);
        this.tail = tail;

        left = head;
        right = tail;
    }


    public void PrintMe() {

        /*System.out.format("AST NODE ID LIST:");*/


        if (head != null) head.PrintMe();
        if (tail != null) tail.PrintMe();

        /**********************************/
        /* PRINT to AST GRAPHVIZ DOT file */
        /**********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "ID LIST");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, head.SerialNumber);
        if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
    }

    @Override
    public AST_DEC_VAR getHead() {
        return this.head;
    }

    @Override
    public AST_ID_LIST getTail() {
        return this.tail;
    }
}
