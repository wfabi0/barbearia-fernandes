package com.onebit.barbearia_fernandes.controller;

import com.onebit.barbearia_fernandes.dto.user.CreateUserDto;
import com.onebit.barbearia_fernandes.dto.user.UpdateUserDto;
import com.onebit.barbearia_fernandes.dto.user.UserListReponseDto;
import com.onebit.barbearia_fernandes.dto.user.UserResponseDto;
import com.onebit.barbearia_fernandes.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@Tag(
        name = "Usuario",
        description = "Endpoint para gerenciamento de usuários, incluindo criação de novos usuários com validações e criptografia de senha."
)
@SecurityRequirement(name = "bearerAuth")
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

    @GetMapping
    @PreAuthorize("hasRole('BARBEIRO')")
    @Operation(
            summary = "Lista usuários",
            description = "Retorna uma lista paginada de usuários."
    )
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado para listar usuários")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public Page<UserListReponseDto> getListaUsuarios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "userId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return userService.getUsuarios(pageable);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('BARBEIRO')")
    @Operation(
            summary = "Atualiza um usuário",
            description = "Atualiza os dados de um usuário existente."
    )
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de validação nos dados do usuário")
    @ApiResponse(responseCode = "403", description = "Acesso negado para atualizar usuário")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public UserResponseDto updateUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserDto updateUserDto,
            Authentication authentication
    ) {
        return userService.updateUsuario(id, updateUserDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('BARBEIRO')")
    @Operation(
            summary = "Deleta um usuário",
            description = "Deleta um usuário existente pelo ID."
    )
    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado para deletar usuário")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUsuario(
            @PathVariable Long id,
            Authentication authentication
    ) {
        userService.deleteUsuario(id);
    }

}
