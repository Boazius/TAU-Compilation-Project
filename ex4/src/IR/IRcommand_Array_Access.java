package IR;

import MIPS.MIPSGenerator;
import TEMP.TEMP;

public class IRcommand_Array_Access extends IRcommand {
    public TEMP dst;
    public TEMP t1;
    public TEMP t2;

    // t0 = array_access t1 t2 (dst, src, index)
    public IRcommand_Array_Access(TEMP dst, TEMP src, TEMP index) {
        this.dst = dst;
        this.t1 = src;
        this.t2 = index;
    }

    public void MIPSme() {
        System.out.println("IRcommand_Array_Access" + "- MIPSme");

        MIPSGenerator.getInstance().array_access(dst, t1, t2);
    }

}