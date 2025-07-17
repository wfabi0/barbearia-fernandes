package com.onebit.barbearia_fernandes.controller;

import com.onebit.barbearia_fernandes.dto.user.CreateUserDto;
import com.onebit.barbearia_fernandes.dto.user.UserResponseDto;
import com.onebit.barbearia_fernandes.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@Tag(
        name = "Usuario",
        description = "Endpoint para gerenciamento de usuários, incluindo criação de novos usuários com validações e criptografia de senha."
)
public class UserController {

    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('BARBEIRO')")
    @Operation(
            summary = "Cria um novo usuário",
            description = "Cria um novo usuário com validações e criptografia de senha."
    )
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de validação nos dados do usuário")
    @ApiResponse(responseCode = "403", description = "Acesso negado para criar usuário")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public UserResponseDto saveUsuario(
            @Valid @RequestBody CreateUserDto createUserDto,
            Authentication authentication
    ) {
        return userService.saveUsuario(createUserDto);
    }

}
