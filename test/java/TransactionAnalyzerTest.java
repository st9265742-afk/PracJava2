import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;

class TransactionAnalyzerTest {

    @Test
    public void testCalculateTotalBalance() {

        Transaction transaction1 =
                new Transaction("2023-01-01", 100.0, "Дохід");

        Transaction transaction2 =
                new Transaction("2023-01-02", -50.0, "Витрата");

        Transaction transaction3 =
                new Transaction("2023-01-03", 150.0, "Дохід");

        List<Transaction> transactions =
                Arrays.asList(transaction1, transaction2, transaction3);

        TransactionAnalyzer analyzer =
                new TransactionAnalyzer(transactions);

        double result = analyzer.calculateTotalBalance();

        Assertions.assertEquals(200.0, result, 0.001);
    }

    @Test
    public void testCountTransactionsByMonth() {

        Transaction transaction1 =
                new Transaction("01-02-2023", 50.0, "Дохід");

        Transaction transaction2 =
                new Transaction("15-02-2023", -20.0, "Витрата");

        Transaction transaction3 =
                new Transaction("05-03-2023", 100.0, "Дохід");

        List<Transaction> transactions =
                Arrays.asList(transaction1, transaction2, transaction3);

        TransactionAnalyzer analyzer =
                new TransactionAnalyzer(transactions);

        Assertions.assertEquals(
                2, analyzer.countTransactionsByMonth("02-2023"));

        Assertions.assertEquals(
                1, analyzer.countTransactionsByMonth("03-2023"));
    }

    // Тест читання CSV
    @Test
    public void testReadCSV() throws Exception {

        Path csvFile = Files.createTempFile("transactions", ".csv");

        try {
            Files.writeString(csvFile,
                    "2023-01-01,-1000,Продукти\n" +
                    "2023-01-02,-500,Транспорт\n" +
                    "2023-01-03,2000,Дохід\n");

            TransactionCSVReader reader =
                    new TransactionCSVReader();

            List<Transaction> transactions =
                    reader.readTransactions(csvFile.toUri().toString());

            Assertions.assertEquals(3, transactions.size());

            Assertions.assertEquals(
                    "Продукти",
                    transactions.get(0).getDescription());

            Assertions.assertEquals(
                    -1000.0,
                    transactions.get(0).getAmount(),
                    0.001
            );

        } finally {
            Files.deleteIfExists(csvFile);
        }
    }

    // Тест пошуку 10 найбільших витрат
    @Test
    public void testFindTopExpenses() {

        List<Transaction> transactions =
                new java.util.ArrayList<>();

        for (int i = 1; i <= 15; i++) {

            transactions.add(
                    new Transaction(
                            "2023-01-01",
                            -i * 1000.0,
                            "Витрата " + i
                    )
            );
        }

        // Додаємо дохід, який не повинен потрапити у витрати
        transactions.add(
                new Transaction("2023-01-02", 50000.0, "Дохід")
        );

        TransactionAnalyzer analyzer =
                new TransactionAnalyzer(transactions);

        List<Transaction> topExpenses =
                analyzer.findTopExpenses();

        Assertions.assertEquals(10, topExpenses.size());

        Assertions.assertEquals(
                -15000.0,
                topExpenses.get(0).getAmount(),
                0.001
        );

        Assertions.assertEquals(
                -6000.0,
                topExpenses.get(9).getAmount(),
                0.001
        );
    }

}