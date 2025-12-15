package task2;

/**
 * Lab 14 - Task 2: Thread Synchronization
 * 
 * This program demonstrates thread synchronization to avoid race conditions.
 * Three threads access a shared counter and increment it 100 times each.
 * Without synchronization, the final value would be unpredictable.
 * With synchronization, the final value is guaranteed to be 300.
 */
public class ThreadSynchronization {

    public static void main(String[] args) {
        SharedCounter counter = new SharedCounter();

        // Create three threads that will increment the shared counter
        Thread thread1 = new Thread(new CounterIncrementer(counter), "Thread-1");
        Thread thread2 = new Thread(new CounterIncrementer(counter), "Thread-2");
        Thread thread3 = new Thread(new CounterIncrementer(counter), "Thread-3");

        System.out.println("Starting three threads to increment a shared counter...\n");

        // Start all threads
        thread1.start();
        thread2.start();
        thread3.start();

        // Wait for all threads to complete
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nAll threads have completed.");
        System.out.println("Final Counter Value: " + counter.getCount());
        System.out.println("Expected Value: 300");

        if (counter.getCount() == 300) {
            System.out.println("SUCCESS: Synchronization worked correctly!");
        } else {
            System.out.println("FAILURE: Race condition occurred!");
        }
    }
}
