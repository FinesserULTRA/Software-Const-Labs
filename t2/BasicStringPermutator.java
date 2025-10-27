import java.util.ArrayList;
import java.util.List;

/**
 * Task 2: Basic Recursive String Permutations (Normal Code)
 * Generates all permutations of a string using a recursive swapping algorithm.
 */
public class BasicStringPermutator {

    /**
     * Public method to start the permutation generation.
     * @param inputString The string to permute.
     * @return A list of all generated permutations.
     * @throws IllegalArgumentException if the input string is null.
     */
    public List<String> generatePermutations(String inputString) {
        // 5. Implement error handling for null input
        if (inputString == null) {
            throw new IllegalArgumentException("Input string cannot be null.");
        }
        
        // Handle empty string case
        if (inputString.isEmpty()) {
            return List.of("");
        }

        List<String> result = new ArrayList<>();
        char[] charArray = inputString.toCharArray();
        
        // 2. Implement a recursive function
        permute(charArray, 0, result);
        
        return result;
    }

    /**
     * Recursive helper function using the swapping method.
     * @param arr The character array of the string.
     * @param k The starting index for the current subproblem (fix position).
     * @param result List to store the permutations.
     */
    private void permute(char[] arr, int k, List<String> result) {
        // Base Case: If k reaches the end, a full permutation is found.
        if (k == arr.length - 1) {
            result.add(new String(arr));
            return;
        }

        // Recursive Step: Iterate through characters from k to the end.
        for (int i = k; i < arr.length; i++) {
            // 1. Swap character at k with the character at i (fixes character k)
            swap(arr, k, i);
            
            // 2. Recursively find permutations for the remaining substring (k+1)
            permute(arr, k + 1, result);
            
            // 3. Backtrack: Swap them back to restore the original array state
            //    before moving to the next iteration (i+1).
            swap(arr, k, i); 
        }
    }

    private void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java BasicStringPermutator <inputString>");
            return;
        }
        String input = args[0];
        try {
            BasicStringPermutator permutator = new BasicStringPermutator();
            List<String> results = permutator.generatePermutations(input);
            System.out.println("Permutations of '" + input + "' (" + results.size() + "):");
            results.forEach(System.out::println);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}