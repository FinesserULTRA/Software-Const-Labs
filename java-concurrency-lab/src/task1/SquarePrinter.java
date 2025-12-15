package task1;

/**
 * Runnable implementation that prints squares of numbers from 1 to 10
 */
public class SquarePrinter implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("[" + Thread.currentThread().getName() + "] Square of " + i + " = " + (i * i));
            try {
                Thread.sleep(100); // Small delay to observe interleaving
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
