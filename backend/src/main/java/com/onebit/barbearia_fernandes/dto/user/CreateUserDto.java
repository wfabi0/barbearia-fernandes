package com.onebit.barbearia_fernandes.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateUserDto(
        @NotBlank(message = "O nome é obrigatório.")
        @Size(min = 3, max = 100, message = "O nome deve ter entre {min} e {max} caracteres.")
        String nome,

        @NotBlank(message = "O email é obrigatório.")
        @Email(message = "O email deve ser válido.")
        String email,

        @NotBlank(message = "O telefone é obrigatório.")
        @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}\\-?\\d{4}$",
                message = "Formato de telefone inválido. Use (XX) XXXX-XXXX ou (XX) 9XXXX-XXXX")
        String telefone,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 8, message = "A senha deve ter no mínimo {min} caracteres.")
        String senha
) {
}


