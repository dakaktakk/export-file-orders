package org.example.app;

import org.example.parser.ParserFactory;
import org.example.services.DiscountService;
import org.example.services.OrderProcessor;
import org.example.storage.OrderFileReader;
import org.example.storage.OrderFileWriter;

import static org.example.reader.PropertiesReader.READ_FILE_PATH;
import static org.example.reader.PropertiesReader.WRITE_FILE_PATH;


public class Main {

    public static void main(String[] args) {

        ParserFactory parserFactory = new ParserFactory(READ_FILE_PATH);
        OrderFileReader reader = new OrderFileReader(parserFactory);
        OrderFileWriter writer = new OrderFileWriter();
        DiscountService discountService = new DiscountService();
        OrderProcessor processor = new OrderProcessor(reader, writer, discountService);
        processor.process(WRITE_FILE_PATH);
    }
}