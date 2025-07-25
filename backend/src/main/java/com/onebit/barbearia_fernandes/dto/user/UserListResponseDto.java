package com.onebit.barbearia_fernandes.dto.user;

import com.onebit.barbearia_fernandes.enums.PerfilUsuario;

public record UserListResponseDto(
        Long id,
        String nome,
        String email,
        String telefone,
        PerfilUsuario perfil
) {
}
