/**
 * Enhanced implementation demonstrating reentrant vs non-reentrant behavior
 */
public class ReentrantSumEnhanced {
    
    /**
     * Reentrant version - uses only local variables and parameters
     * @param arr the array to sum
     * @param index starting index
     * @return sum of elements from index to end
     */
    public static int sumArrayReentrant(int[] arr, int index) {
        if (index >= arr.length) {
            return 0;
        }
        return arr[index] + sumArrayReentrant(arr, index + 1);
    }
    
    private static int sharedSum = 0;
    
    /**
     * Non-reentrant version - uses shared mutable state
     * NOT thread-safe without synchronization
     * @param arr the array to sum
     * @param index starting index
     * @return sum of elements from index to end
     */
    public static int sumArrayNonReentrant(int[] arr, int index) {
        if (index >= arr.length) {
            return sharedSum;
        }
        sharedSum += arr[index];
        return sumArrayNonReentrant(arr, index + 1);
    }
    
    /**
     * Synchronized non-reentrant version
     * Thread-safe but slower due to synchronization overhead
     * @param arr the array to sum
     * @param index starting index
     * @return sum of elements from index to end
     */
    public static synchronized int sumArraySynchronized(int[] arr, int index) {
        if (index == 0) {
            sharedSum = 0;
        }
        if (index >= arr.length) {
            return sharedSum;
        }
        sharedSum += arr[index];
        return sumArraySynchronized(arr, index + 1);
    }
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Enhanced Reentrant Recursive Sum");
        System.out.println("=================================\n");
        
        int[] testArray = {1, 2, 3, 4, 5};
        System.out.println("Test array: " + java.util.Arrays.toString(testArray));
        System.out.println("Expected sum: 15\n");
        
        System.out.println("=== 1. Reentrant Version (Thread-Safe) ===");
        testReentrant();
        
        System.out.println("\n=== 2. Non-Reentrant Version (NOT Thread-Safe) ===");
        testNonReentrant();
        
        System.out.println("\n=== 3. Synchronized Version (Thread-Safe but slower) ===");
        testSynchronized();
        
        System.out.println("\n=== Performance Comparison ===");
        performanceTest();
    }
    
    private static void testReentrant() throws InterruptedException {
        Thread[] threads = new Thread[5];
        int[][] arrays = {
            {1, 2, 3, 4, 5},
            {10, 20, 30},
            {100, 200},
            {5, 10, 15, 20},
            {1, 1, 1, 1, 1}
        };
        int[] expected = {15, 60, 300, 50, 5};
        
        for (int i = 0; i < threads.length; i++) {
            final int idx = i;
            threads[i] = new Thread(() -> {
                int result = sumArrayReentrant(arrays[idx], 0);
                System.out.printf("Thread %d: %s = %d (expected: %d) %s\n", 
                    idx + 1, 
                    java.util.Arrays.toString(arrays[idx]), 
                    result, 
                    expected[idx],
                    result == expected[idx] ? "✓" : "✗");
            });
            threads[i].start();
        }
        
        for (Thread t : threads) {
            t.join();
        }
    }
    
    private static void testNonReentrant() throws InterruptedException {
        Thread[] threads = new Thread[5];
        int[][] arrays = {
            {1, 2, 3, 4, 5},
            {10, 20, 30},
            {100, 200},
            {5, 10, 15, 20},
            {1, 1, 1, 1, 1}
        };
        
        System.out.println("WARNING: Results will likely be incorrect due to race conditions!");
        
        for (int i = 0; i < threads.length; i++) {
            final int idx = i;
            threads[i] = new Thread(() -> {
                sharedSum = 0;
                int result = sumArrayNonReentrant(arrays[idx], 0);
                System.out.printf("Thread %d: %s = %d (unpredictable)\n", 
                    idx + 1, 
                    java.util.Arrays.toString(arrays[idx]), 
                    result);
            });
            threads[i].start();
        }
        
        for (Thread t : threads) {
            t.join();
        }
    }
    
    private static void testSynchronized() throws InterruptedException {
        Thread[] threads = new Thread[5];
        int[][] arrays = {
            {1, 2, 3, 4, 5},
            {10, 20, 30},
            {100, 200},
            {5, 10, 15, 20},
            {1, 1, 1, 1, 1}
        };
        int[] expected = {15, 60, 300, 50, 5};
        
        for (int i = 0; i < threads.length; i++) {
            final int idx = i;
            threads[i] = new Thread(() -> {
                int result = sumArraySynchronized(arrays[idx], 0);
                System.out.printf("Thread %d: %s = %d (expected: %d) %s\n", 
                    idx + 1, 
                    java.util.Arrays.toString(arrays[idx]), 
                    result, 
                    expected[idx],
                    result == expected[idx] ? "✓" : "✗");
            });
            threads[i].start();
        }
        
        for (Thread t : threads) {
            t.join();
        }
    }
    
    private static void performanceTest() {
        int[] largeArray = new int[1000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i + 1;
        }
        
        int iterations = 10000;
        
        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            sumArrayReentrant(largeArray, 0);
        }
        long reentrantTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            sumArraySynchronized(largeArray, 0);
        }
        long synchronizedTime = System.nanoTime() - start;
        
        System.out.println("Iterations: " + iterations);
        System.out.printf("Reentrant: %.2f ms\n", reentrantTime / 1_000_000.0);
        System.out.printf("Synchronized: %.2f ms\n", synchronizedTime / 1_000_000.0);
        System.out.printf("Overhead: %.2fx slower\n", (double) synchronizedTime / reentrantTime);
    }
}
