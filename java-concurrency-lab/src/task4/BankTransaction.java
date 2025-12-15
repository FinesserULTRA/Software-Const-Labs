package task4;

/**
 * Lab 14 - Task 4: Simulation of Bank Transaction System
 * 
 * This program simulates a bank account with multiple clients (threads)
 * performing concurrent deposits and withdrawals.
 * 
 * Thread safety is ensured using:
 * - AtomicInteger for the balance
 * - Synchronized methods for transaction logging
 */
public class BankTransaction {

    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000); // Initial balance: $1000

        System.out.println("=== Bank Transaction System Simulation ===");
        System.out.println("Initial Balance: $" + account.getBalance());
        System.out.println("Starting 5 client threads...\n");

        // Create multiple client threads
        Thread client1 = new Thread(new BankClient(account, "Alice"), "Client-Alice");
        Thread client2 = new Thread(new BankClient(account, "Bob"), "Client-Bob");
        Thread client3 = new Thread(new BankClient(account, "Charlie"), "Client-Charlie");
        Thread client4 = new Thread(new BankClient(account, "Diana"), "Client-Diana");
        Thread client5 = new Thread(new BankClient(account, "Eve"), "Client-Eve");

        // Start all client threads
        client1.start();
        client2.start();
        client3.start();
        client4.start();
        client5.start();

        // Wait for all clients to complete their transactions
        try {
            client1.join();
            client2.join();
            client3.join();
            client4.join();
            client5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n=== All Transactions Completed ===");
        System.out.println("Final Balance: $" + account.getBalance());
        System.out.println("Total Deposits: $" + account.getTotalDeposits());
        System.out.println("Total Withdrawals: $" + account.getTotalWithdrawals());
        System.out.println("Expected Final Balance: $" +
                (1000 + account.getTotalDeposits() - account.getTotalWithdrawals()));
    }
}
