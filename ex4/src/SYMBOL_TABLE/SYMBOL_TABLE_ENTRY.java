/***********/
/* PACKAGE */
/***********/
package SYMBOL_TABLE;

/*******************/
/* PROJECT IMPORTS */
/*******************/

import TYPES.TYPE;

/**********************/
/* SYMBOL TABLE ENTRY */

/**********************/
public class SYMBOL_TABLE_ENTRY {
    /*********/
    /* index */
    /********/
    public String name;

    /********/
    /* name */
    public boolean isInstance;
    /******************/
    public TYPE type;

    /******************/
    /* TYPE value ... */
    /*********************************************/
    public SYMBOL_TABLE_ENTRY prevtop;

    /*********************************************/
    /* prevtop and next symbol table entries ... */
    public SYMBOL_TABLE_ENTRY next;
    /****************************************************/
    public int prevtop_index;

    /****************************************************/
    /* The prevtop_index is just for debug purposes ... */
    /*********/
    int index;

    /******************/
    /* CONSTRUCTOR(S) */

    /******************/
    public SYMBOL_TABLE_ENTRY(
            String name,
            TYPE type,
            int index,
            SYMBOL_TABLE_ENTRY next,
            SYMBOL_TABLE_ENTRY prevtop,
            int prevtop_index) {
        this.index = index;
        this.name = name;
        this.type = type;
        this.next = next;
        this.prevtop = prevtop;
        this.prevtop_index = prevtop_index;

        this.isInstance = true;
    }
}
