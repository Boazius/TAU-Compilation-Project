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

import java.util.ArrayList;

//not in liveness
public class IRcommand_declareClass extends IRcommand {
    String class_name;
    ArrayList<ArrayList<String>> funcs;
    ArrayList<ArrayList<ArrayList<String>>> fs;

    //class A{
    // fields are Fs
    //funcs are funcs
    //}
    public IRcommand_declareClass(String name, ArrayList<ArrayList<String>> funcs,
                                  ArrayList<ArrayList<ArrayList<String>>> Fs) {
        this.class_name = name;
        this.funcs = funcs;
        this.fs = Fs;
    }

    /***************/
    /* MIPS me !!! */

    /***************/
    public void MIPSme() {
        System.out.println("IRcommand_declareClass" + "- MIPSme");

        MIPSGenerator.getInstance().declare_class(class_name, funcs, fs);
    }
}