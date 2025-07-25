package com.onebit.barbearia_fernandes.controller;

import com.onebit.barbearia_fernandes.dto.corte.TipoCorteCreateDto;
import com.onebit.barbearia_fernandes.dto.corte.TipoCorteResponseDto;
import com.onebit.barbearia_fernandes.service.TipoCorteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cortes")
@Tag(
        name = "Cortes",
        description = "Gerencia os tipos de cortes disponíveis na barbearia, permitindo cadastro e listagem."
)
@SecurityRequirement(name = "bearerAuth")
public class TipoCorteController {

    private final TipoCorteService tipoCortesService;

    @PostMapping
    @PreAuthorize("hasRole('BARBEIRO')")
    @Operation(
            summary = "Cadastra um novo tipo de corte",
            description = "Permite o cadastro de um novo tipo de corte com as informações necessárias."
    )
    @ApiResponse(responseCode = "201", description = "Corte cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de validação nos dados do corte")
    @ApiResponse(responseCode = "403", description = "Acesso negado para cadastrar corte")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<TipoCorteResponseDto> cadastrarCorte(
            @Valid @RequestBody TipoCorteCreateDto createDto
    ) {
        TipoCorteResponseDto novoCorte = tipoCortesService.cadastrarCorte(createDto);
        return new ResponseEntity<>(novoCorte, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary = "Lista todos os tipos de cortes",
            description = "Retorna uma lista de todos os tipos de cortes disponíveis na barbearia."
    )
    @ApiResponse(responseCode = "200", description = "Lista de cortes retornada com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<List<TipoCorteResponseDto>> getAllTiposCortes() {
        return ResponseEntity.ok(tipoCortesService.todosCortes());
    }

}
