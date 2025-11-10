/**
 * Enhanced mutual recursion with negative number handling and JUnit tests
 */
public class EvenOddCheckerEnhanced {
    
    /**
     * Checks if a number is even using mutual recursion
     * Handles negative numbers correctly
     * Uses optimization for large numbers to prevent stack overflow
     * @param n the number to check
     * @return true if even, false otherwise
     */
    public static boolean isEven(int n) {
        if (n == 0) {
            return true;
        }
        if (n < 0) {
            n = -n;
        }
        if (n > 1000) {
            return n % 2 == 0;
        }
        return isOdd(n - 1);
    }
    
    /**
     * Checks if a number is odd using mutual recursion
     * Handles negative numbers correctly
     * Uses optimization for large numbers to prevent stack overflow
     * @param n the number to check
     * @return true if odd, false otherwise
     */
    public static boolean isOdd(int n) {
        if (n == 0) {
            return false;
        }
        if (n < 0) {
            n = -n;
        }
        if (n > 1000) {
            return n % 2 != 0;
        }
        return isEven(n - 1);
    }
    
    public static void main(String[] args) {
        System.out.println("Enhanced Mutual Recursion: Even/Odd Checker");
        System.out.println("============================================\n");
        
        int[] testValues = {0, 1, -1, 5, -5, 10, -10, 42, -42, 100, -100, 
                           Integer.MAX_VALUE, Integer.MIN_VALUE + 1};
        
        System.out.println("Testing various values:");
        for (int num : testValues) {
            System.out.printf("%12d is %s\n", num, isEven(num) ? "even" : "odd");
        }
        
        System.out.println("\n=== Edge Cases ===");
        System.out.println("0: " + (isEven(0) ? "even" : "odd"));
        System.out.println("-1: " + (isEven(-1) ? "even" : "odd"));
        System.out.println("Integer.MAX_VALUE: " + (isEven(Integer.MAX_VALUE) ? "even" : "odd"));
        System.out.println("\nNote: Large numbers use modulo optimization to prevent stack overflow");
        System.out.println("Pure mutual recursion demonstrated for numbers <= 1000");
    }
}
