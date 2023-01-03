package SYMBOL_TABLE;


import TYPES.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/****************/
/* SYMBOL TABLE */

/****************/
public class SYMBOL_TABLE {

    /**********************************************/
    /* The actual symbol table data structure ... */
    /**********************************************/
    private final List<Map<String, TYPE>> tableList;
    private int scope_index = 0;


    /*push <String,TYPE> to current scope*/
    public void enter(String name, TYPE t) {
        Map<String, TYPE> currScope = tableList.get(scope_index);
        currScope.put(name, t);
        PrintMe();
    }

    /***********************************************/
    /* Find the inner-most scope element with name */
    /***********************************************/
    public TYPE find(String name) {
        /*start from innermost*/
        for (int i = scope_index; i >= 0; i--) {
            Map<String, TYPE> currScope = tableList.get(i);
            if (currScope.containsKey(name)) return currScope.get(name);

        }

        return null;
    }

    /*find but only in current scope..*/
    public TYPE findInCurrScope(String name) {
        Map<String, TYPE> currScope = tableList.get(scope_index);
        return currScope.getOrDefault(name, null);
    }

    /*add a hashmap and enter it*/
    public void beginScope() {

        tableList.add(new HashMap<>());
        scope_index++;
        PrintMe();
    }


    /*delete the hashmap and exit it*/
    public void endScope() {
        tableList.remove(scope_index);
        scope_index--;

        PrintMe();
    }

    public static int n = 0;

    public void PrintMe() {
        /*TODO*/
/*        int i=0;
        int j=0;
        String dirname="./output/";
        String filename=String.format("SYMBOL_TABLE_%d_IN_GRAPHVIZ_DOT_FORMAT.txt",n++);

        try
        {
            *//*******************************************//*
         *//* [1] Open Graphviz text file for writing *//*
         *//*******************************************//*
            PrintWriter fileWriter = new PrintWriter(dirname+filename);

            *//*********************************//*
         *//* [2] Write Graphviz dot prolog *//*
         *//*********************************//*
            fileWriter.print("digraph structs {\n");
            fileWriter.print("rankdir = LR\n");
            fileWriter.print("node [shape=record];\n");

            *//*******************************//*
         *//* [3] Write Hash Table Itself *//*
         *//*******************************//*
            fileWriter.print("hashTable [label=\"");
            for (i=0;i<hashArraySize-1;i++) { fileWriter.format("<f%d>\n%d\n|",i,i); }
            fileWriter.format("<f%d>\n%d\n\"];\n",hashArraySize-1,hashArraySize-1);

            *//****************************************************************************//*
         *//* [4] Loop over hash table array and print all linked lists per array cell *//*
         *//****************************************************************************//*
            for (i=0;i<hashArraySize;i++)
            {
                if (table[i] != null)
                {
                    *//*****************************************************//*
         *//* [4a] Print hash table array[i] -> entry(i,0) edge *//*
         *//*****************************************************//*
                    fileWriter.format("hashTable:f%d -> node_%d_0:f0;\n",i,i);
                }
                j=0;
                for (SYMBOL_TABLE_ENTRY it=table[i];it!=null;it=it.next)
                {
                    *//*******************************//*
         *//* [4b] Print entry(i,it) node *//*
         *//*******************************//*
                    fileWriter.format("node_%d_%d ",i,j);
                    fileWriter.format("[label=\"<f0>%s|<f1>%s|<f2>prevtop=%d|<f3>next\"];\n",
                            it.name,
                            it.type.name,
                            it.prevtop_index);

                    if (it.next != null)
                    {
                        *//***************************************************//*
         *//* [4c] Print entry(i,it) -> entry(i,it.next) edge *//*
         *//***************************************************//*
                        fileWriter.format(
                                "node_%d_%d -> node_%d_%d [style=invis,weight=10];\n",
                                i,j,i,j+1);
                        fileWriter.format(
                                "node_%d_%d:f3 -> node_%d_%d:f0;\n",
                                i,j,i,j+1);
                    }
                    j++;
                }
            }
            fileWriter.print("}\n");
            fileWriter.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/
    }

    private static SYMBOL_TABLE instance = null;


    protected SYMBOL_TABLE() {
        /*global scope*/
        this.tableList = new ArrayList<>();
        tableList.add(new HashMap<>());


    }

    /*create the symbol table if doesnt exist, and return it.*/
    public static SYMBOL_TABLE getInstance() {
        if (instance == null) {
            /*******************************/
            /* [0] The instance itself ... */
            /*******************************/
            instance = new SYMBOL_TABLE();

            /*****************************************/
            /* [1] Enter primitive types int, string */
            /*****************************************/
            instance.enter("int", TYPE_INT.getInstance());
            instance.enter("string", TYPE_STRING.getInstance());

            /*************************************/
            /* [2] How should we handle void ??? */
            /*************************************/

            /***************************************/
            /* [3] Enter library function PrintInt and PrintString */
            /***************************************/
            instance.enter("PrintInt", new TYPE_FUNCTION(TYPE_VOID.getInstance(), "PrintInt", new TYPE_LIST(TYPE_INT.getInstance(), null)));
            instance.enter("PrintString", new TYPE_FUNCTION(TYPE_VOID.getInstance(), "PrintString", new TYPE_LIST(TYPE_STRING.getInstance(), null)));
        }
        return instance;
    }
}
