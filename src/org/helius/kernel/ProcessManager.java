package org.helius.kernel;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class ProcessManager {

    private static final Map<Integer, Process> processes = new HashMap<>();
    private static int nextPid = 0;
    private static final Random random = new Random();
    private static final StringBuilder builder = new StringBuilder();
    private static final PrintWriter writer;

    static {
        try {
            writer = new PrintWriter(new FileWriter("src/result.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createProcess(String name, int cycleCount) {
        int pid = nextPid++;
        Process process = new Process(name, pid, cycleCount);
        processes.put(pid, process);
    }

    public static void run() {
        while (!processes.isEmpty()) {
            reschedule();
            removeFinishedProcesses();
        }
    }

    private static void reschedule() {
        for (Process process : processes.values()) {
            if (!process.hasCycles()) continue;
            switch (process.getStatus()) {
                case Ready -> executeProcess(process);
                case Blocked -> checkIOStatus(process);
            }
        }
    }

    private static void executeProcess(Process process) {
        process.nCpu++;
        process.setStatus(ProcessStatus.Executing);
        printProcTransition(process, ProcessStatus.Ready, ProcessStatus.Executing);

        if (processCycles(process)) {
            process.setStatus(ProcessStatus.Blocked);
            printProcTransition(process, ProcessStatus.Executing, ProcessStatus.Blocked);
        } else {
            process.setStatus(ProcessStatus.Ready);
            printProcTransition(process, ProcessStatus.Executing, ProcessStatus.Ready);
        }
    }

    private static boolean processCycles(Process process) {
        int quantum = 1000;
        for (int i = 0; i < quantum; i++) {
            process.setCycleCount(process.getCycleCount() - 1);
            process.pt++;
            process.pc = process.pt + 1;

            if (inputOutputOperation()) {
                process.io++;
                System.out.println(process.getName() + " Executing I/O operation. Remaining cycles: " + process.getCycleCount());
                return true;
            }

            if (process.getCycleCount() <= 0) {
                return false;
            }
        }
        return false;
    }

    private static void checkIOStatus(Process process) {
        if (getIOFinished()) {
            System.out.println(process.getName() + " IO Finished.");
            process.setStatus(ProcessStatus.Ready);
            printProcTransition(process, ProcessStatus.Blocked, ProcessStatus.Ready);
        }
    }

    private static void removeFinishedProcesses() {
        List<Integer> finishedPids = new ArrayList<>();
        for (Process process : processes.values()) {
            if (!process.hasCycles()) {
                System.out.println("Process " + process.getName() + " has completed execution.");
                logProcessData(process, "Final State");
                finishedPids.add(process.getPid());
            }
        }
        for (Integer pid : finishedPids) {
            processes.remove(pid);
        }
    }

    private static void printProcTransition(Process process, ProcessStatus oldStatus, ProcessStatus newStatus) {
        String transition = process.getName() + ": " + oldStatus.name() + " >> " + newStatus.name();
        System.out.println(transition);
        logProcessData(process, transition);
    }

    private static void logProcessData(Process process, String transition) {
        builder.append("=".repeat(70)).append("\n");
        builder.append("Transition: ").append(transition).append("\n");
        builder.append(String.format("PID: %d, TP: %d, PC: %d, Estado: %s, NES: %d, N_CPU: %d%n",
                process.getPid(), process.pt, process.pc, process.getStatus().name(), process.io, process.nCpu));
        builder.append("=".repeat(70)).append("\n");
        builder.append("\n");
        writer.write(builder.toString());
        writer.flush();
        builder.setLength(0);
    }

    private static boolean getIOFinished() {
        return random.nextInt(3) == 1;
    }

    private static boolean inputOutputOperation() {
        return random.nextInt(100) == 1;
    }
}
