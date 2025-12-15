package task3;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Lab 14 - Task 3: Concurrent Data Structures
 * 
 * This program demonstrates thread-safe data structures:
 * - CopyOnWriteArrayList: A thread-safe variant of ArrayList
 * - ConcurrentHashMap: A thread-safe variant of HashMap
 * 
 * Multiple threads read and write concurrently without explicit
 * synchronization.
 */
public class ConcurrentDataStructures {

    // Thread-safe list
    private static final List<Integer> sharedList = new CopyOnWriteArrayList<>();

    // Thread-safe map
    private static final Map<String, Integer> sharedMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        System.out.println("=== Demonstrating CopyOnWriteArrayList ===\n");
        testCopyOnWriteArrayList();

        System.out.println("\n=== Demonstrating ConcurrentHashMap ===\n");
        testConcurrentHashMap();
    }

    /**
     * Test CopyOnWriteArrayList with multiple reader and writer threads
     */
    private static void testCopyOnWriteArrayList() {
        // Create writer threads
        Thread writer1 = new Thread(new ListWriter(sharedList, 1, 5), "ListWriter-1");
        Thread writer2 = new Thread(new ListWriter(sharedList, 6, 10), "ListWriter-2");

        // Create reader threads
        Thread reader1 = new Thread(new ListReader(sharedList), "ListReader-1");
        Thread reader2 = new Thread(new ListReader(sharedList), "ListReader-2");

        // Start all threads
        writer1.start();
        reader1.start();
        writer2.start();
        reader2.start();

        // Wait for completion
        try {
            writer1.join();
            writer2.join();
            reader1.join();
            reader2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nFinal List Contents: " + sharedList);
        System.out.println("List Size: " + sharedList.size());
    }

    /**
     * Test ConcurrentHashMap with multiple reader and writer threads
     */
    private static void testConcurrentHashMap() {
        // Create writer threads
        Thread mapWriter1 = new Thread(new MapWriter(sharedMap, "A", 1, 3), "MapWriter-1");
        Thread mapWriter2 = new Thread(new MapWriter(sharedMap, "B", 4, 6), "MapWriter-2");

        // Create reader threads
        Thread mapReader1 = new Thread(new MapReader(sharedMap), "MapReader-1");
        Thread mapReader2 = new Thread(new MapReader(sharedMap), "MapReader-2");

        // Start all threads
        mapWriter1.start();
        mapReader1.start();
        mapWriter2.start();
        mapReader2.start();

        // Wait for completion
        try {
            mapWriter1.join();
            mapWriter2.join();
            mapReader1.join();
            mapReader2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nFinal Map Contents: " + sharedMap);
        System.out.println("Map Size: " + sharedMap.size());
    }
}
