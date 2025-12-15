package task4;

import java.util.Random;

/**
 * Bank client that performs random deposits and withdrawals
 */
public class BankClient implements Runnable {
    private final BankAccount account;
    private final String clientName;
    private final Random random = new Random();

    public BankClient(BankAccount account, String clientName) {
        this.account = account;
        this.clientName = clientName;
    }

    @Override
    public void run() {
        // Each client performs 5 random transactions
        for (int i = 0; i < 5; i++) {
            int amount = random.nextInt(100) + 10; // Random amount between 10-109

            if (random.nextBoolean()) {
                // Deposit
                account.deposit(clientName, amount);
            } else {
                // Withdraw
                account.withdraw(clientName, amount);
            }

            try {
                Thread.sleep(random.nextInt(100) + 50); // Random delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
