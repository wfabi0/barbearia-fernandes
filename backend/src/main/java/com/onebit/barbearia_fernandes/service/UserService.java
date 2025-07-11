package com.onebit.barbearia_fernandes.service;

import com.onebit.barbearia_fernandes.dto.CreateUserDto;
import com.onebit.barbearia_fernandes.dto.UserResponseDto;
import com.onebit.barbearia_fernandes.model.Usuario;
import com.onebit.barbearia_fernandes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UsuarioRepository userRepository;

    @Autowired
    public UserService(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponseDto saveUsuario(CreateUserDto dto) {
        Usuario entity = new Usuario();
        entity.setNomeUsuario(dto.nomeUsuario());
        entity.setEmail(dto.email());
        entity.setTelefone(dto.telefone());
        entity.setSenha(dto.senha());

        Usuario usuario = userRepository.save(entity);

        return toResponseDto(usuario);
    }

    private UserResponseDto toResponseDto(Usuario usuario) {
        return new UserResponseDto(
                usuario.getNomeUsuario(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getPerfil()
        );
    }

}
