package org.example.storage;

import org.example.entity.Order;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.math.RoundingMode.HALF_UP;

/**
 * Запись счетов в файл
 *
 * @author Dmitriy Gromov
 * @since 02.07.2026
 */
public class OrderFileWriter {

    public static final int SCALE = 2;

    public void write(List<Order> orders, Path outputPath) {
        List<String> lines = orders.stream()
                .map(order -> String.format("%s - %s",
                        order.getCompanyName(),
                        order.getOrderDiscountPrice().setScale(SCALE, HALF_UP)))
                .toList();

        try {
            Files.write(outputPath, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи в файл", e);
        }
    }
}
