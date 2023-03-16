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

public class IRcommand_Jump_beq extends IRcommand_Conditional_Jump {
    //String label;
    //TEMP oprnd1;
    //TEMP oprnd2;

    public IRcommand_Jump_beq(String label, TEMP op1, TEMP op2) {
        this.label = label;
        this.oprnd1 = op1;
        this.oprnd2 = op2;
        changeName("IRcommand_Conditional_Jump");
    }

    /***************/
    /* MIPS me !!! */

    /***************/
    public void MIPSme() {
        System.out.println("IRcommand_Jump_beq" + "- MIPSme");

        MIPSGenerator.getInstance().beq(oprnd1, oprnd2, label);
    }
}
