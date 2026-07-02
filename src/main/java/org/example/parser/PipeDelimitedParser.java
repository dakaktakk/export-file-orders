package org.example.parser;

import org.example.entity.Order;
import org.example.interfaces.OrderReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.example.constants.Constants.CORRECT_AMOUNT_PARTS;
import static org.example.constants.Constants.PIPE_DELIMITER;

/**
 * Парсер файла без типа в счета разделитель "|"
 * @author Dmitriy Gromov
 * @since 02.07.2026
 */

public class PipeDelimitedParser implements OrderReader {

    @Override
    public List<Order> read(BufferedReader bufferedReader) {

        String line;
        List<Order> orders = new ArrayList<>();

        try {
            while ((line = bufferedReader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(PIPE_DELIMITER);

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
