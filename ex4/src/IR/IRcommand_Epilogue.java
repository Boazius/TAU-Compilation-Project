package IR;

import MIPS.MIPSGenerator;

public class IRcommand_Epilogue extends IRcommand {
    //not in liveness
    public IRcommand_Epilogue() {
    }

    public void MIPSme() {
        System.out.println("IRcommand_Epilogue" + "- MIPSme");

        MIPSGenerator.getInstance().epilogue();

    }

}
