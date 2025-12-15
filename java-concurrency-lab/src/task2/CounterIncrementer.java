package task2;

/**
 * Runnable that increments the shared counter 100 times
 */
public class CounterIncrementer implements Runnable {
    private final SharedCounter counter;

    public CounterIncrementer(SharedCounter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            counter.increment();
        }
        System.out.println("[" + Thread.currentThread().getName() + "] Completed 100 increments.");
    }
}
