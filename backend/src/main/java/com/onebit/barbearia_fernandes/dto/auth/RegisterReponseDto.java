package com.onebit.barbearia_fernandes.dto.auth;

import com.onebit.barbearia_fernandes.enums.PerfilUsuario;
import lombok.Builder;

@Builder
public record RegisterReponseDto(
        String nome,
        String email,
        String telefone,
        PerfilUsuario perfil
) {
}
