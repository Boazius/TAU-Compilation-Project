package AST.DEC;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import AST.STMT.AST_STMT_LIST;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_FUNCTION;

public class AST_DEC_FUNC extends AST_DEC {


    public AST_STMT_LIST stmtList;
    public AST_FUNC_SIG sig; /*contains return type, name of the function, and list of ids in signature */
    public static TYPE func_type = null; /*this is static because this is the current function we are in, in semant.*/


    public AST_DEC_FUNC(AST_FUNC_SIG sig, AST_STMT_LIST list) {
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.format("funcDec -> sig stmtList\n");

        this.stmtList = list;
        this.sig = sig;
        left = sig;
        right = list;
    }

    public void PrintMe() {
        /*        System.out.format("decFunc\n");*/

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        if (sig != null) sig.PrintMe();
        if (stmtList != null) stmtList.PrintMe();

        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Function Dec"));
        if (sig != null)
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, sig.SerialNumber);
        if (stmtList != null)
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, stmtList.SerialNumber);

    }

    /*semantic check for global function*/
    public TYPE SemantMe() {
        /*Start Scope*/
        SYMBOL_TABLE.getInstance().beginScope();
        func_type = sig.type.SemantMe(); /*save current type of return of function*/
        TYPE_FUNCTION newFuncDec = (TYPE_FUNCTION) sig.SemantMe(); /*semantic check signature*/

        /*semantic check every statement in function*/
        stmtList.SemantMe();

        SYMBOL_TABLE.getInstance().endScope();
        /*End Scope*/
        SYMBOL_TABLE.getInstance().enter(sig.name, newFuncDec);

        /*return TYPE_FUNCTION*/
        return newFuncDec;
    }

    /*semantic check for function in class*/
    public TYPE cSemantMe(String name) {
        /*Start Scope*/
        SYMBOL_TABLE.getInstance().beginScope();

        TYPE_FUNCTION newFuncDec;
        func_type = sig.type.SemantMe();
        newFuncDec = (TYPE_FUNCTION) sig.cSemantMe(name);

        /*semantic check every statement in function*/
        stmtList.SemantMe();

        SYMBOL_TABLE.getInstance().endScope();
        /*End Scope*/

        /*return TYPE_FUNCTION*/
        return newFuncDec;

    }


}
