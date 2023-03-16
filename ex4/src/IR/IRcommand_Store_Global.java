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

public class IRcommand_Store_Global extends IRcommand {
    public TEMP dst;
    String label;

    // x = t0 (x is stored in label, dst)
    public IRcommand_Store_Global(TEMP dst, String label) {
        this.dst = dst;
        this.label = label;
    }

    /***************/
    /* MIPS me !!! */

    /***************/
    public void MIPSme() {
        System.out.println("IRcommand_Store_Global" + "- MIPSme");

        MIPSGenerator.getInstance().store_label(dst, label);
    }
}
