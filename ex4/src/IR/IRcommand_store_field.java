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

public class IRcommand_store_field extends IRcommand {
    public TEMP val;
    String classs;
    String var;

    // this.var = value
    // where var is a field of class c [i.e. x = t3, where x is a field] .
    public IRcommand_store_field(String classs, String varName, TEMP value) {
        this.classs = classs;
        this.var = varName;
        this.val = value;
    }

    /***************/
    /* MIPS me !!! */

    /***************/
    public void MIPSme() {
        System.out.println("IRcommand_store_field" + "- MIPSme");

        MIPSGenerator.getInstance().store_field(offset, val);
    }
}