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
public class IRcommand_Return extends IRcommand {
    public TEMP RetVal;

    public IRcommand_Return(TEMP retVal) {
        this.RetVal = retVal;
    }

    /***************/
    /* MIPS me !!! */

    /***************/
    public void MIPSme() {
        System.out.println("IRcommand_Return" + "- MIPSme");
        MIPSGenerator.getInstance().ret(RetVal);
    }
}
