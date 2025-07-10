package com.onebit.barbearia_fernandes.controller;

import com.onebit.barbearia_fernandes.model.Usuario;

public record UserResponseDto(String nomeUsuario, String email, String telefone, String perfil) {

    public UserResponseDto(Usuario usuario) {
        this(
                usuario.getNomeUsuario(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getPerfil()
        );
    }
}
