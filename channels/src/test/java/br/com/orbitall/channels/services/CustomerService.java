package br.com.orbitall.channels.services;

import br.com.orbitall.channels.canonicals.CustomerInput;
import br.com.orbitall.channels.canonicals.CustomerOutput;
import br.com.orbitall.channels.exceptions.BusinessException;
import br.com.orbitall.channels.exceptions.ResourceNotFoundException;
import br.com.orbitall.channels.models.Customer;
import br.com.orbitall.channels.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerService service;

    private Customer customer;
    private CustomerInput customerInput;
    private UUID customerId;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();

        customer = new Customer();
        customer.setId(customerId);
        customer.setFullName("João Silva");
        customer.setEmail("joao@email.com");
        customer.setPhone("(11) 99999-9999");
        customer.setActive(true);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        customerInput = new CustomerInput("João Silva", "joao@email.com", "(11) 99999-9999");
    }

    @Test
    void testCreateCustomer_Success() {
        // Arrange
        when(repository.existsByEmail(customerInput.email())).thenReturn(false);
        when(repository.save(any(Customer.class))).thenReturn(customer);

        // Act
        CustomerOutput result = service.create(customerInput);

        // Assert
        assertNotNull(result);
        assertEquals("João Silva", result.fullName());
        assertEquals("joao@email.com", result.email());
        assertEquals("(11) 99999-9999", result.phone());
        assertTrue(result.active());

        verify(repository).existsByEmail(customerInput.email());
        verify(repository).save(any(Customer.class));
    }

    @Test
    void testCreateCustomer_EmailAlreadyExists() {
        // Arrange
        when(repository.existsByEmail(customerInput.email())).thenReturn(true);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class,
                () -> service.create(customerInput));

        assertEquals("E-mail já está em uso", exception.getMessage());
        verify(repository).existsByEmail(customerInput.email());
        verify(repository, never()).save(any(Customer.class));
    }

    @Test
    void testRetrieveCustomer_Success() {
        // Arrange
        when(repository.findByIdAndActive(customerId)).thenReturn(Optional.of(customer));

        // Act
        CustomerOutput result = service.retrieve(customerId);

        // Assert
        assertNotNull(result);
        assertEquals(customerId, result.id());
        assertEquals("João Silva", result.fullName());
        verify(repository).findByIdAndActive(customerId);
    }

    @Test
    void testRetrieveCustomer_NotFound() {
        // Arrange
        when(repository.findByIdAndActive(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> service.retrieve(customerId));

        assertTrue(exception.getMessage().contains("Cliente não encontrado"));
        verify(repository).findByIdAndActive(customerId);
    }
}