/**
 * Enhanced implementation supporting bases 2-36 with performance analysis
 */
public class IntegerToStringEnhanced {
    
    /**
     * Converts an integer to its string representation in the specified base
     * @param n the integer to convert
     * @param base the base (2-36)
     * @return string representation of n in the given base
     */
    public static String stringValue(int n, int base) {
        if (base < 2 || base > 36) {
            throw new IllegalArgumentException("Base must be between 2 and 36");
        }
        
        if (n == Integer.MIN_VALUE) {
            return "-" + stringValueHelper(-(n + 1), base, true);
        }
        
        if (n < 0) {
            return "-" + stringValue(-n, base);
        }
        
        return stringValueHelper(n, base, false);
    }
    
    /**
     * Helper method to handle recursion with special case handling
     * @param n the positive integer
     * @param base the base
     * @param addOne whether to add 1 to last digit (for MIN_VALUE handling)
     * @return string representation
     */
    private static String stringValueHelper(int n, int base, boolean addOne) {
        if (n < base) {
            int digit = n + (addOne ? 1 : 0);
            return digitToChar(digit);
        }
        
        String result = stringValueHelper(n / base, base, false) + digitToChar(n % base);
        if (addOne) {
            return result.substring(0, result.length() - 1) + digitToChar((n % base) + 1);
        }
        return result;
    }
    
    /**
     * Converts a digit to its character representation (0-9, A-Z)
     * @param digit the digit (0-35)
     * @return character representation
     */
    private static String digitToChar(int digit) {
        if (digit < 10) {
            return String.valueOf((char) ('0' + digit));
        }
        return String.valueOf((char) ('A' + digit - 10));
    }
    
    /**
     * Iterative version for performance comparison
     * @param n the integer to convert
     * @param base the base (2-36)
     * @return string representation
     */
    public static String stringValueIterative(int n, int base) {
        if (base < 2 || base > 36) {
            throw new IllegalArgumentException("Base must be between 2 and 36");
        }
        
        if (n == 0) return "0";
        
        boolean negative = n < 0;
        long absN = Math.abs((long) n);
        
        StringBuilder result = new StringBuilder();
        while (absN > 0) {
            result.insert(0, digitToChar((int) (absN % base)));
            absN /= base;
        }
        
        if (negative) {
            result.insert(0, '-');
        }
        
        return result.toString();
    }
    
    public static void main(String[] args) {
        System.out.println("Enhanced Integer-to-String Conversion (Base 2-36)");
        System.out.println("=================================================");
        
        int testNum = 1234567;
        System.out.println("Number: " + testNum);
        System.out.println("Binary (base 2): " + stringValue(testNum, 2));
        System.out.println("Octal (base 8): " + stringValue(testNum, 8));
        System.out.println("Decimal (base 10): " + stringValue(testNum, 10));
        System.out.println("Hexadecimal (base 16): " + stringValue(testNum, 16));
        System.out.println("Base 36: " + stringValue(testNum, 36));
        
        System.out.println("\nNegative number: " + stringValue(-9999, 16));
        System.out.println("Integer.MIN_VALUE: " + stringValue(Integer.MIN_VALUE, 10));
        
        System.out.println("\n=== Performance Analysis ===");
        int iterations = 100000;
        int testValue = 123456789;
        
        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            stringValue(testValue, 10);
        }
        long recursiveTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            stringValueIterative(testValue, 10);
        }
        long iterativeTime = System.nanoTime() - start;
        
        System.out.println("Iterations: " + iterations);
        System.out.println("Recursive: " + recursiveTime / 1_000_000.0 + " ms");
        System.out.println("Iterative: " + iterativeTime / 1_000_000.0 + " ms");
        System.out.println("Ratio (R/I): " + String.format("%.2f", (double) recursiveTime / iterativeTime));
        
        System.out.println("\n=== All Bases 2-36 for 255 ===");
        for (int base = 2; base <= 36; base++) {
            System.out.printf("Base %2d: %s\n", base, stringValue(255, base));
        }
    }
}
