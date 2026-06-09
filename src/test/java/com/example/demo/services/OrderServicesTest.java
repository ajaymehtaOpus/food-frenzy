package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.example.demo.entities.Orders;
import com.example.demo.entities.User;
import com.example.demo.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServicesTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServices orderServices;

    private Orders order;
    private User user;

    @BeforeEach
    void setUp() {
        order = new Orders();
        user = new User();
    }

    @Test
    void getOrdersShouldReturnAllOrdersFromRepository() {
        List<Orders> expected = Arrays.asList(new Orders(), new Orders());
        when(orderRepository.findAll()).thenReturn(expected);

        List<Orders> actual = orderServices.getOrders();

        assertSame(expected, actual);
        assertEquals(2, actual.size());
        verify(orderRepository).findAll();
    }

    @Test
    void saveOrderShouldDelegateToRepository() {
        orderServices.saveOrder(order);

        verify(orderRepository).save(order);
        assertSame(order, order);
    }

    @Test
    void updateOrderShouldSetIdAndSaveOrder() {
        orderServices.updateOrder(42, order);

        assertEquals(42, order.getoId());
        verify(orderRepository).save(order);
    }

    @Test
    void deleteOrderShouldDelegateToRepository() {
        orderServices.deleteOrder(7);

        verify(orderRepository).deleteById(7);
        assertEquals(7, 7);
    }

    @Test
    void getOrdersForUserShouldReturnOrdersForGivenUser() {
        List<Orders> expected = Collections.singletonList(new Orders());
        when(orderRepository.findOrdersByUser(any(User.class))).thenReturn(expected);

        List<Orders> actual = orderServices.getOrdersForUser(user);

        assertSame(expected, actual);
        assertEquals(1, actual.size());
        verify(orderRepository).findOrdersByUser(user);
    }
}