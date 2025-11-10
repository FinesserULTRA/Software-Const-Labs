/**
 * Basic reentrant recursive sum implementation
 */
public class ReentrantSum {
    
    /**
     * Recursively sums array elements starting from given index
     * Uses only local variables - reentrant safe
     * @param arr the array to sum
     * @param index starting index
     * @return sum of elements from index to end
     */
    public static int sumArray(int[] arr, int index) {
        if (index >= arr.length) {
            return 0;
        }
        return arr[index] + sumArray(arr, index + 1);
    }
    
    /**
     * Convenience method to sum entire array
     * @param arr the array to sum
     * @return sum of all elements
     */
    public static int sumArray(int[] arr) {
        return sumArray(arr, 0);
    }
    
    public static void main(String[] args) {
        System.out.println("Reentrant Recursive Sum");
        System.out.println("=======================\n");
        
        int[] testArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        
        System.out.println("Array: " + java.util.Arrays.toString(testArray));
        System.out.println("Sum: " + sumArray(testArray));
        
        System.out.println("\n=== Testing Reentrancy with Threads ===");
        
        Thread thread1 = new Thread(() -> {
            int[] arr1 = {1, 2, 3, 4, 5};
            int result = sumArray(arr1);
            System.out.println("Thread 1 - Array: " + java.util.Arrays.toString(arr1) + " Sum: " + result);
        });
        
        Thread thread2 = new Thread(() -> {
            int[] arr2 = {10, 20, 30, 40, 50};
            int result = sumArray(arr2);
            System.out.println("Thread 2 - Array: " + java.util.Arrays.toString(arr2) + " Sum: " + result);
        });
        
        Thread thread3 = new Thread(() -> {
            int[] arr3 = {100, 200, 300};
            int result = sumArray(arr3);
            System.out.println("Thread 3 - Array: " + java.util.Arrays.toString(arr3) + " Sum: " + result);
        });
        
        thread1.start();
        thread2.start();
        thread3.start();
        
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            System.out.println("\nAll threads completed successfully - method is reentrant!");
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
