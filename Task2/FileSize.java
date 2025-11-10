import java.io.File;

/**
 * Basic recursive file size calculator
 */
public class FileSize {
    
    /**
     * Recursively calculates total size of all files in a directory
     * @param folder the directory to analyze
     * @return total size in bytes
     */
    public static long getDirectorySize(File folder) {
        if (!folder.exists()) {
            return 0;
        }
        
        if (folder.isFile()) {
            return folder.length();
        }
        
        long totalSize = 0;
        File[] files = folder.listFiles();
        
        if (files != null) {
            for (File file : files) {
                totalSize += getDirectorySize(file);
            }
        }
        
        return totalSize;
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
    
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java FileSize <directory_path>");
            System.out.println("\nExample test with current directory:");
            File currentDir = new File(".");
            long size = getDirectorySize(currentDir);
            System.out.println("Directory: " + currentDir.getAbsolutePath());
            System.out.println("Total size: " + formatSize(size) + " (" + size + " bytes)");
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
        
        long size = getDirectorySize(folder);
        System.out.println("Directory: " + folder.getAbsolutePath());
        System.out.println("Total size: " + formatSize(size) + " (" + size + " bytes)");
    }
}
