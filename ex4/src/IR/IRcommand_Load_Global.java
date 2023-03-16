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

public class IRcommand_Load_Global extends IRcommand_Assign {
    //TEMP dst;
    String label;

    public IRcommand_Load_Global(TEMP dst, String name) {
        this.dst = dst;
        this.label = name;
        changeName("IRcommand_Assign");
    }

    /***************/
    /* MIPS me !!! */

    /***************/
    public void MIPSme() {
        System.out.println("IRcommand_Load_Global" + "- MIPSme");

        MIPSGenerator.getInstance().load_label(dst, label);
    }
}
