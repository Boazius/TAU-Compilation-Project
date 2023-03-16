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

public class IRcommand_Store_Local extends IRcommand {
    public TEMP src;
    public String var;

    // x = t4
    public IRcommand_Store_Local(String var, TEMP src) {
        this.src = src;
        this.var = var;
    }

    /***************/
    /* MIPS me !!! */

    /***************/
    public void MIPSme() {
        System.out.println("IRcommand_Store_Local" + "- MIPSme");

        MIPSGenerator.getInstance().store_local(src, offset);
    }
}