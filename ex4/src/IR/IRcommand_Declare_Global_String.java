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

// string id = value (global scope)
public class IRcommand_Declare_Global_String extends IRcommand_Assign_Non_Temp {
    //String id;
    //String var;

    public IRcommand_Declare_Global_String(String id, String value) {
        this.id = id;
        this.var = value;
        changeName("IRcommand_Assign_Non_Temp");
    }

    /***************/
    /* MIPS me !!! */

    /***************/
    public void MIPSme() {
        System.out.println("IRcommand_Declare_Global_String" + " - MIPSme");

        String label = IRcommand.getFreshLabel("const_string");
        MIPSGenerator.getInstance().declare_global_string(label, id, var);

    }
}
