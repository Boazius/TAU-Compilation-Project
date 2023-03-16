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

//not in liveness
public class IRcommand_Const_String extends IRcommand_Assign {
    //TEMP dst;
    String label;
    String val;

    // assuming ba
    public IRcommand_Const_String(TEMP dst, String label, String value) {
        this.dst = dst;
        this.label = label;
        this.val = value;
        changeName("IRcommand_Assign");
    }

    /***************/
    /* MIPS me !!! */

    /***************/
    public void MIPSme() {
        System.out.println("IRcommand_Const_String" + "- MIPSme");

        MIPSGenerator.getInstance().allocate_const_string(dst, label, val);
    }
}
