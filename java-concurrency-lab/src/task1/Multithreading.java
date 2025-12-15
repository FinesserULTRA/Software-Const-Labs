package task1;

/**
 * Lab 14 - Task 1: Introduction to Multithreading
 * 
 * This program demonstrates basic multithreading by creating two threads:
 * - Thread 1: Prints numbers from 1 to 10
 * - Thread 2: Prints squares of numbers from 1 to 10
 * 
 * Both threads run concurrently, demonstrating interleaved execution.
 */
public class Multithreading {

    public static void main(String[] args) {
        // Create threads using Runnable interface
        Thread numberThread = new Thread(new NumberPrinter(), "NumberThread");
        Thread squareThread = new Thread(new SquarePrinter(), "SquareThread");

        System.out.println("Starting both threads concurrently...\n");

        // Start both threads
        numberThread.start();
        squareThread.start();

        // Wait for both threads to complete
        try {
            numberThread.join();
            squareThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nBoth threads have completed execution.");
    }
}
