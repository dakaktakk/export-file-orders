package org.example.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountServiceTest {

    private DiscountService discountService;

    @BeforeEach
    void setUp() {
        discountService = new DiscountService();
    }

    @Test
    @DisplayName("Первый вызов: расчет цены с начальной скидкой")
    void calculateDiscountedPrice_firstCall_appliesInitialDiscount() {

        BigDecimal weight = new BigDecimal("100.0");
        BigDecimal result = discountService.calculateDiscountedPrice(weight);
        assertEquals(new BigDecimal("500.00"), result);
    }

    @Test
    @DisplayName("Второй вызов: скидка должна уменьшиться на шаг 5%")
    void calculateDiscountedPrice_secondCall_discountDecreases() {

        BigDecimal weight = new BigDecimal("100.0");
        discountService.calculateDiscountedPrice(weight);
        BigDecimal result = discountService.calculateDiscountedPrice(weight);
        assertEquals(new BigDecimal("550.00"), result);

    }

    @Test
    @DisplayName("Скидка не должна опускаться ниже 0")
    void calculateDiscountedPrice_discountDoesNotGoBelowZero() {

        BigDecimal weight = new BigDecimal("100.0");
        for (int i = 0; i < 10; i++) {
            discountService.calculateDiscountedPrice(weight);
        }
        BigDecimal result = discountService.calculateDiscountedPrice(weight);
        assertEquals(new BigDecimal("1000.00"), result);

    }

    @Test
    @DisplayName("Метод reset() должен возвращать скидку к начальному значению")
    void reset_restoresInitialDiscount() {

        BigDecimal weight = new BigDecimal("100.0");
        discountService.calculateDiscountedPrice(weight);
        discountService.reset();
        BigDecimal result = discountService.calculateDiscountedPrice(weight);
        assertEquals(new BigDecimal("500.00"), result);

    }

    @Test
    @DisplayName("Граничный случай: вес 0 кг")
    void calculateDiscountedPrice_zeroWeight_returnsZero() {

        BigDecimal weight = BigDecimal.ZERO;
        BigDecimal result = discountService.calculateDiscountedPrice(weight);
        assertEquals(new BigDecimal("0.00"), result);
    }
}