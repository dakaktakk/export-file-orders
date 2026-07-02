package org.example.constants;

import java.math.BigDecimal;

/**
 * Константы приложения
 * @author Dmitriy Gromov
 * @since 02.07.2026
 */

public class Constants {

    public static final String WRITE_FILE_PATH = "src/main/resources/files/orders/discount/discount_orders.txt";
    public static final int WEIGHT_UNIT_KG = 50;
    public static final int PRICE_PER_UNIT = 500;
    public static final int SCALE = 2;
    public static final BigDecimal PRICE_PER_KG = BigDecimal.valueOf((double) PRICE_PER_UNIT / WEIGHT_UNIT_KG);
    public static final BigDecimal INITIAL_DISCOUNT_PERCENT = new BigDecimal("50.0");
    public static final BigDecimal DISCOUNT_DECREASE_STEP = new BigDecimal("5.0");
    public static final BigDecimal MIN_DISCOUNT = new BigDecimal("0.0");
    public static final int CORRECT_AMOUNT_PARTS = 3;
    public static final String FILE_EXTENSION_TXT = ".txt";
    public static final String PIPE_DELIMITER = "\\|";
    public static final String HASH_DELIMITER = "#";
    public static final String POINT_DELIMITER = ".";
}
