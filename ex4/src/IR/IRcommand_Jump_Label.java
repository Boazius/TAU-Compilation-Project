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

public class IRcommand_Jump_Label extends IRcommand_Conditional_Jump {
    //String label;

    public IRcommand_Jump_Label(String label) {
        this.label = label;
        changeName("IRcommand_Conditional_Jump");
    }

    /***************/
    /* MIPS me !!! */

    /***************/
    public void MIPSme() {
        System.out.println("IRcommand_Jump_Label" + "- MIPSme");

        MIPSGenerator.getInstance().jump(label);
    }
}
