package org.example.parser;

import java.nio.file.Path;

/**
 * Фабрика парсеров
 *
 * @author Dmitriy Gromov
 * @since 02.07.2026
 */
public record ParserFactory(Path path) {

    private static final String POINT_DELIMITER = ".";
    private static final String FILE_EXTENSION_TXT = ".txt";

    /**
     * Возвращает парсер, соответствующий формату файла.
     *
     * <p>Правила выбора:
     * <ul>
     *   <li>Файлы с расширением {@code .txt} — {@link PipeDelimitedParser}</li>
     *   <li>Файлы без расширения (без точки в имени) — {@link HashDelimitedParser}</li>
     * </ul>
     *
     * <p>Проверка расширения выполняется без учёта регистра.
     *
     * @return парсер для чтения файла
     * @throws RuntimeException если формат файла не поддерживается
     */
    public OrderReader getParser() {

        String fileName = path.getFileName().toString().toLowerCase();

        if (fileName.endsWith(FILE_EXTENSION_TXT)) {
            return new PipeDelimitedParser();
        }

        if (!fileName.contains(POINT_DELIMITER)) {
            return new HashDelimitedParser();
        }

        throw new RuntimeException("Неизвестный формат файла: " + fileName);
    }
}
