package task4;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread-safe bank account using AtomicInteger for balance management
 */
public class BankAccount {
    private final AtomicInteger balance;
    private final AtomicInteger totalDeposits = new AtomicInteger(0);
    private final AtomicInteger totalWithdrawals = new AtomicInteger(0);

    public BankAccount(int initialBalance) {
        this.balance = new AtomicInteger(initialBalance);
    }

    /**
     * Thread-safe deposit operation
     */
    public void deposit(String clientName, int amount) {
        int newBalance = balance.addAndGet(amount);
        totalDeposits.addAndGet(amount);
        logTransaction(clientName, "DEPOSIT", amount, newBalance);
    }

    /**
     * Thread-safe withdrawal operation with balance check
     */
    public synchronized boolean withdraw(String clientName, int amount) {
        if (balance.get() >= amount) {
            int newBalance = balance.addAndGet(-amount);
            totalWithdrawals.addAndGet(amount);
            logTransaction(clientName, "WITHDRAW", amount, newBalance);
            return true;
        } else {
            System.out.println("[" + clientName + "] WITHDRAW FAILED: Insufficient funds for $" + amount);
            return false;
        }
    }

    /**
     * Synchronized logging to prevent interleaved output
     */
    private synchronized void logTransaction(String client, String type, int amount, int balance) {
        System.out.printf("[%s] %s: $%d | Balance: $%d%n", client, type, amount, balance);
    }

    public int getBalance() {
        return balance.get();
    }

    public int getTotalDeposits() {
        return totalDeposits.get();
    }

    public int getTotalWithdrawals() {
        return totalWithdrawals.get();
    }
}
