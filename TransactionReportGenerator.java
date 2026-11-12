import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TransactionReportGenerator {

    private static final int AMOUNT_PER_STAR = 1000;

    public void printBalanceReport(double totalBalance) {
        System.out.println("Загальний баланс: " + totalBalance + " грн");
    }

    public void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("Кількість транзакцій за "
                + monthYear + ": " + count);
    }

    // Звіт про 10 найбільших витрат
    public void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("\n10 найбільших витрат:");

        for (Transaction expense : topExpenses) {
            System.out.println(expense.getDescription()
                    + ": " + Math.abs(expense.getAmount()) + " грн");
        }
    }

    // Звіт про найбільшу та найменшу витрати
    public void printExtremeExpensesReport(
            Transaction largest,
            Transaction smallest) {

        System.out.println("\nВитрати за вказаний період:");

        if (largest != null) {
            System.out.println("Найбільша витрата: "
                    + largest.getDescription() + " - "
                    + Math.abs(largest.getAmount()) + " грн");
        } else {
            System.out.println("Найбільших витрат не знайдено.");
        }

        if (smallest != null) {
            System.out.println("Найменша витрата: "
                    + smallest.getDescription() + " - "
                    + Math.abs(smallest.getAmount()) + " грн");
        } else {
            System.out.println("Найменших витрат не знайдено.");
        }
    }

    // Створення зірочок для діаграми
    private String createBar(double amount) {
        int stars = (int) (Math.abs(amount) / AMOUNT_PER_STAR);

        return "*".repeat(stars);
    }

    // Звіт про витрати за категоріями
    public void printExpensesByCategory(List<Transaction> transactions) {

        Map<String, Double> categories = new TreeMap<>();

        for (Transaction transaction : transactions) {

            if (transaction.getAmount() < 0) {

                String category = transaction.getDescription();

                categories.put(
                        category,
                        categories.getOrDefault(category, 0.0)
                                + Math.abs(transaction.getAmount())
                );
            }
        }

        System.out.println("\nВитрати за категоріями:");

        for (Map.Entry<String, Double> entry : categories.entrySet()) {

            System.out.println(
                    entry.getKey() + ": "
                            + entry.getValue() + " грн "
                            + createBar(entry.getValue())
            );
        }
    }

    // Звіт про витрати за місяцями
    public void printExpensesByMonth(List<Transaction> transactions) {

        Map<String, Double> months = new TreeMap<>();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MM-yyyy");

        for (Transaction transaction : transactions) {

            if (transaction.getAmount() < 0) {

                LocalDate date = parseDate(transaction.getDate());

                String month = date.format(formatter);

                months.put(
                        month,
                        months.getOrDefault(month, 0.0)
                                + Math.abs(transaction.getAmount())
                );
            }
        }

        System.out.println("\nВитрати за місяцями:");

        for (Map.Entry<String, Double> entry : months.entrySet()) {

            System.out.println(
                    entry.getKey() + ": "
                            + entry.getValue() + " грн "
                            + createBar(entry.getValue())
            );
        }
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (Exception e) {
            return LocalDate.parse(
                    date,
                    DateTimeFormatter.ofPattern("dd-MM-yyyy")
            );
        }
    }
}
