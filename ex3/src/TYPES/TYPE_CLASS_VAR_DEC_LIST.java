package TYPES;

public class TYPE_CLASS_VAR_DEC_LIST extends TYPE {
    public TYPE_CLASS_VAR_DEC head;
    public TYPE_CLASS_VAR_DEC_LIST tail;

    public TYPE_CLASS_VAR_DEC_LIST(TYPE_CLASS_VAR_DEC head, TYPE_CLASS_VAR_DEC_LIST tail) {
        this.head = head;
        this.tail = tail;
    }

    public void print() {
        System.out.println(head);
        if (tail != null)
            tail.print();
    }

    public String getType() {
        return "TYPE_CLASS_VAR_DEC_LIST";
    }

}
