package br.com.orbitall.channels.canonicals;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record TransactionInput(
        @NotNull(message = "ID do cliente é obrigatório")
        UUID customerId,

        @NotNull(message = "Valor da transação é obrigatório")
        @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
        BigDecimal amount,

        @NotBlank(message = "Tipo de cartão é obrigatório")
        @Pattern(regexp = "DÉBITO|CRÉDITO", message = "Tipo de cartão deve ser DÉBITO ou CRÉDITO")
        String cardType
) {
}