/**
 * Basic implementation of integer-to-string conversion using recursion
 * Supports bases 2-16
 */
public class IntegerToString {
    
    /**
     * Converts an integer to its string representation in the specified base
     * @param n the integer to convert
     * @param base the base (2-16)
     * @return string representation of n in the given base
     */
    public static String stringValue(int n, int base) {
        if (base < 2 || base > 16) {
            throw new IllegalArgumentException("Base must be between 2 and 16");
        }
        
        if (n < 0) {
            return "-" + stringValue(-n, base);
        }
        
        if (n < base) {
            return digitToChar(n);
        }
        
        return stringValue(n / base, base) + digitToChar(n % base);
    }
    
    /**
     * Helper method to convert a digit to its character representation
     * @param digit the digit (0-15)
     * @return character representation
     */
    private static String digitToChar(int digit) {
        if (digit < 10) {
            return String.valueOf((char) ('0' + digit));
        }
        return String.valueOf((char) ('A' + digit - 10));
    }
    
    /**
     * Iterative version for comparison
     * @param n the integer to convert
     * @param base the base (2-16)
     * @return string representation
     */
    public static String stringValueIterative(int n, int base) {
        if (base < 2 || base > 16) {
            throw new IllegalArgumentException("Base must be between 2 and 16");
        }
        
        if (n == 0) return "0";
        
        boolean negative = n < 0;
        n = Math.abs(n);
        
        StringBuilder result = new StringBuilder();
        while (n > 0) {
            result.insert(0, digitToChar(n % base));
            n /= base;
        }
        
        if (negative) {
            result.insert(0, '-');
        }
        
        return result.toString();
    }
    
    public static void main(String[] args) {
        System.out.println("Testing Integer-to-String Conversion (Base 2-16)");
        System.out.println("==============================================");
        
        int testNum = 255;
        System.out.println("Number: " + testNum);
        System.out.println("Binary (base 2): " + stringValue(testNum, 2));
        System.out.println("Octal (base 8): " + stringValue(testNum, 8));
        System.out.println("Decimal (base 10): " + stringValue(testNum, 10));
        System.out.println("Hexadecimal (base 16): " + stringValue(testNum, 16));
        
        System.out.println("\nNegative number: " + stringValue(-42, 10));
        
        System.out.println("\nPerformance comparison:");
        long start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            stringValue(12345, 10);
        }
        long recursiveTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            stringValueIterative(12345, 10);
        }
        long iterativeTime = System.nanoTime() - start;
        
        System.out.println("Recursive: " + recursiveTime + " ns");
        System.out.println("Iterative: " + iterativeTime + " ns");
    }
}
