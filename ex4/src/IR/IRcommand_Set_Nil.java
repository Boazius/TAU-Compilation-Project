package IR;

import MIPS.MIPSGenerator;
import TEMP.TEMP;

public class IRcommand_Set_Nil extends IRcommand_Assign {
    //public TEMP dst;

    public IRcommand_Set_Nil(TEMP t) {
        this.dst = t;
    }

    public void MIPSme() {
        System.out.println("IRcommand_Set_Nil - MIPSme");

        MIPSGenerator.getInstance().li(dst, 0);
    }
}
