package TYPES;

public class TYPE_LIST {
    /****************/
    /* DATA MEMBERS */
    /****************/
    public TYPE head;
    public TYPE_LIST tail;

    public String name; // ???

    /******************/
    /* CONSTRUCTOR(S) */

    /******************/
    public TYPE_LIST(TYPE head, TYPE_LIST tail) {
        this.head = head;
        this.tail = tail;

        String headName = head.name;
        this.name = "TYPE_LIST(" + headName;

        for (TYPE_LIST it = tail; it != null; it = it.tail) {
            String currName = it.head.name;
            this.name += ", " + currName;
        }
        this.name += ")";
    }

    public TYPE_LIST reverseList() {

        TYPE_LIST res = null;

        for (TYPE_LIST it = this; it != null; it = it.tail) {
            res = new TYPE_LIST(it.head, res);
        }

        return res;
    }
}
