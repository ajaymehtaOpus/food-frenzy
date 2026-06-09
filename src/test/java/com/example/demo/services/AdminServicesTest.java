package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.entities.Admin;
import com.example.demo.repositories.AdminRepository;

@ExtendWith(MockitoExtension.class)
class AdminServicesTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminServices adminServices;

    private Admin admin1;
    private Admin admin2;

    @BeforeEach
    void setUp() {
        admin1 = new Admin();
        admin1.setAdminId(1);
        admin1.setAdminEmail("admin1@example.com");
        admin1.setAdminPassword("secret1");

        admin2 = new Admin();
        admin2.setAdminId(2);
        admin2.setAdminEmail("admin2@example.com");
        admin2.setAdminPassword("secret2");
    }

    @Test
    void getAllShouldReturnAllAdmins() {
        when(adminRepository.findAll()).thenReturn(Arrays.asList(admin1, admin2));

        List<Admin> result = adminServices.getAll();

        assertEquals(2, result.size());
        assertSame(admin1, result.get(0));
        verify(adminRepository, times(1)).findAll();
    }

    @Test
    void getAdminShouldReturnAdminById() {
        when(adminRepository.findById(1)).thenReturn(Optional.of(admin1));

        Admin result = adminServices.getAdmin(1);

        assertSame(admin1, result);
        assertEquals(1, result.getAdminId());
        verify(adminRepository, times(1)).findById(1);
    }

    @Test
    void getAdminShouldThrowWhenAdminNotFound() {
        when(adminRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(java.util.NoSuchElementException.class, () -> adminServices.getAdmin(99));
        verify(adminRepository, times(1)).findById(99);
    }

    @Test
    void updateShouldSaveAdminWhenMatchingIdExists() {
        when(adminRepository.findAll()).thenReturn(Collections.singletonList(admin1));

        adminServices.update(admin2, 1);

        verify(adminRepository, times(1)).findAll();
        verify(adminRepository, times(1)).save(admin2);
    }

    @Test
    void updateShouldNotSaveAdminWhenNoMatchingIdExists() {
        when(adminRepository.findAll()).thenReturn(Collections.singletonList(admin1));

        adminServices.update(admin2, 2);

        verify(adminRepository, times(1)).findAll();
        verify(adminRepository, times(0)).save(admin2);
    }

    @Test
    void deleteShouldDelegateToRepository() {
        adminServices.delete(5);

        verify(adminRepository, times(1)).deleteById(5);
        assertTrue(true);
    }

    @Test
    void addAdminShouldSaveAdmin() {
        adminServices.addAdmin(admin1);

        verify(adminRepository, times(1)).save(admin1);
        assertEquals("admin1@example.com", admin1.getAdminEmail());
    }

    @Test
    void validateAdminCredentialsShouldReturnTrueForValidCredentials() {
        when(adminRepository.findByAdminEmail("admin1@example.com")).thenReturn(admin1);

        boolean result = adminServices.validateAdminCredentials("admin1@example.com", "secret1");

        assertTrue(result);
        verify(adminRepository, times(1)).findByAdminEmail("admin1@example.com");
    }

    @Test
    void validateAdminCredentialsShouldReturnFalseForInvalidPassword() {
        when(adminRepository.findByAdminEmail("admin1@example.com")).thenReturn(admin1);

        boolean result = adminServices.validateAdminCredentials("admin1@example.com", "wrong");

        assertFalse(result);
        verify(adminRepository, times(1)).findByAdminEmail("admin1@example.com");
    }

    @Test
    void validateAdminCredentialsShouldReturnFalseWhenAdminNotFound() {
        when(adminRepository.findByAdminEmail("missing@example.com")).thenReturn(null);

        boolean result = adminServices.validateAdminCredentials("missing@example.com", "secret");

        assertFalse(result);
        verify(adminRepository, times(1)).findByAdminEmail("missing@example.com");
    }
}