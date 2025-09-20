package br.com.orbitall.channels.repositories;

import br.com.orbitall.channels.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("SELECT t FROM Transaction t WHERE t.customerId = :customerId AND t.active = true ORDER BY t.createdAt DESC")
    List<Transaction> findByCustomerIdAndActiveOrderByCreatedAtDesc(@Param("customerId") UUID customerId);

    @Query("SELECT t FROM Transaction t WHERE t.active = true ORDER BY t.createdAt DESC")
    List<Transaction> findAllActiveOrderByCreatedAtDesc();
}