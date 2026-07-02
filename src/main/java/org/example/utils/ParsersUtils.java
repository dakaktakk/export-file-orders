package org.example.utils;

import org.example.interfaces.OrderReader;
import org.example.parser.HashDelimitedParser;
import org.example.parser.PipeDelimitedParser;

import java.nio.file.Path;

import static org.example.constants.Constants.FILE_EXTENSION_TXT;
import static org.example.constants.Constants.POINT_DELIMITER;


/**
 * Фабрика парсеров
 * @author Dmitriy Gromov
 * @since 02.07.2026
 */
public class ParsersUtils {

    private final Path path;

    public ParsersUtils (Path path) {
        this.path = path;
    }

    public Path getPath() {
        return this.path;
    }

    public OrderReader GetParser() {

        String fileName = path.getFileName().toString().toLowerCase();

        if(fileName.endsWith(FILE_EXTENSION_TXT)) {
            return  new PipeDelimitedParser();
        }

        if(!fileName.contains(POINT_DELIMITER)) {
            return new HashDelimitedParser();
        }

        throw new RuntimeException("Неизвестный формат файла: " + fileName);
    }
}
