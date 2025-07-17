package com.onebit.barbearia_fernandes.service;

import com.onebit.barbearia_fernandes.dto.auth.LoginRequestDto;
import com.onebit.barbearia_fernandes.dto.auth.LoginResponseDto;
import com.onebit.barbearia_fernandes.dto.auth.RegisterReponseDto;
import com.onebit.barbearia_fernandes.dto.auth.RegisterRequestDto;
import com.onebit.barbearia_fernandes.model.Usuario;
import com.onebit.barbearia_fernandes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto dto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.email(), dto.senha())
            );
        } catch (AuthenticationException ex) {
            throw new DataIntegrityViolationException("E-mail ou senha inválidos.");
        }

        Usuario user = usuarioRepository.findByEmail(dto.email()).orElseThrow(() ->
                new DataIntegrityViolationException("E-mail ou senha inválidos.")
        );

        UserDetails userDetails = User.builder()
                .username(user.getEmail())
                .password(user.getSenha())
                .roles(user.getPerfil().toString())
                .build();

        String token = jwtService.generateToken(userDetails);

        return new LoginResponseDto(token);
    }

    @Transactional
    public RegisterReponseDto register(RegisterRequestDto dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new DataIntegrityViolationException("E-mail já cadastrado.");
        }

        if (usuarioRepository.existsByTelefone(dto.telefone())) {
            throw new DataIntegrityViolationException("O telefone informado já está sendo usado.");
        }

        Usuario entity = new Usuario();
        entity.setNomeUsuario(dto.nome());
        entity.setEmail(dto.email());
        entity.setTelefone(dto.telefone());
        entity.setSenha(passwordEncoder.encode(dto.senha()));

        usuarioRepository.save(entity);

        return toResponseDto(entity);
    }

    private RegisterReponseDto toResponseDto(Usuario usuario) {
        return new RegisterReponseDto(
                usuario.getNomeUsuario(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getPerfil()
        );
    }

}
