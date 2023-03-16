package IR;

import AST.AST_Node;

//not in liveness
public class IRcommand_Declare_Func extends IRcommand {
    public String name;
    public int localVars;
    public String className; // is nessecary?
    public String labelEnd;

    public IRcommand_Declare_Func(String name) {
        this.name = name;
    }

    public void MIPSme() {
        System.out.println("IRcommand_Declare_Func" + "- MIPSme");
        // prologue
        String labelStart;
        if (className != null)
            labelStart = className + "_" + name;
        else {
            labelStart = IRcommand.getFreshLabel("start_" + name);
            AST_Node.offsets.put(name, labelStart);
        }
        (new IRcommand_Label(labelStart)).MIPSme();
        (new IRcommand_Prologue(localVars)).MIPSme();
    }
}
