package com.onebit.barbearia_fernandes.dto.comentario;

import com.onebit.barbearia_fernandes.dto.user.UserResumeResponseDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ComentarioResponseDto(
        Long comentarioId,
        Long agendamentoId,
        UserResumeResponseDto cliente,
        UserResumeResponseDto barbeiro,
        int nota,
        String descricao,
        LocalDateTime dataCriacao
) {
}
