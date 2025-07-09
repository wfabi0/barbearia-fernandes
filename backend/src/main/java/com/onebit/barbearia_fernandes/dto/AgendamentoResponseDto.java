package com.onebit.barbearia_fernandes.dto;

import com.onebit.barbearia_fernandes.model.StatusAgendamento;

import java.time.LocalDateTime;

public record AgendamentoResponseDto (
        Long agendamentoId,
        Long clienteId,
        Long barbeiroId,
        Long tipoCorteId,
        LocalDateTime dataHora,
        StatusAgendamento status,
        LocalDateTime createdAt
) {
}
