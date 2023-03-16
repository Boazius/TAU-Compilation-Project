/***********/
/* PACKAGE */
/***********/
package CFG;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
//import basicBlock;
/*******************/
/* PROJECT IMPORTS */

/*******************/
public class colorGraph {
    HashSet<node> valid = new HashSet<node>();
    HashSet<node> overall = new HashSet<node>();

    public colorGraph(basicBlock h) {
        basicBlock curr = h;
        while (curr != null) {
            Iterator<String> it = curr.inSet.iterator();
            node p;
            while (it.hasNext()) {
                String n = it.next();
                p = find(n);
                if (p == null) {
                    p = new node(n);
                    this.overall.add(p);
                    this.valid.add(p);
                }
                for (String s : curr.inSet) {
                    if (!s.equals(p.name)) {
                        p.neig.add(s);

                    }
                }
                p.activeNeig = p.neig.size();

            }
            curr = curr.direct;


        }


    }

    public node find(String check) {
        for (node maybe : this.valid) {
            if (Objects.equals(maybe.name, check)) {
                return maybe;

            }
        }
        return null;


    }

    public Boolean isEmpty() {
        return this.valid.isEmpty();
    }

    public class node {
        String name;
        HashSet<String> neig;
        int activeNeig;
        String paint;

        public node(String name) {
            this.name = name;
            this.neig = new HashSet<String>();
            this.activeNeig = 0;
            this.paint = null;
        }

    }

}