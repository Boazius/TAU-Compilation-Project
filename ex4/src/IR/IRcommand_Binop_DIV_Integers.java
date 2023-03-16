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

public class IRcommand_Binop_DIV_Integers extends IRcommand_Binop {
    //public TEMP t1;
    //public TEMP t2;
    //public TEMP dst;

    public IRcommand_Binop_DIV_Integers(TEMP dst, TEMP t1, TEMP t2) {
        this.dst = dst;
        this.t1 = t1;
        this.t2 = t2;
        changeName("IRcommand_Binop");
    }

    /***************/
    /* MIPS me !!! */

    /***************/
    public void MIPSme() {
        System.out.println("IRcommand_Binop_DIV_Integers" + "- MIPSme");

        MIPSGenerator.getInstance().div(dst, t1, t2);
    }
}
