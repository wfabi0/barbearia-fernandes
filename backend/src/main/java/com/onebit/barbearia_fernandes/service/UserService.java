package com.onebit.barbearia_fernandes.service;

import com.onebit.barbearia_fernandes.dto.user.CreateUserDto;
import com.onebit.barbearia_fernandes.dto.user.UpdateUserDto;
import com.onebit.barbearia_fernandes.dto.user.UserListResponseDto;
import com.onebit.barbearia_fernandes.dto.user.UserResponseDto;
import com.onebit.barbearia_fernandes.exception.ResourceNotFoundException;
import com.onebit.barbearia_fernandes.model.Usuario;
import com.onebit.barbearia_fernandes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        if (findUsuarioByEmail(dto.email())) {
            throw new DataIntegrityViolationException("Já existe um usuário cadastrado com o e-mail: " + dto.email());
        }

        if (findUsuarioByTelefone(dto.telefone())) {
            throw new DataIntegrityViolationException("Já existe um usuário cadastrado com o telefone: " + dto.telefone());
        }

        Usuario entity = new Usuario();
        entity.setNomeUsuario(dto.nome());
        entity.setEmail(dto.email());
        entity.setTelefone(dto.telefone());
        entity.setSenha(passwordEncoder.encode(dto.senha()));

        Usuario novoUsuario = userRepository.save(entity);

        return toResponseDto(novoUsuario);
    }

    @Transactional(readOnly = true)
    public Page<UserListResponseDto> getUsuarios(Pageable pageable) {
        Page<Usuario> usuarios = userRepository.findAll(pageable);
        return usuarios.map(this::toResponseListDto);
    }

    @Transactional
    public UserResponseDto updateUsuario(Long userId, UpdateUserDto dto) {
        Usuario usuarioExistente = getUsuarioById(userId);

        if (dto.email() != null && !usuarioExistente.getEmail().equals(dto.email()) && findUsuarioByEmail(dto.email())) {
            throw new DataIntegrityViolationException("Já existe um usuário cadastrado com o e-mail: " + dto.email());
        }

        if (dto.telefone() != null && !usuarioExistente.getTelefone().equals(dto.telefone()) && findUsuarioByTelefone(dto.telefone())) {
            throw new DataIntegrityViolationException("Já existe um usuário cadastrado com o telefone: " + dto.telefone());
        }

        if (dto.nome() != null && !dto.nome().trim().isEmpty()) {
            usuarioExistente.setNomeUsuario(dto.nome());
        }

        if (dto.email() != null && !dto.email().trim().isEmpty()) {
            usuarioExistente.setEmail(dto.email());
        }

        if (dto.telefone() != null && !dto.telefone().trim().isEmpty()) {
            usuarioExistente.setTelefone(dto.telefone());
        }

        if (dto.senha() != null && !dto.senha().trim().isEmpty()) {
            usuarioExistente.setSenha(passwordEncoder.encode(dto.senha()));
        }

        Usuario usuarioAtualizado = userRepository.save(usuarioExistente);
        return toResponseDto(usuarioAtualizado);
    }

    @Transactional
    public void deleteUsuario(Long userId) {
        Usuario usuario = getUsuarioById(userId);
        userRepository.delete(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario getUsuarioByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o e-mail: " + email));
    }

    @Transactional(readOnly = true)
    public Usuario getUsuarioById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + userId));
    }

    @Transactional(readOnly = true)
    public boolean findUsuarioByTelefone(String telefone) {
        return userRepository.existsByTelefone(telefone);
    }

    @Transactional(readOnly = true)
    public boolean findUsuarioByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private UserResponseDto toResponseDto(Usuario usuario) {
        return new UserResponseDto(
                usuario.getNomeUsuario(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getPerfil()
        );
    }

    private UserListResponseDto toResponseListDto(Usuario usuario) {
        return new UserListResponseDto(
                usuario.getUserId(),
                usuario.getNomeUsuario(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getPerfil()
        );
    }

}
