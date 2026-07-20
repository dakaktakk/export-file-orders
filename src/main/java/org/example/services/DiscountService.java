package org.example.services;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.example.reader.PropertiesReader.CURRENT_DISCOUNT;
import static org.example.reader.PropertiesReader.DISCOUNT_DECREASE_STEP;
import static org.example.reader.PropertiesReader.PRICE_PER_50_KG;
import static org.example.reader.PropertiesReader.WEIGHT_UNI_KG;

/**
 * Расчёт счидки счёта
 *
 * @author Dmitriy Gromov
 * @since 02.07.2026
 */
public class DiscountService {

    private static final int SCALE = 2;
    private static final BigDecimal MIN_DISCOUNT = new BigDecimal("0.0");
    private BigDecimal currentDiscount = CURRENT_DISCOUNT;

    public BigDecimal calculateDiscountedPrice(BigDecimal weightKg) {

        BigDecimal pricePerKg = PRICE_PER_50_KG
                .divide(WEIGHT_UNI_KG, SCALE, RoundingMode.HALF_UP);

        BigDecimal basePrice = pricePerKg
                .multiply(weightKg);

        BigDecimal discountFraction = currentDiscount
                .divide(BigDecimal.valueOf(100), SCALE, RoundingMode.HALF_UP);

        BigDecimal discountMultiplier = BigDecimal.ONE.subtract(discountFraction);

        BigDecimal finalPrice = basePrice
                .multiply(discountMultiplier)
                .setScale(SCALE, RoundingMode.HALF_UP);

        decreaseDiscount();

        return finalPrice;
    }

    public void reset() {
        this.currentDiscount = CURRENT_DISCOUNT;
    }

    private void decreaseDiscount() {
        BigDecimal newDiscount = currentDiscount.subtract(DISCOUNT_DECREASE_STEP);
        this.currentDiscount = newDiscount.max(MIN_DISCOUNT);
    }
}

