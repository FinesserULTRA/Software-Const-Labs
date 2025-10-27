import java.io.File;
import java.util.Optional;

/**
 * Task 1: Basic Recursive File Search (Normal Code)
 * Searches for a single file name recursively.
 */
public class BasicFileSearcher {

    private final String targetFileName;
    private Optional<String> foundPath = Optional.empty();

    public BasicFileSearcher(String targetFileName) {
        this.targetFileName = targetFileName;
    }

    /**
     * Starts the recursive search.
     * @param startDirPath The initial directory path.
     */
    public void search(String startDirPath) {
        File startDir = new File(startDirPath);

        // 5. Implement error handling for invalid directory
        if (!startDir.exists() || !startDir.isDirectory()) {
            System.err.println("Error: Invalid or non-existent directory path: " + startDirPath);
            return;
        }

        System.out.println("Starting search for '" + targetFileName + "' in: " + startDir.getAbsolutePath());
        recursiveSearch(startDir);

        // 3. Display search result message
        if (foundPath.isPresent()) {
            System.out.println("\n✅ File Found: " + foundPath.get());
        } else {
            System.out.println("\n❌ File Not Found: The file '" + targetFileName + "' was not found.");
        }
    }

    /**
     * 2. The recursive search function.
     * @param currentFile The current file or directory being examined.
     */
    private void recursiveSearch(File currentFile) {
        // Stop the search if the file has already been found
        if (foundPath.isPresent()) {
            return;
        }

        // Base Case Check: If it's a file, check for a match
        if (currentFile.isFile()) {
            // Check if the file name matches the target
            if (currentFile.getName().equals(targetFileName)) {
                foundPath = Optional.of(currentFile.getAbsolutePath());
                // The search will stop in subsequent recursive calls due to the check at the start.
            }
        } else if (currentFile.isDirectory()) {
            // Recursive Step: If it's a directory, list its contents
            File[] files = currentFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    // Recursively call the search function on the sub-file/directory
                    recursiveSearch(file);
                    // Optimization: check again to stop traversal immediately
                    if (foundPath.isPresent()) {
                        return;
                    }
                }
            } else {
                // 5. Error handling for inaccessible directory
                System.err.println("Warning: Cannot access directory: " + currentFile.getAbsolutePath());
            }
        }
    }

    // Main method for command-line execution (1. Takes command-line arguments)
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java BasicFileSearcher <directoryPath> <fileName>");
            return;
        }

        String startDir = args[0];
        String fileName = args[1];

        BasicFileSearcher searcher = new BasicFileSearcher(fileName);
        searcher.search(startDir);
    }
}