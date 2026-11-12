import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionAnalyzer {

    private List<Transaction> transactions;

    public TransactionAnalyzer(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    // Перетворення дати у LocalDate
    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date,
                    DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception e) {
            return LocalDate.parse(date,
                    DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }
    }

    // Розрахунок загального балансу
    public double calculateTotalBalance() {
        double balance = 0;

        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }

        return balance;
    }

    // Підрахунок транзакцій за місяць
    public int countTransactionsByMonth(String monthYear) {
        int count = 0;

        for (Transaction transaction : transactions) {
            LocalDate date = parseDate(transaction.getDate());

            String transactionMonthYear =
                    date.format(DateTimeFormatter.ofPattern("MM-yyyy"));

            if (transactionMonthYear.equals(monthYear)) {
                count++;
            }
        }

        return count;
    }

    // Пошук 10 найбільших витрат
    public List<Transaction> findTopExpenses() {
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparing(Transaction::getAmount))
                .limit(10)
                .collect(Collectors.toList());
    }

    // Пошук найбільшої витрати за період
    public Transaction findLargestExpense(String startDate, String endDate) {

        LocalDate start = parseDate(startDate);
        LocalDate end = parseDate(endDate);

        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> {
                    LocalDate date = parseDate(t.getDate());
                    return !date.isBefore(start) && !date.isAfter(end);
                })
                .min(Comparator.comparing(Transaction::getAmount))
                .orElse(null);
    }

    // Пошук найменшої витрати за період
    public Transaction findSmallestExpense(String startDate, String endDate) {

        LocalDate start = parseDate(startDate);
        LocalDate end = parseDate(endDate);

        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .filter(t -> {
                    LocalDate date = parseDate(t.getDate());
                    return !date.isBefore(start) && !date.isAfter(end);
                })
                .max(Comparator.comparing(Transaction::getAmount))
                .orElse(null);
    }
}