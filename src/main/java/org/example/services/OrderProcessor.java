package org.example.services;

import org.example.entity.Order;
import org.example.storage.OrderFileReader;
import org.example.storage.OrderFileWriter;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;

import static java.util.Comparator.comparing;

public class OrderProcessor {

    private final OrderFileReader reader;
    private final OrderFileWriter writer;
    private final DiscountService discountService;

    public OrderProcessor(OrderFileReader reader, OrderFileWriter writer, DiscountService discountService) {
        this.reader = reader;
        this.writer = writer;
        this.discountService = discountService;
    }

    public void process(Path path) {

        List<Order> orders = reader.read();

        orders.sort(comparing(Order::getCreated));

        discountService.reset();

        for (Order order : orders) {
            BigDecimal discountedPrice = discountService.calculateDiscountedPrice(
                    BigDecimal.valueOf(order.getWeightKg())
            );
            order.setOrderDiscountPrice(discountedPrice);
        }

        writer.write(orders, path);
    }
}
