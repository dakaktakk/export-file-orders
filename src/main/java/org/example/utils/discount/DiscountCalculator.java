package org.example.utils.discount;

import org.example.entity.Order;
import org.example.interfaces.OrderReader;
import org.example.utils.ParsersUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.example.constants.Constants.INITIAL_DISCOUNT_PERCENT;
import static org.example.constants.Constants.SCALE;
import static org.example.constants.Constants.WRITE_FILE_PATH;
import static org.example.constants.Constants.PRICE_PER_KG;
import static org.example.constants.Constants.DISCOUNT_DECREASE_STEP;
import static org.example.constants.Constants.MIN_DISCOUNT;

/**
 * Расчёт счидки счёта
 * @author Dmitriy Gromov
 * @since 02.07.2026
 */
public class DiscountCalculator {

    private final ParsersUtils parsersUtils;
    private BigDecimal discount = INITIAL_DISCOUNT_PERCENT;
    private static final List<Order> orders = new ArrayList<>();
    private final Path folder;

    public DiscountCalculator (ParsersUtils parsersUtils) {
        this.parsersUtils = parsersUtils;
        this.folder = this.parsersUtils.getPath();
    }

    public void run() {

        readFile(folder);

        orders.sort(Comparator.comparing(Order::getCreated));

        orders.forEach(this::discountCalculator);

        writeFile();
    }

    private void writeFile() {
        List<String> lines = orders.stream()
                .map(order -> String.format("%s - %s",
                        order.getCompanyName(), order.getOrderDiscountPrice().setScale(SCALE, RoundingMode.HALF_UP)))
                .toList();

        Path writePath = Path.of(WRITE_FILE_PATH);

        try {
            Files.write(writePath, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи в файл", e);
        }
    }

    private void readFile(Path path) {

        OrderReader orderReader = parsersUtils.GetParser();
        
        try {
            orders.addAll(orderReader.read(Files.newBufferedReader(path)));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при открытии для чтения файлов", e);
        }
    }

    private void discountCalculator(Order order) {

        BigDecimal basePrice = BigDecimal.valueOf(order.getWeightKg())
                .multiply(PRICE_PER_KG);

        BigDecimal discountFraction = getDiscount()
                .divide(BigDecimal.valueOf(100), SCALE, RoundingMode.HALF_UP);

        BigDecimal discountMultiplier = BigDecimal.ONE
                .subtract(discountFraction);

        BigDecimal orderDiscountPrice = basePrice
                .multiply(discountMultiplier)
                .setScale(SCALE, RoundingMode.HALF_UP);

        order.setOrderDiscountPrice(orderDiscountPrice);

        BigDecimal newDiscount = getDiscount()
                .subtract(DISCOUNT_DECREASE_STEP);

        setDiscount(newDiscount);
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        if(discount == null) {
            this.discount = MIN_DISCOUNT;
            return;
        }

        this.discount = discount.max(MIN_DISCOUNT);
    }
}
