package br.com.orbitall.channels.canonicals;

import jakarta.validation.constraints.*;

public record CustomerInput(
        @NotBlank(message = "Nome completo é obrigatório")
        @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
        String fullName,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail deve ter um formato válido")
        @Size(max = 100, message = "E-mail deve ter no máximo 100 caracteres")
        String email,

        @NotBlank(message = "Telefone é obrigatório")
        String phone
) {
}