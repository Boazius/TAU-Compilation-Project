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

// int id = value (global scope)
public class IRcommand_Declare_Global_Int extends IRcommand_Assign_Non_Temp {
    //String id;
    int value;

    public IRcommand_Declare_Global_Int(String id, int value) {
        this.id = id;
        this.value = value;
        changeName("IRcommand_Assign_Non_Temp");
    }

    /***************/
    /* MIPS me !!! */

    /***************/
    public void MIPSme() {
        System.out.println("IRcommand_Declare_Global_Int" + " - MIPSme");

        MIPSGenerator.getInstance().declare_global_int(id, value);

    }
}
