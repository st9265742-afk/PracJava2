import java.util.List;

public interface TransactionReader {
    List<Transaction> readTransactions(String source);
}