import java.util.List;

public class TransactionProcessor {

    public void processTransactions(List<Transaction> transactions) {

        double totalAmount = 0;

        for (Transaction transaction : transactions) {
            totalAmount += transaction.getAmount();
        }

        System.out.println("Transactions:");

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }

        System.out.println("Total amount: " + totalAmount);
    }
}