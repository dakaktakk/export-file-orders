package org.example.storage;

import org.example.entity.Order;
import org.example.parser.OrderReader;
import org.example.parser.ParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Чтение счетов из файла
 *
 * @author Dmitriy Gromov
 * @since 02.07.2026
 */
public class OrderFileReader {
    private final ParserFactory parserFactory;

    public OrderFileReader(ParserFactory parserFactory) {
        this.parserFactory = parserFactory;
    }

    public List<Order> read() {
        OrderReader orderReader = parserFactory.getParser();

        try (BufferedReader bufferedReader = Files.newBufferedReader(parserFactory.path())) {
            return new ArrayList<>(orderReader.read(bufferedReader));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при открытии для чтения файлов", e);
        }
    }
}