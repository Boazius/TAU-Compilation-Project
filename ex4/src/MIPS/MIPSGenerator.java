/***********/
/* PACKAGE */
/***********/
package MIPS;

/*******************/
/* GENERAL IMPORTS */
/*******************/

import IR.IRcommand;
import TEMP.TEMP;
import TEMP.TEMP_LIST;

import java.io.PrintWriter;
import java.util.ArrayList;


public class MIPSGenerator {
    public static String mipsOutputFilename;
    /**************************************/
    private static MIPSGenerator instance = null;
    /***********************/
    private final int WORD_SIZE = 4;

    /***********************/
    /* The file writer ... */
    /***********************/
    private PrintWriter fileWriter;

    /*****************************/
    protected MIPSGenerator() {
    }

    /******************************/
    public static MIPSGenerator getInstance() {
        if (instance == null) {
            /*******************************/
            /* [0] The instance itself ... */
            /*******************************/
            instance = new MIPSGenerator();

            try {
                /*********************************************************************************/
                /*
                 * [1] Open the MIPS text file and write data section with error message strings
                 */
                /*********************************************************************************/


                /***************************************/
                /* [2] Open MIPS text file for writing */
                /***************************************/
                instance.fileWriter = new PrintWriter(mipsOutputFilename);
            } catch (Exception e) {
                e.printStackTrace();
            }

            /*****************************************************/
            /* [3] Print data section with error message strings */
            /*****************************************************/
            instance.fileWriter.print(".data\n");
            instance.fileWriter.print("string_access_violation: .asciiz \"Access Violation\"\n");
            instance.fileWriter.print("string_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"\n");
            instance.fileWriter.print("string_illegal_div_by_0: .asciiz \"Illegal Division By Zero\"\n");
            instance.fileWriter.print("string_space: .asciiz \" \"\n");
            instance.fileWriter.print("string_tab: .asciiz \"\t\"\n");

            ///////////////// div by 0 //////////////////////////////////
            instance.fileWriter.format("\n.text\n");
            instance.label("division_by_zero");
            instance.fileWriter.format("\tla $a0, string_illegal_div_by_0\n");
            instance.fileWriter.format("\tli $v0, 4\n");
            instance.fileWriter.format("\tsyscall\n");
            instance.fileWriter.format("\tli $v0, 10\n");
            instance.fileWriter.format("\tsyscall\n");
            ///////////////////// invalid pointer //////////////////////
            instance.label("invalid_pointer_dereference");
            instance.fileWriter.format("\tla $a0, string_invalid_ptr_dref\n");
            instance.fileWriter.format("\tli $v0,4\n");
            instance.fileWriter.format("\tsyscall\n");
            instance.fileWriter.format("\tli $v0,10\n");
            instance.fileWriter.format("\tsyscall\n");
            instance.label("out_of_bound");
            instance.fileWriter.format("\tla $a0, string_access_violation\n");
            instance.fileWriter.format("\tli $v0, 4\n");
            instance.fileWriter.format("\tsyscall\n");
            instance.fileWriter.print("\tli $v0, 10\n");
            instance.fileWriter.print("\tsyscall\n");

            // library funcs
            instance.fileWriter.format(".text\n");
            MIPSGenerator.getInstance().label("PrintInt");
            MIPSGenerator.getInstance().prologue(0);
            instance.fileWriter.format("\tlw $a0, 8($fp)\n");
            instance.fileWriter.format("\tli $v0, 1\n");
            instance.fileWriter.format("\tsyscall\n");
            instance.fileWriter.format("\tla $a0, string_space\n");
            instance.fileWriter.format("\tli $v0, 4\n");
            instance.fileWriter.format("\tsyscall\n");
            MIPSGenerator.getInstance().epilogue();

            MIPSGenerator.getInstance().label("PrintString");
            MIPSGenerator.getInstance().prologue(0);
            instance.fileWriter.format("\tlw $a0, 8($fp)\n");
            instance.fileWriter.format("\tli $v0, 4\n");
            instance.fileWriter.format("\tsyscall\n");
            MIPSGenerator.getInstance().epilogue();

        }
        return instance;
    }

    /***********************/
    public void finalizeFile() {
        fileWriter.print("\t\n");
        fileWriter.print("main:\n");
        fileWriter.print("\tjal user_main\n");
        fileWriter.print("\tli $v0, 10\n");
        fileWriter.print("\tsyscall\n");
        fileWriter.close();
    }

    public void declare_class(String class_name, ArrayList<ArrayList<String>> funcs,
                              ArrayList<ArrayList<ArrayList<String>>> fs) {
        fileWriter.format(".data\n");
        fileWriter.format("vt_%s:\n", class_name);
        if (funcs.size() == 0)
            fileWriter.format("\t.word 0\n");
        else
            for (int i = 0; i < funcs.size(); i++) {
                String n = (funcs.get(i)).get(1) + "_";
                n += (funcs.get(i)).get(0);
                fileWriter.format("\t.word %s\n", n);
            }
        fileWriter.format(".text\n");
    }

    public void field_Access(TEMP t2, int offset, TEMP t1) {
        int t1id = t1.getSerialNumber();
        int t2id = t2.getSerialNumber();

        fileWriter.format("\tbeq $t%d, 0, invalid_pointer_dereference\n", t1id);
        fileWriter.format("\tlw $t%d, %d($t%d)\n", t2id, offset, t1id);

    }

    public void field_set(TEMP t1, int off, TEMP val) {
        int t1id = t1.getSerialNumber();
        int valid = val.getSerialNumber();

        fileWriter.format("\tbeq $t%d, 0, invalid_pointer_dereference\n", t1id);
        fileWriter.format("\tla $s0, %d($t%d)\n", off, t1id);
        fileWriter.format("\tsw $t%d, 0($s0)\n", valid);

    }

    public void virtual_call(TEMP dst, TEMP classTemp, int offset, TEMP_LIST args) {
        int cnt = 1;
        fileWriter.format("\tsubu $sp, $sp, 4\n");

        while (args != null) {
            cnt++;
            int num = args.head.getSerialNumber();
            fileWriter.format("\tsw $t%d, 0($sp)\n", num);
            fileWriter.format("\tsubu $sp, $sp, 4\n");
            args = args.tail;
        }
        int classix = classTemp.getSerialNumber();
        fileWriter.format("\tsw $t%d, 0($sp) \n", classix);

        fileWriter.format("\tlw $s0, 0($t%d)\n", classix);
        fileWriter.format("\tlw $s1, %d($s0)\n", offset);
        fileWriter.format("\tjalr $s1\n");

        fileWriter.format("\taddu $sp, $sp,%d\n", 4 * cnt);

        int dstix = dst.getSerialNumber();
        fileWriter.format("\tmove $t%d, $v0\n", dstix);

    }

    public void load_local(TEMP dst, int offset) {
        int destin = dst.getSerialNumber();
        fileWriter.format("\tlw $t%d,%d($fp)\n", destin, offset);
    }

    public void load_label(TEMP dst, String label) {
        int destin = dst.getSerialNumber();
        fileWriter.format("\tlw $t%d, %s\n", destin, label);
    }

    public void load_field_in_func(TEMP dst, int offset) {
        int id = dst.getSerialNumber();
        fileWriter.format("\t lw $s0, 8($fp)\n");
        fileWriter.format("\t lw $t%d, %d($s0)\n", id, offset);
    }

    public void store_local(TEMP dst, int offset) {
        int destin = dst.getSerialNumber();
        // int srcin = src.getSerialNumber();

        fileWriter.format("\tsw $t%d, %d($fp)\n", destin, offset);
    }

    public void store_label(TEMP dst, String label) {
        int destin = dst.getSerialNumber();
        fileWriter.format("\tsw $t%d, %s\n", destin, label);
    }

    public void store_field(int offset, TEMP val) {
        int id = val.getSerialNumber();
        fileWriter.format("\tlw $s0, 8($fp)\n");
        fileWriter.format("\tsw $t%d, %d($s0)\n", id, offset);
    }

    public void prologue(int vars) {
        fileWriter.format("\tsubu $sp, $sp, 4\n");
        fileWriter.format("\tsw $ra, 0($sp)\n");
        fileWriter.format("\tsubu $sp, $sp, 4\n");
        fileWriter.format("\tsw $fp, 0($sp)\n");
        fileWriter.format("\tmove $fp, $sp\n");

        fileWriter.format("\tsubu $sp, $sp, 4\n");
        fileWriter.format("\tsw $t0, 0($sp)\n");
        fileWriter.format("\tsubu $sp, $sp, 4\n");
        fileWriter.format("\tsw $t1, 0($sp)\n");
        fileWriter.format("\tsubu $sp, $sp, 4\n");
        fileWriter.format("\tsw $t2, 0($sp)\n");
        fileWriter.format("\tsubu $sp, $sp, 4\n");
        fileWriter.format("\tsw $t3, 0($sp)\n");
        fileWriter.format("\tsubu $sp, $sp, 4\n");
        fileWriter.format("\tsw $t4, 0($sp)\n");
        fileWriter.format("\tsubu $sp, $sp, 4\n");
        fileWriter.format("\tsw $t5, 0($sp)\n");
        fileWriter.format("\tsubu $sp, $sp, 4\n");
        fileWriter.format("\tsw $t6, 0($sp)\n");
        fileWriter.format("\tsubu $sp, $sp, 4\n");
        fileWriter.format("\tsw $t7, 0($sp)\n");
        fileWriter.format("\tsubu $sp, $sp, 4\n");
        fileWriter.format("\tsw $t8, 0($sp)\n");
        fileWriter.format("\tsubu $sp, $sp, 4\n");
        fileWriter.format("\tsw $t9, 0($sp)\n");

        fileWriter.format("\tsub $sp, $sp, %d\n", vars * 4);
    }

    public void epilogue() {
        fileWriter.format("\tmove $sp, $fp\n");
        fileWriter.format("\tlw $t0, -4($sp)\n");
        fileWriter.format("\tlw $t1, -8($sp)\n");
        fileWriter.format("\tlw $t2, -12($sp)\n");
        fileWriter.format("\tlw $t3, -16($sp)\n");
        fileWriter.format("\tlw $t4, -20($sp)\n");
        fileWriter.format("\tlw $t5, -24($sp)\n");
        fileWriter.format("\tlw $t6, -28($sp)\n");
        fileWriter.format("\tlw $t7, -32($sp)\n");
        fileWriter.format("\tlw $t8, -36($sp)\n");
        fileWriter.format("\tlw $t9, -40($sp)\n");
        fileWriter.format("\tlw $fp, 0($sp)\n");
        fileWriter.format("\tlw $ra, 4($sp)\n");
        fileWriter.format("\taddu $sp, $sp, 8\n");
        fileWriter.format("\tjr $ra\n");
        fileWriter.format("\n");
    }

    public void ret(TEMP val) {
        if (val != null) {
            int idx = val.getSerialNumber();
            fileWriter.format("\tmove $v0, $t%d\n", idx);
        }
        epilogue();
    }

    public void li(TEMP dst, int val) {
        int idxdst = dst.getSerialNumber();
        fileWriter.format("\tli $t%d, %d\n", idxdst, val);
    }

    public void allocate_const_string(TEMP dst, String label, String val) {
        int idxdst = dst.getSerialNumber();
        fileWriter.format(".data\n");
        fileWriter.format("%s: .asciiz \"%s\"\n", label, val);
        fileWriter.format(".text\n");
        fileWriter.format("\tla $t%d, %s\n", idxdst, label);
    }

    public void checkOverflow(TEMP dst) {
        String label = IRcommand.getFreshLabel("not_over");
        int ind = dst.getSerialNumber();
        int max = (int) Math.pow(2, 15) - 1;

        fileWriter.format("\tli $s0, %d\n", max);
        fileWriter.format("\tbge $s0, $t%d, %s\n", ind, label);
        fileWriter.format("\tli $t%d, %d\n", ind, max);
        fileWriter.format("%s:\n", label);
    }

    public void checkUnderflow(TEMP dst) {
        String label = IRcommand.getFreshLabel("not_under");
        int ind = dst.getSerialNumber();
        int min = -(int) Math.pow(2, 15);

        fileWriter.format("\tli $s0, %d\n", min);
        fileWriter.format("\tble $s0, $t%d, %s\n", ind, label);
        fileWriter.format("\tli $t%d, %d\n", ind, min);
        fileWriter.format("%s:\n", label);
    }

    public void add(TEMP dst, TEMP oprnd1, TEMP oprnd2) {
        int i1 = oprnd1.getSerialNumber();
        int i2 = oprnd2.getSerialNumber();
        int dstidx = dst.getSerialNumber();
        fileWriter.format("\tadd $t%d,$t%d,$t%d\n", dstidx, i1, i2);
        checkOverflow(dst);
        checkUnderflow(dst);
    }

    public void sub(TEMP dst, TEMP oprnd1, TEMP oprnd2) {
        int i1 = oprnd1.getSerialNumber();
        int i2 = oprnd2.getSerialNumber();
        int dstidx = dst.getSerialNumber();
        fileWriter.format("\tsub $t%d,$t%d,$t%d\n", dstidx, i1, i2);
        checkUnderflow(dst);
        checkOverflow(dst);
    }

    public void mul(TEMP dst, TEMP oprnd1, TEMP oprnd2) {
        int i1 = oprnd1.getSerialNumber();
        int i2 = oprnd2.getSerialNumber();
        int dstidx = dst.getSerialNumber();

        fileWriter.format("\tmul $t%d,$t%d,$t%d\n", dstidx, i1, i2);
        checkOverflow(dst);
        checkUnderflow(dst);
    }

    public void div(TEMP dst, TEMP oprnd1, TEMP oprnd2) {
        int i1 = oprnd1.getSerialNumber();
        int i2 = oprnd2.getSerialNumber();
        int dstidx = dst.getSerialNumber();
        beqz(oprnd2, "division_by_zero");
        fileWriter.format("\tdiv $t%d,$t%d,$t%d\n", dstidx, i1, i2);
        checkUnderflow(dst);
        checkOverflow(dst);
    }

    public void move(TEMP dst, TEMP value) {
        int val = value.getSerialNumber();
        int dstidx = dst.getSerialNumber();
        fileWriter.format("\tmove $t%d,$t%d\n", dstidx, val);
    }

    public void label(String inlabel) {
        fileWriter.format("\n");
        fileWriter.format("%s:\n", inlabel);
    }

    public void jump(String inlabel) {
        fileWriter.format("\tj %s\n", inlabel);
    }

    public void blt(TEMP oprnd1, TEMP oprnd2, String label) {
        int i1 = oprnd1.getSerialNumber();
        int i2 = oprnd2.getSerialNumber();

        fileWriter.format("\tblt $t%d,$t%d,%s\n", i1, i2, label);
    }

    public void bgt(TEMP oprnd1, TEMP oprnd2, String label) {
        int i1 = oprnd1.getSerialNumber();
        int i2 = oprnd2.getSerialNumber();

        fileWriter.format("\tbgt $t%d,$t%d,%s\n", i1, i2, label);
    }

    public void bge(TEMP oprnd1, TEMP oprnd2, String label) {
        int i1 = oprnd1.getSerialNumber();
        int i2 = oprnd2.getSerialNumber();

        fileWriter.format("\tbge $t%d,$t%d,%s\n", i1, i2, label);
    }

    public void ble(TEMP oprnd1, TEMP oprnd2, String label) {
        int i1 = oprnd1.getSerialNumber();
        int i2 = oprnd2.getSerialNumber();

        fileWriter.format("\tble $t%d,$t%d,%s\n", i1, i2, label);
    }

    public void bne(TEMP oprnd1, TEMP oprnd2, String label) {
        int i1 = oprnd1.getSerialNumber();
        int i2 = oprnd2.getSerialNumber();

        fileWriter.format("\tbne $t%d,$t%d,%s\n", i1, i2, label);
    }

    public void beq(TEMP oprnd1, TEMP oprnd2, String label) {
        int i1 = oprnd1.getSerialNumber();
        int i2 = oprnd2.getSerialNumber();

        fileWriter.format("\tbeq $t%d,$t%d,%s\n", i1, i2, label);
    }

    public void beqz(TEMP oprnd1, String label) {
        int i1 = oprnd1.getSerialNumber();

        fileWriter.format("\tbeq $t%d,$zero,%s\n", i1, label);
    }

    public void bnez(TEMP oprnd1, String label) {
        int i1 = oprnd1.getSerialNumber();

        fileWriter.format("\tbne $t%d,$zero,%s\n", i1, label);
    }

    /******************************/
    /* GET SINGLETON INSTANCE ... */
    public void concat_string(TEMP dst, TEMP str1, TEMP str2, String[] labels) {
        int idx1 = str1.getSerialNumber();
        int idx2 = str2.getSerialNumber();
        int idxdst = dst.getSerialNumber();

        // COUNTING PHASE
        MIPSGenerator.getInstance().label(labels[0]);
        // initialize $s0 and $s1 to the start of the strings, and a counter to 0
        fileWriter.format("\tmove $s0, $t%d\n", idx1);
        fileWriter.format("\tmove $s1, $t%d\n", idx2);
        fileWriter.format("\tli $s2, 0\n");

        // iterate on s1, if next char isn't null then increment counter
        MIPSGenerator.getInstance().label(labels[1]);
        fileWriter.format("\tlb $s3, 0($s0)\n");
        fileWriter.format("\tbeq $s3, 0, %s\n", labels[2]);
        fileWriter.format("\taddu $s0, $s0, 1\n");
        fileWriter.format("\taddu $s2, $s2, 1\n");
        MIPSGenerator.getInstance().jump(labels[1]);

        // iterate on s2, if next char isn't null then increment counter
        MIPSGenerator.getInstance().label(labels[2]);
        fileWriter.format("\tlb $s3, 0($s1)\n");
        fileWriter.format("\tbeq $s3, 0, %s\n", labels[3]);
        fileWriter.format("\taddu $s1, $s1, 1\n");
        fileWriter.format("\taddu $s2, $s2, 1\n");
        MIPSGenerator.getInstance().jump(labels[2]);

        // COPYING PHASE
        // allocate space for the new string (+1 space for null)
        MIPSGenerator.getInstance().label(labels[3]);
        fileWriter.format("\taddu $s2, $s2, 1\n");
        fileWriter.format("\tmove $a0, $s2\n", idx1);
        fileWriter.format("\tli $v0, 9\n");
        instance.fileWriter.format("\tsyscall\n");

        // init $s0 and $s1 to the start of the strings, $s2 to the start of the new
        fileWriter.format("\tmove $s0, $t%d\n", idx1);
        fileWriter.format("\tmove $s1, $t%d\n", idx2);
        fileWriter.format("\tmove $s2, $v0\n");

        // iterate on s1, copy every char to the new string
        MIPSGenerator.getInstance().label(labels[4]);
        fileWriter.format("\tlb $s3, 0($s0)\n");
        fileWriter.format("\tbeq $s3, 0, %s\n", labels[5]);
        fileWriter.format("\tsb $s3, 0($s2)\n");
        fileWriter.format("\taddu $s0, $s0, 1\n");
        fileWriter.format("\taddu $s2, $s2, 1\n");
        MIPSGenerator.getInstance().jump(labels[4]);

        // iterate on s2, copy every char to the new string
        MIPSGenerator.getInstance().label(labels[5]);
        fileWriter.format("\tlb $s3, 0($s1)\n");
        fileWriter.format("\tbeq $s3, 0, %s\n", labels[6]);
        fileWriter.format("\tsb $s3, 0($s2)\n");
        fileWriter.format("\taddu $s1, $s1, 1\n");
        fileWriter.format("\taddu $s2, $s2, 1\n");
        MIPSGenerator.getInstance().jump(labels[5]);

        // add null terminator to the
        MIPSGenerator.getInstance().label(labels[6]);
        fileWriter.format("\taddu $s2, $s2, 1\n");
        fileWriter.format("\tli $s3, 0\n");
        fileWriter.format("\tsb $s3, 0($s2)\n");

        // set dst to the start of the new string
        fileWriter.format("\tmove $t%d, $v0\n", idxdst);
    }

    public void array_access(TEMP t0, TEMP t1, TEMP t2) {
        int t0_ind = t0.getSerialNumber();
        int t1_ind = t1.getSerialNumber();
        int t2_ind = t2.getSerialNumber();

        // valid array check (runtime check)
        fileWriter.format("\tbeq $t%d, 0, invalid_pointer_dereference\n", t1_ind);

        // valid index check (runtime check)
        fileWriter.format("\tbltz $t%d, out_of_bound\n", t2_ind);
        fileWriter.format("\tlw $s0, 0($t%d)\n", t1_ind);
        fileWriter.format("\tbge $t%d, $s0, out_of_bound\n", t2_ind);

        // actual array access
        fileWriter.format("\tmove $s0, $t%d\n", t2_ind);
        fileWriter.format("\tadd $s0, $s0, 1\n");
        fileWriter.format("\tmul $s0, $s0, 4\n");
        fileWriter.format("\taddu $s0, $t%d, $s0\n", t1_ind);
        fileWriter.format("\tlw $t%d, 0($s0)\n", t0_ind);

    }

    public void new_array(TEMP t0, TEMP t1) {
        int t0_ind = t0.getSerialNumber();
        int t1_ind = t1.getSerialNumber();

        fileWriter.format("\tli $v0, 9\n");
        fileWriter.format("\tmove $a0, $t%d\n", t1_ind);
        fileWriter.format("\tadd $a0, $a0, 1\n");
        fileWriter.format("\tmul $a0, $a0, 4\n");
        fileWriter.print("\tsyscall\n");
        fileWriter.format("\tmove $t%d, $v0\n", t0_ind);
        fileWriter.format("\tsw $t%d, 0($t%d)\n", t1_ind, t0_ind);
    }

    public void new_class(TEMP t0, String className, int size) {
        int t0_ind = t0.getSerialNumber();

        // allocate the object itself
        fileWriter.format("\tli $v0, 9\n");
        fileWriter.format("\tli $a0, %d\n", size);
        fileWriter.print("\tsyscall\n");
        fileWriter.format("\tmove $t%d, $v0\n", t0_ind);

        // get class declaration pointer
        fileWriter.format("\tla $s0, vt_%s\n", className);
        fileWriter.format("\tsw $s0, 0($t%d)\n", t0_ind);

        // print address
        // fileWriter.format("\tmove $a0, $v0\n");
        // fileWriter.format("\tli $v0, 1\n");
        // fileWriter.format("\tsyscall\n");
    }

    public void set_field_default(TEMP t0, String label, int offset) {
        int t0_ind = t0.getSerialNumber();

        // get class declaration pointer
        fileWriter.format("\tlw $s0, %s\n", label);
        fileWriter.format("\tsw $s0, %d($t%d)\n", offset, t0_ind);
    }

    public void call_func(TEMP t, String startLabel, TEMP_LIST reversedTempList) {
        int cnt = 0;
        int tInd = t.getSerialNumber();

        // push temps backwards to stack
        for (TEMP_LIST it = reversedTempList; it != null; it = it.tail) {
            cnt++;
            TEMP curr = it.head;
            int currInd = curr.getSerialNumber();

            fileWriter.format("\tsubu $sp, $sp, 4\n");
            fileWriter.format("\tsw $t%d, 0($sp)\n", currInd);
        }

        // jump to the method and store the result
        fileWriter.format("\tjal %s\n", startLabel);
        fileWriter.format("\taddu $sp, $sp, %d\n", 4 * cnt);
        fileWriter.format("\tmove $t%d, $v0\n", tInd);
    }

    public void declare_global_string(String label, String id, String value) {
        fileWriter.format(".data\n");
        fileWriter.format("%s: .asciiz \"%s\"\n", label, value);
        fileWriter.format("%s: .word %s\n", id, label);
        fileWriter.format("\n.text\n");
    }

    public void declare_global_int(String id, int value) {
        fileWriter.format(".data\n");
        fileWriter.format("%s: .word %d\n", id, value);
        fileWriter.format("\n.text\n");
    }

    public void declare_global_object(String id) {
        fileWriter.format(".data\n");
        fileWriter.format("%s: .word 0\n", id);
        fileWriter.format("\n.text\n");
    }

    public void eq_strings(TEMP dst, TEMP t1, TEMP t2, String[] labels) {
        int dstInd = dst.getSerialNumber();
        int t1Ind = t1.getSerialNumber();
        int t2Ind = t2.getSerialNumber();

        // init regs to the start
        fileWriter.format("\tli $t%d, 1\n", dstInd);
        fileWriter.format("\tmove $s0, $t%d\n", t1Ind);
        fileWriter.format("\tmove $s1, $t%d\n", t2Ind);

        // check equality
        label(labels[0]);
        fileWriter.format("\tlb $s2, 0($s0)\n");
        fileWriter.format("\tlb $s3, 0($s1)\n");
        fileWriter.format("\tbne $s2, $s3, %s\n", labels[1]);
        fileWriter.format("\tbeq $s2, 0, %s\n", labels[2]);
        fileWriter.format("\taddu $s0, $s0, 1\n");
        fileWriter.format("\taddu $s1, $s1, 1\n");
        jump(labels[0]);
        label(labels[1]);
        fileWriter.format("\tli $t%d, 0\n", dstInd);
        label(labels[2]);
    }

    public void array_set(TEMP array, TEMP index, TEMP val) {
        int arrayInd = array.getSerialNumber();
        int indexInd = index.getSerialNumber();
        int valInd = val.getSerialNumber();

        // valid array check (runtime check)
        fileWriter.format("\tbeq $t%d, 0, invalid_pointer_dereference\n", arrayInd);

        // valid index check (runtime check)
        fileWriter.format("\tbltz $t%d, out_of_bound\n", indexInd);
        fileWriter.format("\tlw $s0, 0($t%d)\n", arrayInd);
        fileWriter.format("\tbge $t%d, $s0, out_of_bound\n", indexInd);

        // actual array set
        fileWriter.format("\tmove $s0, $t%d\n", indexInd);
        fileWriter.format("\tadd $s0, $s0, 1\n");
        fileWriter.format("\tmul $s0, $s0, 4\n");
        fileWriter.format("\taddu $s0, $t%d, $s0\n", arrayInd);
        fileWriter.format("\tsw $t%d, 0($s0)\n", valInd);
    }

}
