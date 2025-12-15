package task3;

import java.util.List;

/**
 * Runnable that reads values from a shared list
 */
public class ListReader implements Runnable {
    private final List<Integer> list;

    public ListReader(List<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("[" + Thread.currentThread().getName() + "] Reading list: " + list);
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
