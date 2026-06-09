package com.example.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import com.example.demo.entities.Product;
import com.example.demo.loginCredentials.AdminLogin;
import com.example.demo.services.ProductServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {

    @Mock
    private ProductServices productServices;

    @Mock
    private Model model;

    @InjectMocks
    private HomeController homeController;

    @Test
    void homeShouldReturnHomeView() {
        String viewName = homeController.home();

        assertEquals("Home", viewName);
    }

    @Test
    void productsShouldAddProductsToModelAndReturnProductsView() {
        Product product = new Product();
        List<Product> products = Collections.singletonList(product);
        when(productServices.getAllProducts()).thenReturn(products);

        String viewName = homeController.products(model);

        assertEquals("Products", viewName);
        verify(productServices).getAllProducts();
        verify(model).addAttribute(eq("products"), eq(products));
    }

    @Test
    void locationShouldReturnLocateUsView() {
        String viewName = homeController.location();

        assertEquals("Locate_us", viewName);
    }

    @Test
    void aboutShouldReturnAboutView() {
        String viewName = homeController.about();

        assertEquals("About", viewName);
    }

    @Test
    void loginShouldAddAdminLoginToModelAndReturnLoginView() {
        String viewName = homeController.login(model);

        assertEquals("Login", viewName);
        verify(model).addAttribute(eq("adminLogin"), any(AdminLogin.class));
    }
}