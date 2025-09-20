package br.com.orbitall.channels.services;

import br.com.orbitall.channels.canonicals.CustomerInput;
import br.com.orbitall.channels.canonicals.CustomerOutput;
import br.com.orbitall.channels.exceptions.BusinessException;
import br.com.orbitall.channels.exceptions.ResourceNotFoundException;
import br.com.orbitall.channels.models.Customer;
import br.com.orbitall.channels.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public CustomerOutput create(CustomerInput input) {
        // Validar se email já existe
        if (repository.existsByEmail(input.email())) {
            throw new BusinessException("E-mail já está em uso");
        }

        Customer customer = new Customer();
        // Removido: customer.setId(UUID.randomUUID()); - O Hibernate vai gerar automaticamente
        customer.setFullName(input.fullName());
        customer.setEmail(input.email());
        customer.setPhone(input.phone());
        customer.setActive(true);

        repository.save(customer);

        return new CustomerOutput(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.isActive()
        );
    }

    public CustomerOutput retrieve(UUID id) {
        Customer customer = repository
                .findByIdAndActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado (id: " + id + ")"));

        return new CustomerOutput(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.isActive()
        );
    }

    public CustomerOutput update(UUID id, CustomerInput input) {
        Customer fetched = repository
                .findByIdAndActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado (id: " + id + ")"));

        // Validar se email já existe (exceto para o próprio cliente)
        if (!fetched.getEmail().equals(input.email()) && repository.existsByEmail(input.email())) {
            throw new BusinessException("E-mail já está em uso");
        }

        fetched.setFullName(input.fullName());
        fetched.setEmail(input.email());
        fetched.setPhone(input.phone());

        repository.save(fetched);

        return new CustomerOutput(
                fetched.getId(),
                fetched.getFullName(),
                fetched.getEmail(),
                fetched.getPhone(),
                fetched.getCreatedAt(),
                fetched.getUpdatedAt(),
                fetched.isActive()
        );
    }

    public CustomerOutput delete(UUID id) {
        Customer fetched = repository
                .findByIdAndActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado (id: " + id + ")"));

        fetched.setActive(false);
        repository.save(fetched);

        return new CustomerOutput(
                fetched.getId(),
                fetched.getFullName(),
                fetched.getEmail(),
                fetched.getPhone(),
                fetched.getCreatedAt(),
                fetched.getUpdatedAt(),
                fetched.isActive()
        );
    }

    public List<CustomerOutput> findAll() {
        List<CustomerOutput> list = new ArrayList<>();

        repository.findAllActive().forEach(customer -> {
            CustomerOutput output = new CustomerOutput(
                    customer.getId(),
                    customer.getFullName(),
                    customer.getEmail(),
                    customer.getPhone(),
                    customer.getCreatedAt(),
                    customer.getUpdatedAt(),
                    customer.isActive()
            );
            list.add(output);
        });

        return list;
    }
}