package com.onebit.barbearia_fernandes.dto.corte;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TipoCorteCreateDto(
        @NotBlank(message = "O nome do corte é obrigatório.")
        String nome,

        @NotNull(message = "O preço do corte é obrigatório.")
        @Positive(message = "O preço do corte deve ser um valor positivo.")
        BigDecimal preco
) {
}
