package com.onebit.barbearia_fernandes.dto;

import com.onebit.barbearia_fernandes.model.PerfilUsuario;

public record UserResponseDto(
        String nomeUsuario,
        String email,
        String telefone,
        PerfilUsuario perfil
) {
}
