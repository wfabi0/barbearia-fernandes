package com.onebit.barbearia_fernandes.dto;

public record CreateUserDto(
        String nomeUsuario,
        String email,
        String telefone,
        String senha
) {
}


