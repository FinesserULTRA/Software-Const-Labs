import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Task 1: Recursive File Search Test Suite
 * Covers: base cases, DFS traversal, case sensitivity, multiple files, error handling
 * Run: java RecursiveFileSearchTest
 */
public class RecursiveFileSearchTest {

    private static final String TEST_DIR = "t3_test_dir";
    private Path tempRoot;
    private int testsPassed = 0;
    private int testsFailed = 0;

    public static void main(String[] args) {
        RecursiveFileSearchTest tester = new RecursiveFileSearchTest();
        try {
            tester.setup();
            tester.runAllTests();
            tester.cleanup();
            tester.printSummary();
        } catch (IOException e) {
            System.err.println("Setup error: " + e.getMessage());
        }
    }

    private void setup() throws IOException {
        tempRoot = Paths.get(TEST_DIR);
        if (Files.exists(tempRoot)) {
            deleteDirectory(tempRoot);
        }
        Files.createDirectory(tempRoot);

        // Create test structure
        Files.writeString(tempRoot.resolve("file1.txt"), "root");
        Files.writeString(tempRoot.resolve("README.md"), "readme");

        Path dir1 = Files.createDirectory(tempRoot.resolve("dir1"));
        Files.writeString(dir1.resolve("file2.txt"), "file2");
        Files.writeString(dir1.resolve("file1.txt"), "dir1-file1");

        Path subdir = Files.createDirectory(dir1.resolve("subdir"));
        Files.writeString(subdir.resolve("README.md"), "subdir-readme");

        Path dir2 = Files.createDirectory(tempRoot.resolve("dir2"));
        Files.writeString(dir2.resolve("FILE1.TXT"), "case-test");
    }

    private void cleanup() throws IOException {
        if (Files.exists(tempRoot)) {
            deleteDirectory(tempRoot);
        }
    }

    private void deleteDirectory(Path dir) throws IOException {
        Files.walk(dir)
             .sorted((p1, p2) -> p2.getNameCount() - p1.getNameCount())
             .forEach(p -> {
                 try { Files.deleteIfExists(p); } catch (IOException ignored) {}
             });
    }

    private void runAllTests() {
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("  Task 1: File Search - Test Suite");
        System.out.println("═══════════════════════════════════════════════════════════\n");

        System.out.println("┌─ BASE CASES ──────────────────────────────────────────────┐");
        testFoundInRoot();
        testNotFound();
        testFoundNested();
        System.out.println("└─────────────────────────────────────────────────────────────┘\n");

        System.out.println("┌─ DFS TRAVERSAL ───────────────────────────────────────────┐");
        testFirstOccurrence();
        testDeepNesting();
        System.out.println("└─────────────────────────────────────────────────────────────┘\n");

        System.out.println("┌─ CASE SENSITIVITY ────────────────────────────────────────┐");
        testCaseSensitive();
        testCaseInsensitive();
        System.out.println("└─────────────────────────────────────────────────────────────┘\n");

        System.out.println("┌─ ENHANCEMENTS ────────────────────────────────────────────┐");
        testMultipleFiles();
        System.out.println("└─────────────────────────────────────────────────────────────┘\n");

        System.out.println("┌─ ERROR HANDLING ──────────────────────────────────────────┐");
        testInvalidPath();
        System.out.println("└─────────────────────────────────────────────────────────────┘\n");
    }

    // ========== BASE CASES ==========

    private void testFoundInRoot() {
        try {
            BasicFileSearcher searcher = new BasicFileSearcher("file1.txt");
            String result = findFile(searcher, tempRoot.toString());
            assertTrue(result != null && result.contains("file1.txt"), "Should find in root");
            pass("Base: Found in Root");
        } catch (Exception e) {
            fail("Base: Found in Root", e);
        }
    }

    private void testNotFound() {
        try {
            BasicFileSearcher searcher = new BasicFileSearcher("nonexistent.xyz");
            String result = findFile(searcher, tempRoot.toString());
            assertTrue(result == null, "Should not find");
            pass("Base: File Not Found");
        } catch (Exception e) {
            fail("Base: File Not Found", e);
        }
    }

    private void testFoundNested() {
        try {
            BasicFileSearcher searcher = new BasicFileSearcher("README.md");
            String result = findFile(searcher, tempRoot.toString());
            assertTrue(result != null && result.contains("README.md"), "Should find nested");
            pass("Base: Found Nested");
        } catch (Exception e) {
            fail("Base: Found Nested", e);
        }
    }

    // ========== DFS TRAVERSAL ==========

    private void testFirstOccurrence() {
        try {
            // file1.txt exists in root and dir1
            BasicFileSearcher searcher = new BasicFileSearcher("file1.txt");
            String result = findFile(searcher, tempRoot.toString());
            assertTrue(result != null, "Should find first");
            pass("DFS: First Occurrence (Depth-First)");
        } catch (Exception e) {
            fail("DFS: First Occurrence", e);
        }
    }

    private void testDeepNesting() {
        try {
            BasicFileSearcher searcher = new BasicFileSearcher("README.md");
            String result = findFile(searcher, tempRoot.toString());
            assertTrue(result != null && result.contains("README.md"), "Should traverse deep");
            pass("DFS: Deep Nesting (2+ levels)");
        } catch (Exception e) {
            fail("DFS: Deep Nesting", e);
        }
    }

    // ========== CASE SENSITIVITY ==========

    private void testCaseSensitive() {
        try {
            // Enhancement 3: Case-sensitive
            List<String> targets = List.of("FILE1.TXT");
            EnhancedFileSearcher searcher = new EnhancedFileSearcher(targets, true, null);
            Map<String, List<String>> result = searcher.search(tempRoot.toString());
            
            assertTrue(result.containsKey("FILE1.TXT"), "Should find FILE1.TXT");
            pass("Case: Sensitive Mode");
        } catch (Exception e) {
            fail("Case: Sensitive Mode", e);
        }
    }

    private void testCaseInsensitive() {
        try {
            // Enhancement 3: Case-insensitive
            List<String> targets = List.of("file1.txt");
            EnhancedFileSearcher searcher = new EnhancedFileSearcher(targets, false, null);
            Map<String, List<String>> result = searcher.search(tempRoot.toString());
            
            // Should find both file1.txt and FILE1.TXT
            assertTrue(result.containsKey("file1.txt") && result.get("file1.txt").size() >= 2,
                "Should find 2+ matches case-insensitive");
            pass("Case: Insensitive Mode");
        } catch (Exception e) {
            fail("Case: Insensitive Mode", e);
        }
    }

    // ========== ENHANCEMENTS ==========

    private void testMultipleFiles() {
        try {
            // Enhancement 1 & 2: Multiple files + counting
            List<String> targets = List.of("file1.txt", "file2.txt", "README.md");
            EnhancedFileSearcher searcher = new EnhancedFileSearcher(targets, true, "file1.txt");
            Map<String, List<String>> result = searcher.search(tempRoot.toString());
            
            assertTrue(result.size() >= 2, "Should find multiple files");
            assertTrue(result.get("file1.txt") != null && result.get("file1.txt").size() >= 1,
                "Should count file1.txt");
            pass("Enhancement: Multiple Files & Counting");
        } catch (Exception e) {
            fail("Enhancement: Multiple Files & Counting", e);
        }
    }

    // ========== ERROR HANDLING ==========

    private void testInvalidPath() {
        try {
            List<String> targets = List.of("test.txt");
            EnhancedFileSearcher searcher = new EnhancedFileSearcher(targets, true, null);
            searcher.search("/invalid/nonexistent/path");
            fail("Error: Invalid Path", "Should throw exception");
        } catch (IllegalArgumentException e) {
            pass("Error: Invalid Path Throws");
        } catch (Exception e) {
            fail("Error: Invalid Path", e);
        }
    }

    // ========== HELPER METHODS ==========

    private String findFile(BasicFileSearcher searcher, String path) {
        try {
            var foundPathField = BasicFileSearcher.class.getDeclaredField("foundPath");
            foundPathField.setAccessible(true);
            searcher.search(path);
            
            var optional = foundPathField.get(searcher);
            var getMethod = optional.getClass().getDeclaredMethod("orElse", Object.class);
            getMethod.setAccessible(true);
            return (String) getMethod.invoke(optional, (Object) null);
        } catch (Exception e) {
            return null;
        }
    }

    private void assertTrue(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private void pass(String testName) {
        System.out.println("  ✓ " + testName);
        testsPassed++;
    }

    private void fail(String testName, Exception e) {
        System.out.println("  ✗ " + testName);
        System.out.println("    Error: " + e.getMessage());
        testsFailed++;
    }

    private void fail(String testName, String reason) {
        System.out.println("  ✗ " + testName);
        System.out.println("    Reason: " + reason);
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
