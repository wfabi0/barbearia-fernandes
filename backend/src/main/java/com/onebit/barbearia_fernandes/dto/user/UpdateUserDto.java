package com.onebit.barbearia_fernandes.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserDto(
        @Size(min = 3, max = 100, message = "O nome deve ter entre {min} e {max} caracteres.")
        String nome,

        @Email(message = "O email deve ser válido.")
        String email,

        @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}\\-?\\d{4}$",
                message = "Formato de telefone inválido. Use (XX) XXXX-XXXX ou (XX) 9XXXX-XXXX")
        String telefone,

        @Size(min = 8, message = "A senha deve ter no mínimo {min} caracteres.")
        String senha
) {
}