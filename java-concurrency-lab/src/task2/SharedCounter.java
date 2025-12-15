package task2;

/**
 * Shared counter class with synchronized increment method
 */
public class SharedCounter {
    private int count = 0;

    /**
     * Synchronized method to safely increment the counter.
     * The synchronized keyword ensures only one thread can execute this method at a
     * time.
     */
    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
