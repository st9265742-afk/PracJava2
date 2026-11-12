import java.util.List;

public class Main {

    public static void main(String[] args) {

        String filePath =
                "https://informer.com.ua/dut/java/pr2.csv";

        // Читання транзакцій
        TransactionReader reader = new TransactionCSVReader();

        List<Transaction> transactions =
                reader.readTransactions(filePath);

        // Аналіз транзакцій
        TransactionAnalyzer analyzer =
                new TransactionAnalyzer(transactions);

        TransactionReportGenerator reportGenerator =
                new TransactionReportGenerator();

        // Загальний баланс
        double totalBalance = analyzer.calculateTotalBalance();

        reportGenerator.printBalanceReport(totalBalance);

        // 10 найбільших витрат
        List<Transaction> topExpenses =
                analyzer.findTopExpenses();

        reportGenerator.printTopExpensesReport(topExpenses);

        // Витрати за вказаний період
        String startDate = "01-01-2024";
        String endDate = "31-12-2024";

        Transaction largest =
                analyzer.findLargestExpense(startDate, endDate);

        Transaction smallest =
                analyzer.findSmallestExpense(startDate, endDate);

        reportGenerator.printExtremeExpensesReport(
                largest, smallest);

        // Кількість транзакцій за місяць
        String monthYear = "01-2024";

        int transactionsCount =
                analyzer.countTransactionsByMonth(monthYear);

        reportGenerator.printTransactionsCountByMonth(
                monthYear, transactionsCount);

        // Звіти за категоріями та місяцями
        reportGenerator.printExpensesByCategory(transactions);

        reportGenerator.printExpensesByMonth(transactions);
    }
}
