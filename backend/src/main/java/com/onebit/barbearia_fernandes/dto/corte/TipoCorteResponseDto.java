package com.onebit.barbearia_fernandes.dto.corte;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TipoCorteResponseDto(
        Long corteId,
        String nomeCorte,
        BigDecimal preco,
        String precoFormatted
) {
}
