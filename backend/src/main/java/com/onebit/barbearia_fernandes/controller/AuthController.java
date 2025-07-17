package com.onebit.barbearia_fernandes.controller;

import com.onebit.barbearia_fernandes.dto.auth.LoginRequestDto;
import com.onebit.barbearia_fernandes.dto.auth.LoginResponseDto;
import com.onebit.barbearia_fernandes.dto.auth.RegisterReponseDto;
import com.onebit.barbearia_fernandes.dto.auth.RegisterRequestDto;
import com.onebit.barbearia_fernandes.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Tag(
        name = "Autenticação",
        description = "Endpoints para autenticação de usuários, incluindo login e registro."
)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Realiza o login do usuário e retorna um token de autenticação.")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso, retorna o token de autenticação.")
    @ApiResponse(responseCode = "400", description = "Dados de login inválidos.")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterReponseDto> register(@Valid @RequestBody RegisterRequestDto requestDto) {
        return ResponseEntity.ok(authService.register(requestDto));
    }

}
