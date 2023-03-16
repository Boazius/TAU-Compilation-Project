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

// A id = nil (global scope, A is of TYPE_CLASS)
public class IRcommand_Declare_Global_Object extends IRcommand_Assign_Non_Temp {
    //String id;

    public IRcommand_Declare_Global_Object(String id) {
        this.id = id;
        changeName("IRcommand_Assign_Non_Temp");
    }

    /***************/
    /* MIPS me !!! */

    /***************/
    public void MIPSme() {
        System.out.println("IRcommand_Declare_Global_Object" + " - MIPSme");

        MIPSGenerator.getInstance().declare_global_object(id);

    }
}
