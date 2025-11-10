import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Enhanced file size calculator with mutual recursion and file exclusion
 */
public class FileSizeEnhanced {
    
    private static Set<String> excludedExtensions = new HashSet<>();
    
    /**
     * Recursively calculates directory size with file type exclusion
     * @param folder the directory to analyze
     * @param excludeExtensions file extensions to exclude (e.g., ".tmp", ".log")
     * @return total size in bytes
     */
    public static long getDirectorySize(File folder, String... excludeExtensions) {
        excludedExtensions.clear();
        for (String ext : excludeExtensions) {
            excludedExtensions.add(ext.toLowerCase());
        }
        return traverseFolder(folder);
    }
    
    /**
     * Handles folder traversal (mutual recursion part 1)
     * @param folder the folder to traverse
     * @return total size in bytes
     */
    private static long traverseFolder(File folder) {
        if (!folder.exists()) {
            return 0;
        }
        
        if (folder.isFile()) {
            return processFile(folder);
        }
        
        long totalSize = 0;
        File[] files = folder.listFiles();
        
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    totalSize += traverseFolder(file);
                } else {
                    totalSize += processFile(file);
                }
            }
        }
        
        return totalSize;
    }
    
    /**
     * Processes individual files and calls traverseFolder for subdirectories (mutual recursion part 2)
     * @param file the file to process
     * @return file size or 0 if excluded
     */
    private static long processFile(File file) {
        if (!file.exists()) {
            return 0;
        }
        
        if (file.isDirectory()) {
            return traverseFolder(file);
        }
        
        String fileName = file.getName();
        for (String ext : excludedExtensions) {
            if (fileName.toLowerCase().endsWith(ext)) {
                return 0;
            }
        }
        
        return file.length();
    }
    
    /**
     * Formats bytes into human-readable format
     * @param bytes size in bytes
     * @return formatted string
     */
    public static String formatSize(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.2f KB", bytes / 1024.0);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", bytes / (1024.0 * 1024));
        } else {
            return String.format("%.2f GB", bytes / (1024.0 * 1024 * 1024));
        }
    }
    
    /**
     * Displays detailed statistics about directory
     * @param folder the directory to analyze
     * @param excludeExtensions extensions to exclude
     */
    public static void displayStatistics(File folder, String... excludeExtensions) {
        System.out.println("Directory: " + folder.getAbsolutePath());
        
        long totalSize = getDirectorySize(folder);
        System.out.println("Total size (no exclusions): " + formatSize(totalSize));
        
        if (excludeExtensions.length > 0) {
            long filteredSize = getDirectorySize(folder, excludeExtensions);
            System.out.println("Excluded extensions: " + String.join(", ", excludeExtensions));
            System.out.println("Filtered size: " + formatSize(filteredSize));
            System.out.println("Excluded data: " + formatSize(totalSize - filteredSize));
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Enhanced Recursive File Size Calculator");
        System.out.println("========================================\n");
        
        if (args.length == 0) {
            System.out.println("Testing with current directory:");
            File currentDir = new File(".");
            displayStatistics(currentDir, ".tmp", ".log", ".class");
            
            System.out.println("\nUsage: java FileSizeEnhanced <directory_path> [extensions_to_exclude...]");
            System.out.println("Example: java FileSizeEnhanced /home/user/project .tmp .log .class");
            return;
        }
        
        File folder = new File(args[0]);
        
        if (!folder.exists()) {
            System.out.println("Error: Directory does not exist");
            return;
        }
        
        if (!folder.isDirectory()) {
            System.out.println("Error: Path is not a directory");
            return;
        }
        
        String[] exclusions = new String[args.length - 1];
        System.arraycopy(args, 1, exclusions, 0, exclusions.length);
        
        displayStatistics(folder, exclusions);
    }
}
