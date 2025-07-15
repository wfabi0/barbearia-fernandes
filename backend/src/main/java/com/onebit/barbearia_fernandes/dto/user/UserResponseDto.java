package com.onebit.barbearia_fernandes.dto.user;

import com.onebit.barbearia_fernandes.enums.PerfilUsuario;
import lombok.Builder;

@Builder
public record UserResponseDto(
        String nome,
        String email,
        String telefone,
        PerfilUsuario perfil
) {
}
