package br.com.orbitall.channels.repositories;

import br.com.orbitall.channels.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @Query("SELECT c FROM Customer c WHERE c.active = true")
    List<Customer> findAllActive();

    @Query("SELECT c FROM Customer c WHERE c.id = :id AND c.active = true")
    Optional<Customer> findByIdAndActive(UUID id);

    boolean existsByEmail(String email);
}