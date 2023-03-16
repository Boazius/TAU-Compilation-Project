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

public class IRcommand_Binop_EQ_Strings extends IRcommand_Binop {
    //public TEMP t1;
    //public TEMP t2;
    //public TEMP dst;

    public IRcommand_Binop_EQ_Strings(TEMP dst, TEMP t1, TEMP t2) {
        this.dst = dst;
        this.t1 = t1;
        this.t2 = t2;
        changeName("IRcommand_Binop");
    }

    /***************/
    /* MIPS me !!! */

    /***************/
    public void MIPSme() {
        System.out.println("IRcommand_Binop_EQ_Strings" + "- MIPSme");

        String l0 = IRcommand.getFreshLabel("eq_string_loop");
        String l1 = IRcommand.getFreshLabel("eq_string_no");
        String l2 = IRcommand.getFreshLabel("eq_string_end");
        String[] labels = {l0, l1, l2};

        MIPSGenerator.getInstance().eq_strings(dst, t1, t2, labels);
    }
}
