package com.onebit.barbearia_fernandes.dto;

import com.onebit.barbearia_fernandes.enums.PerfilUsuario;
import lombok.Builder;

@Builder
public record UserResponseDto(
        String nomeUsuario,
        String email,
        String telefone,
        PerfilUsuario perfil
) {
}
