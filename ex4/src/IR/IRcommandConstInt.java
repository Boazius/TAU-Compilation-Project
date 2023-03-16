/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

import MIPS.MIPSGenerator;
import TEMP.TEMP;

public class IRcommandConstInt extends IRcommand_Assign {
    //TEMP dst;
    int val;

    public IRcommandConstInt(TEMP t, int value) {
        this.dst = t;
        this.val = value;
        changeName("IRcommand_Assign");
    }

    /***************/
    /* MIPS me !!! */

    /***************/
    public void MIPSme() {
        System.out.println("IRcommandConstInt" + "- MIPSme");

        MIPSGenerator.getInstance().li(dst, val);
    }
}
