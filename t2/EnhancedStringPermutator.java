import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Task 2: Enhanced String Permutations
 * Enhancements:
 * 1. Option to exclude duplicate permutations
 * 2. Iterative implementation (Heap's Algorithm)
 */
public class EnhancedStringPermutator {

    // Enhancement 1: Recursive with duplicate handling
    public List<String> generateRecursivePermutations(String input, boolean excludeDuplicates) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null.");
        }
        if (input.isEmpty()) {
            return List.of("");
        }

        Set<String> result = new HashSet<>();
        char[] arr = input.toCharArray();
        
        if (excludeDuplicates) {
            permuteNoDuplicates(arr, 0, result);
        } else {
            permute(arr, 0, result);
        }
        
        return new ArrayList<>(result);
    }

    private void permute(char[] arr, int k, Set<String> result) {
        if (k == arr.length - 1) {
            result.add(new String(arr));
            return;
        }
        
        for (int i = k; i < arr.length; i++) {
            swap(arr, k, i);
            permute(arr, k + 1, result);
            swap(arr, k, i);
        }
    }

    private void permuteNoDuplicates(char[] arr, int k, Set<String> result) {
        if (k == arr.length - 1) {
            result.add(new String(arr));
            return;
        }
        
        Set<Character> used = new HashSet<>();
        for (int i = k; i < arr.length; i++) {
            if (!used.contains(arr[i])) {
                used.add(arr[i]);
                swap(arr, k, i);
                permuteNoDuplicates(arr, k + 1, result);
                swap(arr, k, i);
            }
        }
    }

    // Enhancement 2: Iterative (Heap's Algorithm)
    public List<String> generateIterativePermutations(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null.");
        }
        
        char[] arr = input.toCharArray();
        int n = arr.length;
        List<String> result = new ArrayList<>();
        
        if (n == 0) return result;
        if (n == 1) {
            result.add(input);
            return result;
        }
        
        result.add(new String(arr));
        int[] c = new int[n];
        int i = 1;
        
        while (i < n) {
            if (c[i] < i) {
                int j = (i % 2 == 0) ? 0 : c[i];
                swap(arr, i, j);
                result.add(new String(arr));
                c[i]++;
                i = 1;
            } else {
                c[i] = 0;
                i++;
            }
        }
        
        return new ArrayList<>(new HashSet<>(result));
    }

    private void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        EnhancedStringPermutator perm = new EnhancedStringPermutator();

        System.out.println("=== Enhanced String Permutations ===\n");

        // Test 1: Recursive all permutations
        System.out.println("Test 1: Recursive (all)");
        List<String> r1 = perm.generateRecursivePermutations("ABC", false);
        System.out.println("Input: ABC | Count: " + r1.size() + " | " + r1);

        // Test 2: Recursive unique
        System.out.println("\nTest 2: Recursive (unique only)");
        List<String> r2 = perm.generateRecursivePermutations("AAB", true);
        System.out.println("Input: AAB | Count: " + r2.size() + " | " + r2);

        // Test 3: Iterative
        System.out.println("\nTest 3: Iterative (Heap's)");
        List<String> r3 = perm.generateIterativePermutations("ABC");
        System.out.println("Input: ABC | Count: " + r3.size() + " | " + r3);
    }
}