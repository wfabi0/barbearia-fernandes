package com.onebit.barbearia_fernandes.service;

import com.onebit.barbearia_fernandes.controller.CreateUserDto;
import com.onebit.barbearia_fernandes.controller.UserResponseDto;
import com.onebit.barbearia_fernandes.model.Usuario;
import com.onebit.barbearia_fernandes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private final UsuarioRepository userRepository;

    public UserService(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto saveUsuario(CreateUserDto dto){
        Usuario entity = new Usuario();
        entity.setNomeUsuario(dto.nomeUsuario());
        entity.setEmail(dto.email());
        entity.setTelefone(dto.telefone());
        entity.setSenha(dto.senha());
        entity.setPerfil(dto.perfil());


        return new UserResponseDto(entity);

    }
}
