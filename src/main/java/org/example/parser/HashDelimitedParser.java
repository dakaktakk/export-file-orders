package org.example.parser;

import org.example.entity.Order;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Парсер файла типа txt в счета разделитель "|"
 *
 * @author Dmitriy Gromov
 * @since 02.07.2026
 */
public class HashDelimitedParser implements OrderReader {

    private static final String HASH_DELIMITER = "#";
    private static final int CORRECT_AMOUNT_PARTS = 3;

    @Override
    public List<Order> read(BufferedReader bufferedReader) {

        String line;
        List<Order> orders = new ArrayList<>();

        try {
            while ((line = bufferedReader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(HASH_DELIMITER);

                if (parts.length != CORRECT_AMOUNT_PARTS) {
                    throw new IllegalArgumentException("Неверный формат строки: " + line);
                }

                try {
                    orders.add(new Order(LocalDateTime.parse(parts[0]), parts[1], Integer.parseInt(parts[2])));
                } catch (Exception e) {
                    throw new IOException("Ошибка парсинга данных в строке: " + line, e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла", e);
        }

        return orders;
    }
}
