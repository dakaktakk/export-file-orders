package org.example.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Счёт
 * @author Dmitriy Gromov
 * @since 01.07.2026
 */

public class Order implements Comparable<Order>  {

    private final LocalDateTime created;
    private final String companyName;
    private final int weightKg;
    private BigDecimal orderDiscountPrice;

    public Order (LocalDateTime created, String companyName, int weightKg) {
        this.created = created;
        this.companyName = companyName;
        this.weightKg = weightKg;
    }

    @Override
    public int compareTo(Order other) {
        return this.created.compareTo(other.created);
    }

    public LocalDateTime getCreated() {
        return this.created;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public int getWeightKg() {
        return this.weightKg;
    }

    public BigDecimal getOrderDiscountPrice() {
        return this.orderDiscountPrice;
    }

    public void setOrderDiscountPrice(BigDecimal price) {
        this.orderDiscountPrice = price;
    }
}
