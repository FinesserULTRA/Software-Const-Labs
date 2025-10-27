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

    // Map: Key = File Name, Value = List of full paths (Enhancement 1)
    private final Map<String, List<String>> foundFiles = new HashMap<>();
    private final List<String> targetFileNames;
    private final boolean isCaseSensitive; // Enhancement 3
    private final String countTarget;
    private int count = 0; // Enhancement 2

    public EnhancedFileSearcher(List<String> targetFileNames, boolean isCaseSensitive, String countTarget) {
        if (targetFileNames == null || targetFileNames.isEmpty()) {
            throw new IllegalArgumentException("Target file names cannot be empty.");
        }
        this.targetFileNames = targetFileNames;
        this.isCaseSensitive = isCaseSensitive;
        // Count target can be null
        this.countTarget = countTarget;
    }

    public Map<String, List<String>> search(String startDirPath) {
        File startDir = new File(startDirPath);

        if (!startDir.exists() || !startDir.isDirectory()) {
            throw new IllegalArgumentException("Invalid or non-existent directory path: " + startDirPath);
        }

        System.out.println("Starting search in: " + startDir.getAbsolutePath());
        recursiveSearch(startDir);

        // Summary Output
        System.out.println("\n--- Search Results ---");
        if (foundFiles.isEmpty()) {
            System.out.println("No target files found.");
        } else {
            foundFiles.forEach((fileName, paths) -> {
                System.out.printf("File '%s' found %d time(s). Full paths:\n", fileName, paths.size());
            });
        }

        if (countTarget != null) {
            System.out.printf("\nSpecific count for '%s': %d time(s).\n", countTarget, count);
        }

        return foundFiles;
    }

    private void recursiveSearch(File currentFile) {
        if (currentFile == null)
            return;

        if (currentFile.isFile()) {
            String fileName = currentFile.getName();

            for (String target : targetFileNames) {
                boolean match;

                // Case Sensitivity Check (Enhancement 3)
                if (isCaseSensitive) {
                    match = fileName.equals(target);
                } else {
                    // Normalize both for comparison (Ensures case-insensitivity)
                    match = fileName.equalsIgnoreCase(target);
                }

                if (match) {
                    // Store full path (Enhancement 1)
                    foundFiles.computeIfAbsent(fileName, k -> new ArrayList<>()).add(currentFile.getAbsolutePath());

                    // Count specific file (Enhancement 2)
                    if (countTarget != null) {
                        // Check if the found file matches the count target (using the defined case
                        // sensitivity)
                        if ((isCaseSensitive && fileName.equals(countTarget)) ||
                                (!isCaseSensitive && fileName.equalsIgnoreCase(countTarget))) {
                            count++;
                        }
                    }
                    // Since we found a match for one of the targets, move to the next file/dir
                    break;
                }
            }

        } else if (currentFile.isDirectory()) {
            File[] files = currentFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    recursiveSearch(file);
                }
            } else {
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
            FileSearcherEnh searcher = new FileSearcherEnh(targetFiles, isCaseSensitive, countTarget);
            searcher.search(startDir);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}