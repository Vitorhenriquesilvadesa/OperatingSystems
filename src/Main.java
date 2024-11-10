import org.helius.kernel.ProcessManager;

public class Main {
    public static void main(String[] args) {
        ProcessManager.createProcess("Proc 0", 10000);
        ProcessManager.createProcess("Proc 1", 5000);
        ProcessManager.createProcess("Proc 2", 7000);
        ProcessManager.createProcess("Proc 3", 3000);
        ProcessManager.createProcess("Proc 4", 3000);
        ProcessManager.createProcess("Proc 5", 8000);
        ProcessManager.createProcess("Proc 6", 2000);
        ProcessManager.createProcess("Proc 7", 5000);
        ProcessManager.createProcess("Proc 8", 4000);
        ProcessManager.createProcess("Proc 9", 10000);

        ProcessManager.run();
    }
}