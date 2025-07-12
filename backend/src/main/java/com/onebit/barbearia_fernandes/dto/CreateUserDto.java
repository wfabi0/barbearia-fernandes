package com.onebit.barbearia_fernandes.dto;

import lombok.Builder;

@Builder
public record CreateUserDto(
        String nomeUsuario,
        String email,
        String telefone,
        String senha
) {
}


