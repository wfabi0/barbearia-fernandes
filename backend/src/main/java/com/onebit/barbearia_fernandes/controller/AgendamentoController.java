package com.onebit.barbearia_fernandes.controller;

import com.onebit.barbearia_fernandes.dto.agendamento.AgendamentoCreateDto;
import com.onebit.barbearia_fernandes.dto.agendamento.AgendamentoFilter;
import com.onebit.barbearia_fernandes.dto.agendamento.AgendamentoPessoalDto;
import com.onebit.barbearia_fernandes.dto.agendamento.AgendamentoResponseDto;
import com.onebit.barbearia_fernandes.service.AgendamentoService;
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
@RequestMapping("/api/agendamentos")
@Tag(
        name = "Agendamentos",
        description = "Endpoints para gerenciamento de agendamentos, incluindo criação, listagem, atualização e exclusão."
)
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    @Operation(summary = "Criar Agendamento", description = "Cria um novo agendamento com os dados fornecidos. O usuário deve estar autenticado.")
    @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso.")
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos.")
    @ApiResponse(responseCode = "401", description = "Usuário não autenticado.")
    @ApiResponse(responseCode = "403", description = "Usuário não autorizado a criar agendamentos.")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao criar agendamento.")
    public ResponseEntity<AgendamentoResponseDto> criarAgendamento(
            @Valid @RequestBody AgendamentoCreateDto createDto,
            Authentication authentication
    ) {
        AgendamentoResponseDto novoAgendamento = agendamentoService.criarAgendamento(createDto, authentication);
        return new ResponseEntity<>(novoAgendamento, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar Agendamentos", description = "Lista todos os agendamentos com base nos filtros fornecidos.")
    @ApiResponse(responseCode = "200", description = "Lista de agendamentos retornada com sucesso.")
    @ApiResponse(responseCode = "400", description = "Filtros inválidos fornecidos.")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao listar agendamentos.")
    @PreAuthorize("hasRole('BARBEIRO')")
    public ResponseEntity<Page<AgendamentoResponseDto>> listarAgendamentos(
            AgendamentoFilter filter,
            @PageableDefault(size = 10, sort = "dataHora") Pageable pageable
    ) {
        Page<AgendamentoResponseDto> agendamentosPage = agendamentoService.listarAgendamentos(filter, pageable);
        return ResponseEntity.ok(agendamentosPage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Agendamento por ID", description = "Busca um agendamento específico pelo ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Agendamento encontrado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Agendamento não encontrado.")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao buscar agendamento.")
    @PreAuthorize("hasRole('BARBEIRO')")
    public ResponseEntity<AgendamentoResponseDto> buscarAgendamentoPorId(@PathVariable Long id) {
        AgendamentoResponseDto agendamento = agendamentoService.buscarPorId(id);
        return ResponseEntity.ok(agendamento);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Agendamento", description = "Atualiza um agendamento existente com os dados fornecidos.")
    @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso.")
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos.")
    @ApiResponse(responseCode = "404", description = "Agendamento não encontrado.")
    @ApiResponse(responseCode = "401", description = "Usuário não autenticado.")
    @PreAuthorize("hasRole('BARBEIRO')")
    public ResponseEntity<AgendamentoResponseDto> atualizarAgendamento(
            @PathVariable Long id,
            @Valid @RequestBody AgendamentoCreateDto updateDto,
            Authentication authentication
    ) {
        AgendamentoResponseDto agendamentoAtualizado = agendamentoService.atualizarAgendamento(id, updateDto, authentication);
        return ResponseEntity.ok(agendamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar Agendamento", description = "Deleta um agendamento existente pelo ID fornecido.")
    @ApiResponse(responseCode = "204", description = "Agendamento deletado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Agendamento não encontrado.")
    @ApiResponse(responseCode = "401", description = "Usuário não autenticado.")
    @ApiResponse(responseCode = "403", description = "Usuário não autorizado a deletar agendamentos.")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao deletar agendamento.")
    @PreAuthorize("hasRole('BARBEIRO')")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable Long id) {
        agendamentoService.deletarAgendamento(id);
        return ResponseEntity.noContent().build();
    }

    // Rotas /me
    @GetMapping("/me")
    @Operation(summary = "Buscar Meus Agendamentos", description = "Busca os agendamentos do usuário autenticado. O usuário deve estar autenticado.")
    @ApiResponse(responseCode = "200", description = "Lista de agendamentos do usuário autenticado.")
    @ApiResponse(responseCode = "401", description = "Usuário não autenticado.")
    @ApiResponse(responseCode = "403", description = "Usuário não autorizado a acessar seus agendamentos.")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao buscar agendamentos.")
    public ResponseEntity<Page<AgendamentoResponseDto>> buscarMeusAgendamentos(
            AgendamentoPessoalDto filtro,
            @PageableDefault(size = 10, sort = "dataHora") Pageable pageable,
            Authentication authentication
    ) {
        Page<AgendamentoResponseDto> agendamentosPage = agendamentoService.buscarPorIdCliente(filtro, pageable, authentication);
        return ResponseEntity.ok(agendamentosPage);
    }

}
