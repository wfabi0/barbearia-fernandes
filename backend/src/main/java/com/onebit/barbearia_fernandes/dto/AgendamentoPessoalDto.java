package com.onebit.barbearia_fernandes.dto;

import com.onebit.barbearia_fernandes.model.StatusAgendamento;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record AgendamentoPessoalDto(
        Long barbeiroId,
        StatusAgendamento status,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate data
) {
}
