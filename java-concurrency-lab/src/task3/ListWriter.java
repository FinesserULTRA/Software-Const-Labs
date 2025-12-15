package task3;

import java.util.List;

/**
 * Runnable that writes values to a shared list
 */
public class ListWriter implements Runnable {
    private final List<Integer> list;
    private final int start;
    private final int end;

    public ListWriter(List<Integer> list, int start, int end) {
        this.list = list;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {
            list.add(i);
            System.out.println("[" + Thread.currentThread().getName() + "] Added: " + i);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
