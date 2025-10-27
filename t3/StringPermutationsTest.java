import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Task 2: String Permutations Test Suite
 * Covers: base cases, recursive logic, enhancements, error handling, correctness
 * Run: java StringPermutationsTest
 */
public class StringPermutationsTest {

    private EnhancedStringPermutator permutator = new EnhancedStringPermutator();
    private int testsPassed = 0;
    private int testsFailed = 0;

    public static void main(String[] args) {
        StringPermutationsTest tester = new StringPermutationsTest();
        tester.runAllTests();
        tester.printSummary();
    }

    private void runAllTests() {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("  Task 2: String Permutations - Test Suite");
        System.out.println("═══════════════════════════════════════════════════════════\n");

        // BASE CASES
        System.out.println("┌─ BASE CASES ──────────────────────────────────────────────┐");
        testEmptyString();
        testSingleCharacter();
        testTwoCharacters();
        System.out.println("└─────────────────────────────────────────────────────────────┘\n");

        // RECURSIVE
        System.out.println("┌─ RECURSIVE LOGIC ────────────────────────────────────────┐");
        testRecursiveFactorial();
        testRecursiveUnique();
        System.out.println("└─────────────────────────────────────────────────────────────┘\n");

        // ITERATIVE
        System.out.println("┌─ ITERATIVE (HEAP'S ALGORITHM) ───────────────────────────┐");
        testIterativeMatches();
        System.out.println("└─────────────────────────────────────────────────────────────┘\n");

        // ERROR HANDLING
        System.out.println("┌─ ERROR HANDLING ──────────────────────────────────────────┐");
        testNullInput();
        System.out.println("└─────────────────────────────────────────────────────────────┘\n");

        // CORRECTNESS
        System.out.println("┌─ CORRECTNESS VERIFICATION ───────────────────────────────┐");
        testEquivalenceAndUniqueness();
        System.out.println("└─────────────────────────────────────────────────────────────┘\n");
    }

    // ========== BASE CASES ==========

    private void testEmptyString() {
        try {
            List<String> result = permutator.generateRecursivePermutations("", false);
            assertTrue(result.size() == 1 && result.get(0).equals(""), "Empty string failed");
            pass("Base: Empty String");
        } catch (Exception e) {
            fail("Base: Empty String", e);
        }
    }

    private void testSingleCharacter() {
        try {
            List<String> result = permutator.generateRecursivePermutations("A", false);
            assertTrue(result.size() == 1 && result.get(0).equals("A"), "Single char failed");
            pass("Base: Single Character");
        } catch (Exception e) {
            fail("Base: Single Character", e);
        }
    }

    private void testTwoCharacters() {
        try {
            List<String> result = permutator.generateRecursivePermutations("AB", false);
            assertTrue(result.size() == 2 && result.contains("AB") && result.contains("BA"), 
                "Two chars failed");
            pass("Base: Two Characters");
        } catch (Exception e) {
            fail("Base: Two Characters", e);
        }
    }

    // ========== RECURSIVE LOGIC ==========

    private void testRecursiveFactorial() {
        try {
            // Test 3! = 6 and 4! = 24
            List<String> r3 = permutator.generateRecursivePermutations("ABC", false);
            List<String> r4 = permutator.generateRecursivePermutations("ABCD", false);
            
            assertTrue(r3.size() == 6, "3! should be 6");
            assertTrue(r4.size() == 24, "4! should be 24");
            assertTrue(new HashSet<>(r4).size() == 24, "No duplicates");
            
            pass("Recursive: Factorial Count (3!=6, 4!=24)");
        } catch (Exception e) {
            fail("Recursive: Factorial Count", e);
        }
    }

    private void testRecursiveUnique() {
        try {
            // Enhancement 1: Duplicate handling
            List<String> unique = permutator.generateRecursivePermutations("AAB", true);
            List<String> mixed = permutator.generateRecursivePermutations("AABB", true);
            
            // 3!/2! = 3, and 4!/(2!*2!) = 6
            assertTrue(unique.size() == 3, "AAB should have 3 unique");
            assertTrue(mixed.size() == 6, "AABB should have 6 unique");
            
            pass("Recursive: Unique Permutations (AAB=3, AABB=6)");
        } catch (Exception e) {
            fail("Recursive: Unique Permutations", e);
        }
    }

    // ========== ITERATIVE LOGIC ==========

    private void testIterativeMatches() {
        try {
            // Enhancement 2: Heap's Algorithm
            String input = "ABCD";
            List<String> rec = permutator.generateRecursivePermutations(input, false);
            List<String> iter = permutator.generateIterativePermutations(input);
            
            assertTrue(rec.size() == iter.size(), "Size mismatch");
            assertTrue(new HashSet<>(rec).equals(new HashSet<>(iter)), "Results differ");
            
            pass("Iterative: Heap's Algorithm Equivalent");
        } catch (Exception e) {
            fail("Iterative: Heap's Algorithm", e);
        }
    }

    // ========== ERROR HANDLING ==========

    private void testNullInput() {
        try {
            boolean rec_throws = false, iter_throws = false;
            
            try {
                permutator.generateRecursivePermutations(null, false);
            } catch (IllegalArgumentException e) {
                rec_throws = true;
            }
            
            try {
                permutator.generateIterativePermutations(null);
            } catch (IllegalArgumentException e) {
                iter_throws = true;
            }
            
            assertTrue(rec_throws && iter_throws, "Should throw on null");
            pass("Error: Null Input Throws Exception");
        } catch (Exception e) {
            fail("Error: Null Input", e);
        }
    }

    // ========== CORRECTNESS ==========

    private void testEquivalenceAndUniqueness() {
        try {
            List<String> result = permutator.generateRecursivePermutations("ABCD", false);
            Set<String> unique = new HashSet<>(result);
            
            // Check uniqueness
            assertTrue(result.size() == unique.size(), "Found duplicates");
            
            // Check length and characters
            String input = "ABCD";
            Set<Character> inputChars = toCharSet(input);
            for (String perm : result) {
                assertTrue(perm.length() == input.length(), "Wrong length");
                assertTrue(toCharSet(perm).equals(inputChars), "Missing chars");
            }
            
            pass("Correctness: Uniqueness & Char Presence");
        } catch (Exception e) {
            fail("Correctness: Uniqueness & Char Presence", e);
        }
    }

    // ========== HELPER METHODS ==========

    private void assertTrue(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private Set<Character> toCharSet(String s) {
        Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) {
            set.add(c);
        }
        return set;
    }

    private void pass(String testName) {
        System.out.println("  ✓ " + testName);
        testsPassed++;
    }

    private void fail(String testName, Exception e) {
        System.out.println("  ✗ " + testName);
        System.out.println("    Reason: " + e.getMessage());
        testsFailed++;
    }

    private void printSummary() {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("TEST SUMMARY");
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("Passed: " + testsPassed);
        System.out.println("Failed: " + testsFailed);
        System.out.println("Total:  " + (testsPassed + testsFailed));

        if (testsFailed == 0) {
            System.out.println("\n✓ All tests passed!");
        } else {
            System.out.println("\n✗ Some tests failed.");
            System.exit(1);
        }
    }
}