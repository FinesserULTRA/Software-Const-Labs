package task3;

import java.util.Map;

/**
 * Runnable that reads values from a shared map
 */
public class MapReader implements Runnable {
    private final Map<String, Integer> map;

    public MapReader(Map<String, Integer> map) {
        this.map = map;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("[" + Thread.currentThread().getName() + "] Reading map: " + map);
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
