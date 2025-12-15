package task3;

import java.util.Map;

/**
 * Runnable that writes key-value pairs to a shared map
 */
public class MapWriter implements Runnable {
    private final Map<String, Integer> map;
    private final String prefix;
    private final int start;
    private final int end;

    public MapWriter(Map<String, Integer> map, String prefix, int start, int end) {
        this.map = map;
        this.prefix = prefix;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {
            String key = prefix + i;
            map.put(key, i * 10);
            System.out.println("[" + Thread.currentThread().getName() + "] Put: " + key + " -> " + (i * 10));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
