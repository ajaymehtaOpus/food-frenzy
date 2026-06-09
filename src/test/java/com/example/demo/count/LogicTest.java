package com.example.demo.count;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LogicTest {

    @Test
    void shouldInstantiateLogic() {
        Logic logic = new Logic();
        assertNotNull(logic);
    }

    @Test
    void shouldCountTotalForPositiveValues() {
        double result = Logic.countTotal(10.0, 3);
        assertEquals(30.0, result);
    }

    @Test
    void shouldCountTotalForZeroQuantity() {
        double result = Logic.countTotal(10.0, 0);
        assertEquals(0.0, result);
    }
}