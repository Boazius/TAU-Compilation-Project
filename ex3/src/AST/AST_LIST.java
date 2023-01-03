package AST;

import TYPES.TYPE;
import TYPES.TYPE_LIST;

import java.util.Iterator;

public abstract class AST_LIST extends AST_Node implements Iterable<AST_Node>{

    public abstract AST_Node getHead();
    public abstract AST_LIST getTail();

    @Override
    public TYPE_LIST SemantMe() {
        AST_Node head = getHead();
        AST_LIST tail = getTail();

        TYPE headT = null;
        TYPE_LIST tailT = null;
        if (head != null)
            headT = head.SemantMe();
        if (tail != null)
            tailT = tail.SemantMe();

        return new TYPE_LIST(headT,tailT);
    }

    @Override
    public Iterator<AST_Node> iterator() {
        return new ASTListIterator(this);
    }

}
