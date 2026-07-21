package org.example.reader;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesReader {

    public static final Path READ_FILE_PATH;
    public static final Path WRITE_FILE_PATH;
    public static final BigDecimal CURRENT_DISCOUNT;
    public static final BigDecimal DISCOUNT_DECREASE_STEP;
    public static final BigDecimal WEIGHT_UNI_KG;
    public static final BigDecimal PRICE_PER_50_KG;

    static {
        Properties props = new Properties();
        String fileName = "app.properties";

        try (InputStream input = PropertiesReader.class.getClassLoader()
                .getResourceAsStream("props/" + fileName)) {

            if (input == null) {
                throw new ExceptionInInitializerError("Файл конфигурации props/" + fileName + " не найден!");
            }
            props.load(input);

            READ_FILE_PATH = Paths.get(props.getProperty("file.read.order.path"));
            WRITE_FILE_PATH = Paths.get(props.getProperty("file.write.order.path"));
            CURRENT_DISCOUNT = new BigDecimal(props.getProperty("discount.initial"));
            DISCOUNT_DECREASE_STEP = new BigDecimal(props.getProperty("discount.step"));
            WEIGHT_UNI_KG = new BigDecimal(props.getProperty("weight.uni.kg"));
            PRICE_PER_50_KG = new BigDecimal(props.getProperty("price.per.50kg"));

        } catch (IOException e) {
            throw new ExceptionInInitializerError();
        }
    }
}
