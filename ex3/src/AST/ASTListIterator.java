package AST;

import java.util.Iterator;

public class ASTListIterator implements Iterator<AST_Node>{

    private AST_LIST localList;

    public ASTListIterator(AST_LIST list){
        this.localList = list;
    }

    @Override
    public boolean hasNext() {
        return localList != null;
    }

    @Override
    public AST_Node next() {
        AST_Node res = localList.getHead();
        localList = localList.getTail();
        return res;
    }
}
