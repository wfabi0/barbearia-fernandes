package com.onebit.barbearia_fernandes.dto.agendamento;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AgendamentoCreateDto(
        // TODO: lembrar de remover o id do cliente pois sera pelo JWT
        @NotNull(message = "O ID do cliente é obrigatório")
        Long clientId,

        @NotNull(message = "O ID do barbeiro é obrigatório")
        Long barbeiroId,

        @NotNull(message = "O ID do tipo de corte é obrigatório")
        Long tipoCorteId,

        @NotNull(message = "A data e hora são obrigatórias")
        @Future(message = "A data do agendamento deve ser no futuro")
        LocalDateTime data_hora
) {
}
