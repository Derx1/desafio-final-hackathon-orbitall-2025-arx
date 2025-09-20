package br.com.orbitall.channels.services;

import br.com.orbitall.channels.canonicals.TransactionInput;
import br.com.orbitall.channels.canonicals.TransactionOutput;
import br.com.orbitall.channels.exceptions.ResourceNotFoundException;
import br.com.orbitall.channels.models.Transaction;
import br.com.orbitall.channels.repositories.CustomerRepository;
import br.com.orbitall.channels.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public TransactionOutput create(TransactionInput input) {
        // Validar se cliente existe e está ativo
        customerRepository.findByIdAndActive(input.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado (id: " + input.customerId() + ")"));

        Transaction transaction = new Transaction();
        // transaction.setId(UUID.randomUUID()); // REMOVA ESTA LINHA
        transaction.setCustomerId(input.customerId());
        transaction.setAmount(input.amount());
        transaction.setCardType(input.cardType());
        transaction.setActive(true);

        transactionRepository.save(transaction);

        return new TransactionOutput(
                transaction.getId(),
                transaction.getCustomerId(),
                transaction.getAmount(),
                transaction.getCardType(),
                transaction.getCreatedAt(),
                transaction.isActive()
        );
    }

    public List<TransactionOutput> findByCustomerId(UUID customerId) {
        // Validar se cliente existe e está ativo
        customerRepository.findByIdAndActive(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado (id: " + customerId + ")"));

        List<TransactionOutput> list = new ArrayList<>();

        transactionRepository.findByCustomerIdAndActiveOrderByCreatedAtDesc(customerId).forEach(transaction -> {
            TransactionOutput output = new TransactionOutput(
                    transaction.getId(),
                    transaction.getCustomerId(),
                    transaction.getAmount(),
                    transaction.getCardType(),
                    transaction.getCreatedAt(),
                    transaction.isActive()
            );
            list.add(output);
        });

        return list;
    }

    public List<TransactionOutput> findAll() {
        List<TransactionOutput> list = new ArrayList<>();

        transactionRepository.findAllActiveOrderByCreatedAtDesc().forEach(transaction -> {
            TransactionOutput output = new TransactionOutput(
                    transaction.getId(),
                    transaction.getCustomerId(),
                    transaction.getAmount(),
                    transaction.getCardType(),
                    transaction.getCreatedAt(),
                    transaction.isActive()
            );
            list.add(output);
        });

        return list;
    }
}