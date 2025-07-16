package com.onebit.barbearia_fernandes.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginRequestDto(
        @NotBlank(message = "O email não pode ser vazio.")
        @Email(message = "Formato de email inválido.")
        String email,

        @NotBlank(message = "A senha não pode ser vazia.")
        String senha
) {
}
