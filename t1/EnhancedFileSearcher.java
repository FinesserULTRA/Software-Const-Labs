import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Task 1: Enhanced Recursive File Search (Mandatory Enhancements)
 * 1. Search for multiple files.
 * 2. Count occurrences of a specific file.
 * 3. Case-sensitive/insensitive option.
 */
public class EnhancedFileSearcher {

    // Map to store results: Key = File Name, Value = List of full paths (Enhancement 1)
    private final Map<String, List<String>> foundFiles = new HashMap<>();
    // List of file names to search for
    private final List<String> targetFileNames;
    // Flag for case sensitivity
    private final boolean isCaseSensitive; // Enhancement 3
    // Counter for a specific target file's occurrences (for Enhancement 2)
    private final String countTarget;
    private int count = 0;

    /**
     * Constructor for FileSearcher.
     * 
     * @param targetFileNames The list of file names to search for.
     * @param isCaseSensitive True for case-sensitive search, false otherwise.
     * @param countTarget     The specific file name to count occurrences of (can be
     *                        null).
     */
    public EnhancedFileSearcher(List<String> targetFileNames, boolean isCaseSensitive, String countTarget) {
        if (targetFileNames == null || targetFileNames.isEmpty()) {
            throw new IllegalArgumentException("Target file names cannot be empty.");
        }
        this.targetFileNames = targetFileNames;
        this.isCaseSensitive = isCaseSensitive;
        // Count target can be null
        this.countTarget = countTarget;
    }

    /**
     * Starts the recursive search.
     * 
     * @param startDirPath The initial directory path.
     * @return A map of found files (name -> list of paths).
     * @throws IllegalArgumentException if the start directory is invalid.
     */
    public Map<String, List<String>> search(String startDirPath) {
        File startDir = new File(startDirPath);

        if (!startDir.exists() || !startDir.isDirectory()) {
            throw new IllegalArgumentException("Invalid or non-existent directory path: " + startDirPath);
        }

        System.out.println("Starting search in: " + startDir.getAbsolutePath());
        recursiveSearch(startDir);

        System.out.println("\n--- Search Results ---");
        if (foundFiles.isEmpty()) {
            System.out.println("No target files found.");
        } else {
            foundFiles.forEach((fileName, paths) -> {
                System.out.printf("File '%s' found %d time(s). Full paths:\n", fileName, paths.size());
                paths.forEach(path -> System.out.println("  - " + path));
            });
        }

        // Enhancement 2 output
        if (countTarget != null) {
            System.out.printf("\nSpecific count for '%s': %d time(s).\n", countTarget, count);
        }

        return foundFiles;
    }

    /**
     * The recursive search function.
     * 
     * @param currentFile The current file or directory being examined.
     */
    private void recursiveSearch(File currentFile) {
        // Error Handling: Check for null or non-existent files (though unlikely with
        // File.listFiles())
        if (currentFile == null)
            return;

        // Base Case Check: If it's a file, check if it's one of the targets
        if (currentFile.isFile()) {
            String fileName = currentFile.getName();
            boolean foundMatch = false;

            for (String target : targetFileNames) {
                boolean match;

                // Case Sensitivity Check (Enhancement 3)
                if (isCaseSensitive) {
                    match = fileName.equals(target);
                } else {
                    match = fileName.equalsIgnoreCase(target);
                }

                if (match) {
                    foundMatch = true;
                    // Add the path to the results map (Enhancement 1)
                    foundFiles.computeIfAbsent(fileName, k -> new ArrayList<>()).add(currentFile.getAbsolutePath());
                    // Enhancement 2: Count the specific target file
                    if (countTarget != null && target.equalsIgnoreCase(countTarget)) {
                        count++;
                    }
                    // Since we found a match for one of the targets, we can break and continue to
                    // the next file/dir
                    break;
                }
            }

            if (foundMatch) {
                System.out.println("✅ Found: " + currentFile.getAbsolutePath());
            }

        } else if (currentFile.isDirectory()) {
            // Recursive Step: If it's a directory, list its contents and call search on
            // each
            File[] files = currentFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    try {
                        recursiveSearch(file);
                    } catch (StackOverflowError e) {
                        // Handle extremely deep recursion (very rare but possible with malicious
                        // structures)
                        System.err.println("Recursion depth limit reached at: " + file.getAbsolutePath());
                    } catch (Exception e) {
                        // General IO/Security Exception handling
                        System.err.println("Error processing " + file.getAbsolutePath() + ": " + e.getMessage());
                    }
                }
            } else {
                // Handle directories with restricted access (files is null)
                System.err.println("❌ Cannot access directory: " + currentFile.getAbsolutePath());
            }
        }
    }

    /**
     * Main method to demonstrate usage.
     * Expects: <startDir> <caseSensitive:true|false> <countTarget:null|filename>
     * <targetFile1> [targetFile2] ...
     * Example: java FileSearcher /path/to/start/dir true README.md README.md
     * LICENSE.txt
     */
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println(
                    "Usage: java FileSearcher <startDir> <caseSensitive:true|false> <countTarget:null|filename> <targetFile1> [targetFile2] ...");
            return;
        }

        String startDir = args[0];
        boolean isCaseSensitive = Boolean.parseBoolean(args[1]);
        String countTarget = args[2].equalsIgnoreCase("null") ? null : args[2];
        List<String> targetFiles = new ArrayList<>();
        for (int i = 3; i < args.length; i++) {
            targetFiles.add(args[i]);
        }

        try {
            EnhancedFileSearcher searcher = new EnhancedFileSearcher(targetFiles, isCaseSensitive, countTarget);
            searcher.search(startDir);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}