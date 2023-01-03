package TYPES;

public class TYPE_LIST extends TYPE {
    /****************/
    /* DATA MEMBERS */
    /****************/
    public TYPE head;
    public TYPE_LIST tail;

    /******************/
    /* CONSTRUCTOR(S) */

    /******************/
    public TYPE_LIST(TYPE head, TYPE_LIST tail) {
        this.head = head;
        this.tail = tail;
    }

    public String getType() {
        return "TYPE_LIST";
    }

    public void add(TYPE t) {
        TYPE_LIST curr = this;
        while (curr.tail != null) {
            curr = curr.tail;
        }
        if (this.head == null)
            this.head = t;
        else
            curr.tail = new TYPE_LIST(t, null);
    }

    /*push copies of all members of other into this list*/
    public void addAll(TYPE_LIST other) {
        if (other == null)
            return;
        TYPE_LIST curr = this;
        while (curr.tail != null) {
            curr = curr.tail;
        }
        TYPE_LIST copier = other;
        while (copier != null) {
            if (this.head == null)
                this.head = copier.head;
            else {
                curr.tail = new TYPE_LIST(copier.head, null);
                curr = curr.tail;
            }
            copier = copier.tail;
        }
    }

    /* finds string in list*/
    public TYPE findInList(String query) {
        TYPE_LIST currTail = this;
        if (this.head == null)
            return null;
        while (currTail != null) {
            TYPE_FUNCTION currHead = (TYPE_FUNCTION) currTail.head;
            if (currHead.name.equals(query)) {
                return currHead;
            }
            currTail = currTail.tail;
        }
        return null;
    }


    /* checks that the arguments passed to two different functions are the same*/
    public boolean compareFuncArgsByType(TYPE_LIST compareTo) {

        TYPE_LIST currTail1 = this;
        TYPE_LIST currTail2 = compareTo;
        while ((currTail1 != null) && (currTail2 != null)) {
            TYPE currHead1 = currTail1.head;
            TYPE currHead2 = currTail2.head;
            /*if the types of the arguments aren't equal*/
            if (currHead1 != currHead2) {
                return false;
            }
            currTail1 = currTail1.tail;
            currTail2 = currTail2.tail;
        }
        return (currTail1 == null) && (currTail2 == null);
    }


}
