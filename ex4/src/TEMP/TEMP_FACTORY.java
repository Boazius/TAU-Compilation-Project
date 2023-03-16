/***********/
/* PACKAGE */
/***********/
package TEMP;



public class TEMP_FACTORY {
    /**************************************/
    private static TEMP_FACTORY instance = null;
    private int counter = 0;

    /**************************************/
    /* USUAL SINGLETON IMPLEMENTATION ... */
    /*****************************/
    protected TEMP_FACTORY() {
    }

    /*****************************/
    /* PREVENT INSTANTIATION ... */

    /******************************/
    public static TEMP_FACTORY getInstance() {
        if (instance == null) {
            /*******************************/
            /* [0] The instance itself ... */
            /*******************************/
            instance = new TEMP_FACTORY();
        }
        return instance;
    }

    /******************************/
    /* GET SINGLETON INSTANCE ... */

    public TEMP getFreshTEMP() {
        return new TEMP(counter++);
    }
}
