package IR;

import MIPS.MIPSGenerator;
import TEMP.TEMP;

//not in liveness
public class IRcommand_array_set extends IRcommand {

    public TEMP array;
    public TEMP index;
    public TEMP val;

    // array_set t1, t2, t3 (array, index, val)
    public IRcommand_array_set(TEMP array, TEMP index, TEMP val) {
        this.array = array;
        this.index = index;
        this.val = val;

    }

    public void MIPSme() {
        System.out.println("IRcommand_array_set" + "- MIPSme");
        MIPSGenerator.getInstance().array_set(array, index, val);

    }

}