package org.example;

import org.example.utils.ParsersUtils;
import org.example.utils.discount.DiscountCalculator;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Main {



    public static void main(String[] args) {

        //Path path = Paths.get("src/main/resources/files/orders/discount_day.txt");
        Path path = Paths.get("src/main/resources/files/orders/discount_day_without_ext");
        ParsersUtils parsersUtils = new ParsersUtils(path);
        DiscountCalculator discountCalculator = new DiscountCalculator(parsersUtils);
        discountCalculator.run();
    }
}