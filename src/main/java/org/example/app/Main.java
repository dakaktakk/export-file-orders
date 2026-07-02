package org.example.app;

import org.example.reader.PropertiesReader;
import org.example.storage.OrderFileReader;
import org.example.storage.OrderFileWriter;
import org.example.services.OrderProcessor;
import org.example.parser.ParserFactory;
import org.example.services.DiscountService;

import java.nio.file.Path;
import java.nio.file.Paths;


public class Main {

    public static void main(String[] args) {

        PropertiesReader props = new PropertiesReader("app.properties");
        Path readFilePath = Paths.get(props.getString("file.read.order.path"));
        Path writeFilePath = Paths.get(props.getString("file.write.order.path"));
        ParserFactory parserFactory = new ParserFactory(readFilePath);
        OrderFileReader reader = new OrderFileReader(parserFactory);
        OrderFileWriter writer = new OrderFileWriter();
        DiscountService discountService = new DiscountService(props);

        OrderProcessor processor = new OrderProcessor(reader, writer, discountService);

        processor.process(writeFilePath);
    }
}