package br.com.orbitall.channels.services;

import br.com.orbitall.channels.canonicals.TransactionInput;
import br.com.orbitall.channels.canonicals.TransactionOutput;
import br.com.orbitall.channels.exceptions.ResourceNotFoundException;
import br.com.orbitall.channels.models.Customer;
import br.com.orbitall.channels.models.Transaction;
import br.com.orbitall.channels.repositories.CustomerRepository;
import br.com.orbitall.channels.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private TransactionService service;

    private Customer customer;
    private Transaction transaction;
    private TransactionInput transactionInput;
    private UUID customerId;
    private UUID transactionId;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        transactionId = UUID.randomUUID();

        customer = new Customer();
        customer.setId(customerId);
        customer.setFullName("João Silva");
        customer.setEmail("joao@email.com");
        customer.setPhone("(11) 99999-9999");
        customer.setActive(true);

        transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setCustomerId(customerId);
        transaction.setAmount(new BigDecimal("150.75"));
        transaction.setCardType("DÉBITO");
        transaction.setActive(true);
        transaction.setCreatedAt(LocalDateTime.now());

        transactionInput = new TransactionInput(customerId, new BigDecimal("150.75"), "DÉBITO");
    }

    @Test
    void testCreateTransaction_Success() {
        // Arrange
        when(customerRepository.findByIdAndActive(customerId)).thenReturn(Optional.of(customer));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        // Act
        TransactionOutput result = service.create(transactionInput);

        // Assert
        assertNotNull(result);
        assertEquals(customerId, result.customerId());
        assertEquals(new BigDecimal("150.75"), result.amount());
        assertEquals("DÉBITO", result.cardType());
        assertTrue(result.active());

        verify(customerRepository).findByIdAndActive(customerId);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void testCreateTransaction_CustomerNotFound() {
        // Arrange
        when(customerRepository.findByIdAndActive(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> service.create(transactionInput));

        assertTrue(exception.getMessage().contains("Cliente não encontrado"));
        verify(customerRepository).findByIdAndActive(customerId);
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void testFindByCustomerId_Success() {
        // Arrange
        List<Transaction> transactions = Arrays.asList(transaction);
        when(customerRepository.findByIdAndActive(customerId)).thenReturn(Optional.of(customer));
        when(transactionRepository.findByCustomerIdAndActiveOrderByCreatedAtDesc(customerId))
                .thenReturn(transactions);

        // Act
        List<TransactionOutput> result = service.findByCustomerId(customerId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(customerId, result.get(0).customerId());
        assertEquals("DÉBITO", result.get(0).cardType());

        verify(customerRepository).findByIdAndActive(customerId);
        verify(transactionRepository).findByCustomerIdAndActiveOrderByCreatedAtDesc(customerId);
    }

    @Test
    void testFindByCustomerId_CustomerNotFound() {
        // Arrange
        when(customerRepository.findByIdAndActive(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> service.findByCustomerId(customerId));

        assertTrue(exception.getMessage().contains("Cliente não encontrado"));
        verify(customerRepository).findByIdAndActive(customerId);
        verify(transactionRepository, never()).findByCustomerIdAndActiveOrderByCreatedAtDesc(any());
    }

    @Test
    void testFindAll_Success() {
        // Arrange
        List<Transaction> transactions = Arrays.asList(transaction);
        when(transactionRepository.findAllActiveOrderByCreatedAtDesc()).thenReturn(transactions);

        // Act
        List<TransactionOutput> result = service.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("DÉBITO", result.get(0).cardType());
        verify(transactionRepository).findAllActiveOrderByCreatedAtDesc();
    }
}