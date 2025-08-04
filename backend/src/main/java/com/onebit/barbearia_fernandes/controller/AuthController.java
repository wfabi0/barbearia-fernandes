package com.onebit.barbearia_fernandes.controller;

import com.onebit.barbearia_fernandes.dto.auth.LoginRequestDto;
import com.onebit.barbearia_fernandes.dto.auth.LoginResponseDto;
import com.onebit.barbearia_fernandes.dto.auth.RegisterReponseDto;
import com.onebit.barbearia_fernandes.dto.auth.RegisterRequestDto;
import com.onebit.barbearia_fernandes.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
    @ApiResponse(responseCode = "429", description = "Limite de requisições excedido. Tente novamente mais tarde.")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto requestDto,
            HttpServletRequest request
    ) {
        String ipAddress = getClientIp(request);
        return ResponseEntity.ok(authService.login(requestDto, ipAddress));
    }

    @PostMapping("/register")
    @Operation(summary = "Registro", description = "Registra um novo usuário e retorna os detalhes do registro.")
    @ApiResponse(responseCode = "200", description = "Registro realizado com sucesso, retorna os detalhes do usuário registrado.")
    @ApiResponse(responseCode = "400", description = "Dados de registro inválidos.")
    @ApiResponse(responseCode = "429", description = "Limite de requisições excedido. Tente novamente mais tarde.")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    public ResponseEntity<RegisterReponseDto> register(
            @Valid @RequestBody RegisterRequestDto requestDto,
            HttpServletRequest request
    ) {
        String ipAddress = getClientIp(request);
        return ResponseEntity.ok(authService.register(requestDto, ipAddress));
    }

    private String getClientIp(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String[] headers = {
                "X-Forwarded-For",
                "X-Real-IP",
                "X-Original-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP"
        };
        for (String header : headers) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return ip.split(",")[0].trim();
            }
        }
        return request.getRemoteAddr();
    }

}
