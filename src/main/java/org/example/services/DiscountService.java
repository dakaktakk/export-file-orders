package org.example.services;

import org.example.reader.PropertiesReader;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Расчёт счидки счёта
 *
 * @author Dmitriy Gromov
 * @since 02.07.2026
 */
public class DiscountService {

    private static final int SCALE = 2;
    private static final BigDecimal MIN_DISCOUNT = new BigDecimal("0.0");
    private final PropertiesReader props;
    private BigDecimal currentDiscount;
    private final BigDecimal discountDecreaseStep;

    public DiscountService(PropertiesReader props) {
        this.props = props;
        this.currentDiscount = props.getBigDecimal("discount.initial");
        this.discountDecreaseStep = props.getBigDecimal("discount.step");
    }

    public BigDecimal calculateDiscountedPrice(BigDecimal weightKg) {
        BigDecimal weightUniKg = props.getBigDecimal("weight.uni.kg");
        BigDecimal pricePer50Kg = props.getBigDecimal("price.per.50kg");

        BigDecimal pricePerKg = pricePer50Kg
                .divide(weightUniKg, SCALE, RoundingMode.HALF_UP);

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

    private void decreaseDiscount() {
        BigDecimal newDiscount = currentDiscount.subtract(discountDecreaseStep);
        this.currentDiscount = newDiscount.max(MIN_DISCOUNT);
    }

    public void reset() {
        this.currentDiscount = props.getBigDecimal("discount.initial");
    }
}
