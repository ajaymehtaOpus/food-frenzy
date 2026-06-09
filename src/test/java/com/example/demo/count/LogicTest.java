package com.example.demo.count;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LogicTest {

    @Test
    void countTotal_shouldMultiplyPriceByQuantity() {
        double result = Logic.countTotal(10.5, 3);

        assertEquals(31.5, result, 0.000001);
    }

    @Test
    void countTotal_shouldReturnZeroWhenQuantityIsZero() {
        double result = Logic.countTotal(99.99, 0);

        assertEquals(0.0, result, 0.000001);
    }
}