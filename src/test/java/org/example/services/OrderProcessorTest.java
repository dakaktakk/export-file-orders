package org.example.services;

import org.example.entity.Order;
import org.example.storage.OrderFileReader;
import org.example.storage.OrderFileWriter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderProcessorTest {
    
    @Test
    @DisplayName("Должен отсортировать заказы по дате создания, рассчитать скидку для каждого и записать результат в файл")
    void process_shouldSortOrdersByCreatedDateCalculateDiscountAndWriteToFile(@TempDir Path tempDir) {

        Order order1 = new Order(
                    LocalDateTime.of(2026,1,1,12,0),
                    "Company A",
                    100);

        Order order2 = new Order(
                LocalDateTime.of(2026,1,1,10,0),
                "Company B",
                50);

        Order order3 = new Order(
                LocalDateTime.of(2026,1,1,14,0),
                "Company C",
                150);

        List<Order> inputOrders = Arrays.asList(order1, order2, order3);

        StubOrderFileReader reader = new StubOrderFileReader(inputOrders);
        CapturingOrderFileWriter writer = new CapturingOrderFileWriter();
        DiscountService discountService = new DiscountService();

        OrderProcessor processor = new OrderProcessor(reader, writer, discountService);

        Path outputPath = tempDir.resolve("result.txt");

        processor.process(outputPath);

        List<Order> writtenOrders = writer.getCapturedOrders();

        assertEquals(3, writtenOrders.size());
        assertEquals(order2, writtenOrders.get(0));
        assertEquals(order1, writtenOrders.get(1));
        assertEquals(order3, writtenOrders.get(2));

        assertEquals(outputPath, writer.getCapturedPath());

        for (Order order: writtenOrders) {
            assertNotNull(order.getOrderDiscountPrice(), "Цена со скидкой не должна быть null");
            assertTrue(order.getOrderDiscountPrice().compareTo(ZERO) > 0,
                    "Цена со скидкой должны быть больше нуля");
        }


    }

    private static class StubOrderFileReader extends OrderFileReader {

        private final List<Order> ordersToReturn;

        public StubOrderFileReader(List<Order> orders) {
            super(null);
            this.ordersToReturn = orders;
        }

        @Override
        public List<Order> read() {
            return ordersToReturn;
        }

    }

    private static class CapturingOrderFileWriter extends OrderFileWriter {

        private List<Order> capturedOrders;
        private Path capturedPath;

        @Override
        public void write(List<Order> orders, Path outputPath) {
            this.capturedOrders = orders;
            this.capturedPath = outputPath;
        }

        public List<Order> getCapturedOrders() {
            return capturedOrders;
        }

        public Path getCapturedPath() {
            return capturedPath;
        }
    }
}