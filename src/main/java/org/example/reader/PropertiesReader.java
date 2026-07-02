package org.example.reader;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

public class PropertiesReader {

    private final Properties properties;

    public PropertiesReader(String fileName) {
        this.properties = new Properties();

        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("props/" + fileName)) {

            if (input == null) {
                throw new RuntimeException("Файл конфигурации props/" + fileName + " не найден!");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла конфигурации", e);
        }
    }

    public BigDecimal getBigDecimal(String key) {
        return new BigDecimal(properties.getProperty(key));
    }

    public String getString(String key) {
        return properties.getProperty(key);
    }
}
