/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* PROJECT IMPORTS */

/*******************/

public abstract class IRcommand {
    /*****************/
    protected static int label_counter = 0;

    /*****************/
    /* Label Factory */
    public String IRname = "";
    public int offset;

    public static String getFreshLabel(String msg) {
        return String.format("Label_%d_%s", label_counter++, msg);
    }

    /***************/
    /* MIPS me !!! */

    /***************/
    public void MIPSme() {
        System.out.println("DEFAULT MIPSme!!!");
    }

    public void changeName(String name) {
        this.IRname = name;
    }

}
