package org.helius.kernel;

public class Process {

    private final String name;
    private final int pid;
    private int cycleCount;
    private ProcessStatus status;
    public int pc;
    public int pt;
    public int io;
    public int nCpu;

    public Process(String name, int pid, int cycleCount) {
        this.name = name;
        this.pid = pid;
        this.cycleCount = cycleCount;
        this.status = ProcessStatus.Ready;
        this.pc = 0;
        this.pt = 0;
        this.io = 0;
        this.nCpu = 0;
    }

    public boolean hasCycles() {
        return cycleCount > 0;
    }

    public void setCycleCount(int cycleCount) {
        this.cycleCount = cycleCount;
    }

    public void setStatus(ProcessStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public int getPid() {
        return pid;
    }

    public int getCycleCount() {
        return cycleCount;
    }

    public ProcessStatus getStatus() {
        return status;
    }
}
