package CFG;


import IR.IRcommand;

import java.util.HashSet;

public class basicBlock {
    basicBlock father;
    basicBlock direct;
    basicBlock condWorked;
    int time;
    IRcommand line;
    HashSet<String> inSet;
    HashSet<String> outSet;
    boolean inFunc;
    HashSet<String> FuncScope;

    public basicBlock(int time, IRcommand line) {
        this.time = time;
        this.line = line;
        inSet = new HashSet<>();
        outSet = new HashSet<>();
        FuncScope = new HashSet<>();
        inFunc = false;
    }

}