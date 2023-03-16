package IR;

import AST.AST_Node;
import MIPS.MIPSGenerator;
import TEMP.TEMP;

import java.util.ArrayList;

public class IRcommand_New_Class_Object extends IRcommand_Assign {
    public String className;
    // public TEMP dst;

    // t1 = new_class className
    public IRcommand_New_Class_Object(TEMP t1, String className) {
        this.dst = t1;
        this.className = className;
    }

    public void MIPSme() {
        System.out.println("IRcommand_New_Class_Object" + "- MIPSme");

        int size = AST_Node.getClassSize(className);
        MIPSGenerator.getInstance().new_class(dst, className, size);

        // initialize pre-defined fields, where t1 points to the object start
        ArrayList<String> fields = AST_Node.classfields.get(className);

        for (String field : fields) {
            String currName = className + "_" + field;
            int curr_offset = AST_Node.GetOffset(currName);
            MIPSGenerator.getInstance().set_field_default(dst, currName, curr_offset);
        }
    }
}