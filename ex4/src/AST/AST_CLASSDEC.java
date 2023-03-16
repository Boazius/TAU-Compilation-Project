package AST;

import TYPES.TYPE_CLASS;

public abstract class AST_CLASSDEC extends AST_Node {

    public String id;
    public TYPE_CLASS father;
    public AST_CFIELD_LIST data_members;

}
