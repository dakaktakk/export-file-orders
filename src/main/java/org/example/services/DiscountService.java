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

    /**
     * Рассчитывает итоговую стоимость заказа с учётом текущей скидки.
     *
     * <p>Формула расчёта:
     * <ol>
     *   <li>Базовая цена = (цена за 50 кг / 50) × вес заказа</li>
     *   <li>Итоговая цена = базовая цена × (1 - текущая скидка / 100)</li>
     * </ol>
     *
     * <p><b>Побочный эффект:</b> после расчёта текущая скидка уменьшается на
     * {@code DISCOUNT_DECREASE_STEP} процентных пунктов, но не ниже {@code 0.0}.
     *
     * @param weightKg вес заказа в килограммах; должен быть неотрицательным
     * @return итоговая стоимость с округлением до {@value #SCALE} знаков после запятой
     */
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

    /**
     * Сбрасывает текущую скидку до начального значения {@code CURRENT_DISCOUNT}.
     *
     * <p>Должен вызываться перед началом обработки новой партии заказов,
     * чтобы гарантировать корректные расчёты.
     */
    public void reset() {
        this.currentDiscount = CURRENT_DISCOUNT;
    }

    private void decreaseDiscount() {
        BigDecimal newDiscount = currentDiscount.subtract(DISCOUNT_DECREASE_STEP);
        this.currentDiscount = newDiscount.max(MIN_DISCOUNT);
    }
}

