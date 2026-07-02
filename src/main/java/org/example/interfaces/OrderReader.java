package org.example.interfaces;

import org.example.entity.Order;

import java.io.BufferedReader;
import java.util.List;

public interface OrderReader {
    List<Order> read(BufferedReader bufferedReader);
}
