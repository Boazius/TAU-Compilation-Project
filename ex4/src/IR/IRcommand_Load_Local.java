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

public class IRcommand_Load_Local extends IRcommand {
    public TEMP dst;
    String var;

    // t4 = x
    public IRcommand_Load_Local(String var, TEMP dst) {
        this.dst = dst;
        this.var = var;
    }

    /***************/
    /* MIPS me !!! */

    /***************/
    public void MIPSme() {
        System.out.println("IRcommand_Load_Local" + "- MIPSme");

        MIPSGenerator.getInstance().load_local(dst, offset);
    }
}