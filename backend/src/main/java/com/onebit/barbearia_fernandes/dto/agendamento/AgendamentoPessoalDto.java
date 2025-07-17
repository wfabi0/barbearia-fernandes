package com.onebit.barbearia_fernandes.dto.agendamento;

import com.onebit.barbearia_fernandes.enums.StatusAgendamento;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record AgendamentoPessoalDto(
        Long barbeiroId,
        StatusAgendamento status,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate data
) {
}
