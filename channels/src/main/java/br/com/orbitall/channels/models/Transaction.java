package br.com.orbitall.channels.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TRANSACTIONS")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull(message = "ID do cliente é obrigatório")
    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @NotNull(message = "Valor da transação é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @NotBlank(message = "Tipo de cartão é obrigatório")
    @Pattern(regexp = "DÉBITO|CRÉDITO", message = "Tipo de cartão deve ser DÉBITO ou CRÉDITO")
    @Column(name = "card_type", nullable = false)
    private String cardType;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private boolean active = true;
}