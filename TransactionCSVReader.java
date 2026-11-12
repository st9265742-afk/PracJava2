import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TransactionCSVReader implements TransactionReader {

    @Override
    public List<Transaction> readTransactions(String filePath) {

        List<Transaction> transactions = new ArrayList<>();

        try {
            URL url = new URL(filePath);

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            url.openStream(),
                            StandardCharsets.UTF_8))) {

                String line;

                while ((line = br.readLine()) != null) {

                    String[] values = line.split(",", 3);

                    if (values.length < 3) {
                        continue;
                    }

                    try {
                        Transaction transaction = new Transaction(
                                values[0].trim(),
                                Double.parseDouble(values[1].trim()),
                                values[2].trim()
                        );

                        transactions.add(transaction);

                    } catch (NumberFormatException e) {
                        // Пропуск заголовка або некоректного рядка
                        continue;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return transactions;
    }
}