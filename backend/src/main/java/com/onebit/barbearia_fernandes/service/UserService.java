package com.onebit.barbearia_fernandes.service;

import com.onebit.barbearia_fernandes.dto.user.CreateUserDto;
import com.onebit.barbearia_fernandes.dto.user.UserResponseDto;
import com.onebit.barbearia_fernandes.model.Usuario;
import com.onebit.barbearia_fernandes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto saveUsuario(CreateUserDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new DataIntegrityViolationException("O e-mail informado já está sendo usado.");
        }

        if (userRepository.existsByTelefone(dto.telefone())) {
            throw new DataIntegrityViolationException("O telefone informado já está sendo usado.");
        }

        Usuario entity = new Usuario();
        entity.setNomeUsuario(dto.nome());
        entity.setEmail(dto.email());
        entity.setTelefone(dto.telefone());
        entity.setSenha(passwordEncoder.encode(dto.senha()));

        Usuario usuario = userRepository.save(entity);

        return toResponseDto(usuario);
    }

    private UserResponseDto toResponseDto(Usuario usuario) {
        return new UserResponseDto(usuario.getNomeUsuario(), usuario.getEmail(), usuario.getTelefone(), usuario.getPerfil());
    }

}
