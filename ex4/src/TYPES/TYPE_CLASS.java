package TYPES;

import AST.AST_ARG_LIST;
import AST.AST_TYPE_NAME_LIST;

public class TYPE_CLASS extends TYPE {
    /*********************************************************************/
    /* If this class does not extend a father class this should be null */
    /*********************************************************************/
    public TYPE_CLASS father;

    /**************************************************/
    /* Gather up all data members in one place */
    /* Note that data members coming from the AST are */
    /* packed together with the class methods */
    /**************************************************/
    public AST_ARG_LIST data_members;
    public AST_TYPE_NAME_LIST functions;

    /****************/
    /* CTROR(S) ... */

    /****************/
    public TYPE_CLASS(TYPE_CLASS father, String name, AST_ARG_LIST data_members, AST_TYPE_NAME_LIST funcs) {
        this.name = name;
        this.father = father;
        this.data_members = data_members;
        this.functions = funcs;
        printClass();
    }

    public static void printFunc(TYPE_FUNCTION f) {
        System.out.print("\n" + f.returnType.name + " ");
        System.out.print(f.name + " ");
        for (TYPE_LIST it = f.params; it != null; it = it.tail) {
            System.out.print(it.head.name + " ");
        }
        System.out.println(" \n");
    }

    public void printClass() {
        System.out.println("---------------------------");
        System.out.println("Class name is " + name);
        if (father != null) {
            System.out.println("extends class " + father.name);
        } else {
            System.out.println("extends no other class");
        }

        System.out.println("> class feilds are: ");
        if (data_members != null)
            data_members.printArgList();
        System.out.println("> class functions are: ");
        for (AST_TYPE_NAME_LIST it = functions; it != null; it = it.tail) {
            System.out.print(it.head.name + " ");
        }
        System.out.println("---------------------------");
    }

    public boolean isClass() {
        return true;
    }

}
