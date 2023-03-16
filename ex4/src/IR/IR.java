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

public class IR {
    /**************************************/
    private static IR instance = null;
    public IRcommand head = null;
    public IRcommandList tail = null;
    private IRcommand initialization = null;
    /******************/
    /* Add IR command */
    private IRcommandList initializationTail = null;

    /*****************************/
    protected IR() {
    }

    /***************/
    /* MIPS me !!! */

    /******************************/
    public static IR getInstance() {
        if (instance == null) {
            /*******************************/
            /* [0] The instance itself ... */
            /*******************************/
            instance = new IR();
        }
        return instance;
    }

    /******************/
    public void Add_IRcommand(IRcommand cmd) {
        if ((head == null) && (tail == null)) {
            this.head = cmd;
        } else if ((head != null) && (tail == null)) {
            this.tail = new IRcommandList(cmd, null);
        } else {
            IRcommandList it = tail;
            while ((it != null) && (it.tail != null)) {
                it = it.tail;
            }
            it.tail = new IRcommandList(cmd, null);
        }
    }

    /**************************************/
    /* USUAL SINGLETON IMPLEMENTATION ... */

    public void Add_IRcommand_initialization(IRcommand cmd) {
        if ((initialization == null) && (initializationTail == null)) {
            this.initialization = cmd;
        } else if ((initialization != null) && (initializationTail == null)) {
            this.initializationTail = new IRcommandList(cmd, null);
        } else {
            IRcommandList it = initializationTail;
            while ((it != null) && (it.tail != null)) {
                it = it.tail;
            }
            it.tail = new IRcommandList(cmd, null);
        }
    }

    /*****************************/
    /* PREVENT INSTANTIATION ... */

    /***************/
    public void MIPSme() {
        if (head != null)
            head.MIPSme();
        if (tail != null)
            tail.MIPSme();
    }

    /******************************/
    /* GET SINGLETON INSTANCE ... */

    public void MIPSinitialization() {
        if (initialization != null)
            initialization.MIPSme();
        if (initializationTail != null)
            initializationTail.MIPSme();
    }
}
