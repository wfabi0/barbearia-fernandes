package com.onebit.barbearia_fernandes.controller;

import com.onebit.barbearia_fernandes.dto.comentario.ComentarioCreateDto;
import com.onebit.barbearia_fernandes.dto.comentario.ComentarioReponseDto;
import com.onebit.barbearia_fernandes.service.ComentarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comentarios")
@Tag(
        name = "Comentários",
        description = "Endpoints para gerenciamento de comentários, incluindo criação, listagem e exclusão."
)
public class ComentarioController {

    private final ComentarioService comentarioService;

    @PostMapping
    @Operation(summary = "Criar Comentário", description = "Cria um novo comentário para um agendamento concluído. O usuário deve estar autenticado como CLIENTE.")
    @ApiResponse(responseCode = "201", description = "Comentário criado com sucesso.")
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos.")
    @ApiResponse(responseCode = "401", description = "Usuário não autenticado.")
    @ApiResponse(responseCode = "403", description = "Usuário não autorizado a criar comentários.")
    @ApiResponse(responseCode = "404", description = "Agendamento não encontrado ou não concluído.")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao criar comentário.")
    public ResponseEntity<ComentarioReponseDto> criarComentario(
            @Valid @RequestBody ComentarioCreateDto dto,
            Authentication authentication
    ) {
        ComentarioReponseDto response = comentarioService.criarComentario(dto, authentication);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar Comentários", description = "Lista todos os comentários feitos por um usuário autenticado. O usuário deve estar autenticado como CLIENTE.")
    @ApiResponse(responseCode = "200", description = "Lista de comentários retornada com sucesso.")
    @ApiResponse(responseCode = "401", description = "Usuário não autenticado.")
    @ApiResponse(responseCode = "403", description = "Usuário não autorizado a listar comentários.")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao listar comentários.")
    public ResponseEntity<Page<ComentarioReponseDto>> listarComentarios(
            @PageableDefault(size = 10) Pageable pageable,
            Authentication authentication
    ) {
        Page<ComentarioReponseDto> comentarios = comentarioService.listarComentariosPorAutenticacao(authentication, pageable);
        return ResponseEntity.ok(comentarios);
    }


}
