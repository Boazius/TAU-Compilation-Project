package AST.DEC;

import AST.AST_CFIELD_LIST;
import AST.AST_FUNC_LIST;
import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import AST.VAR.AST_VAR_LIST;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_CLASS;
import TYPES.TYPE_LIST;

public class AST_DEC_CLASS extends AST_DEC {

    public AST_CLASS_SIG sig;
    public AST_CFIELD_LIST cfieldList;
    public AST_FUNC_LIST funcList;
    public AST_VAR_LIST varList;


    public AST_DEC_CLASS(AST_CLASS_SIG sig, AST_CFIELD_LIST cfieldList) {

        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.format("decClass -> SIG body\n");

        this.sig = sig;
        this.cfieldList = cfieldList;
        right = cfieldList;
    }


    public void PrintMe() {

        /*debug print*/
/*        System.out.format("decClass ( %s )\n", this.sig.name);
        if (this.sig.ext != null) System.out.format("extends %s", this.sig.ext);*/

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        if (cfieldList != null) cfieldList.PrintMe();

        if (this.sig.ext != null)
            AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("ClassDec %s EXTENDS %s", this.sig.name, this.sig.ext));
        else AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("ClassDec %s", this.sig.name));
        if (cfieldList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cfieldList.SerialNumber);

    }

    /*check semantics*/
    public TYPE SemantMe() {
        /*************************/
        /* [1] Begin Class Scope */
        /*************************/

        /*get TYPE_CLASS from signature - it gets father class too*/
        TYPE_CLASS t = sig.SemantMe();

        /*semant every variable and function*/ /*TODO semanting scoping prioritizes outer scope instead of inner for some reason..*/
        SYMBOL_TABLE.getInstance().beginScope();

        TYPE_LIST varTypeList = null;
        TYPE_LIST funcTypeList = null;
        if (this.varList != null) /* passes name of class containing the variables*/
            varTypeList = varList.cSemantMe(sig.name);
        if (this.funcList != null) /* passes name of class containing the function */
            funcTypeList = funcList.cSemantMe(sig.name);

        t.data_members.addAll(varTypeList);
        t.function_list.addAll(funcTypeList);

        /*****************/
        /* [3] End Scope */
        /*****************/
        SYMBOL_TABLE.getInstance().endScope();

        /************************************************/
        /* [4] Enter the Class Type to the Symbol Table */
        /************************************************/
        SYMBOL_TABLE.getInstance().enter(sig.name, t);

        /*********************************************************/
        /* [5] Return value is irrelevant for class declarations */
        /*********************************************************/
        return null;
    }

    public String getName() {
        return this.sig.name;
    }

}
