package com.onebit.barbearia_fernandes.dto;

import com.onebit.barbearia_fernandes.enums.StatusAgendamento;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record AgendamentoFilter(
        Long barbeiroId,
        Long clienteId,
        StatusAgendamento status,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate data
) {
}
