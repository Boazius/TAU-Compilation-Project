package IR;

import MIPS.MIPSGenerator;
import TEMP.TEMP;

public class IRcommand_Field_Access extends IRcommand {

    public TEMP src;
    public TEMP dst;
    String field;

    // t2 = field_access t1 field (dst, src, fieldName)
    public IRcommand_Field_Access(TEMP dst, TEMP src, String fieldName) {
        this.dst = dst;
        this.src = src;
        this.field = fieldName;
    }

    public void MIPSme() {
        System.out.println("IRcommand_Field_Access" + "- MIPSme");
        MIPSGenerator.getInstance().field_Access(dst, offset, src);
    }

}
