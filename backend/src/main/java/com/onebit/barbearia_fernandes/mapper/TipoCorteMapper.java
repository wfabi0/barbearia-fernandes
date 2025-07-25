package com.onebit.barbearia_fernandes.mapper;

import com.onebit.barbearia_fernandes.dto.corte.TipoCorteResponseDto;
import com.onebit.barbearia_fernandes.model.TipoCorte;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.Locale;

@Component
public class TipoCorteMapper {

    private static final Locale PT_BR = new Locale("pt", "BR");

    private static final ThreadLocal<NumberFormat> CURRENCY_FORMATTER =
            ThreadLocal.withInitial(() -> NumberFormat.getCurrencyInstance(PT_BR));

    public TipoCorteResponseDto toResponseDto(TipoCorte corte) {
        String precoFormatted = CURRENCY_FORMATTER.get().format(corte.getPreco());

        return TipoCorteResponseDto.builder()
                .corteId(corte.getCorteId())
                .nomeCorte(corte.getNomeCorte())
                .preco(corte.getPreco())
                .precoFormatted(precoFormatted)
                .build();
    }

}
