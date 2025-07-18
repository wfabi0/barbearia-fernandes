package com.onebit.barbearia_fernandes.dto.comentario;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ComentarioCreateDto(
        @NotBlank(message = "A descrição do comentário não pode estar em branco.")
        String descricao,

        @NotNull(message = "O ID do agendamento não pode estar em branco.")
        Long agendamentoId,

        @NotNull(message = "A nota não pode estar em branco.")
        @Min(value = 0, message = "A nota deve ser maior ou igual a 0.")
        @Max(value = 10, message = "A nota deve ser menor ou igual a 10.")
        Integer nota
) {
}
