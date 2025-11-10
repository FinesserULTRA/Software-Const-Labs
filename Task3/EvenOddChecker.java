/**
 * Basic mutual recursion implementation for even/odd checking
 */
public class EvenOddChecker {
    
    /**
     * Checks if a number is even using mutual recursion
     * @param n the number to check
     * @return true if even, false otherwise
     */
    public static boolean isEven(int n) {
        if (n == 0) {
            return true;
        }
        return isOdd(n - 1);
    }
    
    /**
     * Checks if a number is odd using mutual recursion
     * @param n the number to check
     * @return true if odd, false otherwise
     */
    public static boolean isOdd(int n) {
        if (n == 0) {
            return false;
        }
        return isEven(n - 1);
    }
    
    public static void main(String[] args) {
        System.out.println("Mutual Recursion: Even/Odd Checker");
        System.out.println("===================================\n");
        
        int[] testValues = {0, 1, 5, 10, 17, 42, 100};
        
        for (int num : testValues) {
            System.out.printf("%d is %s\n", num, isEven(num) ? "even" : "odd");
        }
    }
}
