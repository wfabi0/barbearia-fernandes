package com.onebit.barbearia_fernandes.controller;

import com.onebit.barbearia_fernandes.dto.agendamento.AgendamentoCreateDto;
import com.onebit.barbearia_fernandes.dto.agendamento.AgendamentoFilter;
import com.onebit.barbearia_fernandes.dto.agendamento.AgendamentoPessoalDto;
import com.onebit.barbearia_fernandes.dto.agendamento.AgendamentoResponseDto;
import com.onebit.barbearia_fernandes.service.AgendamentoService;
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
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoResponseDto> criarAgendamento(
            @Valid @RequestBody AgendamentoCreateDto createDto,
            Authentication authentication
    ) {
        AgendamentoResponseDto novoAgendamento = agendamentoService.criarAgendamento(createDto, authentication);
        return new ResponseEntity<>(novoAgendamento, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('BARBEIRO')")
    public ResponseEntity<Page<AgendamentoResponseDto>> listarAgendamentos(
            AgendamentoFilter filter,
            @PageableDefault(size = 10, sort = "dataHora") Pageable pageable
    ) {
        Page<AgendamentoResponseDto> agendamentosPage = agendamentoService.listarAgendamentos(filter, pageable);
        return ResponseEntity.ok(agendamentosPage);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('BARBEIRO')")
    public ResponseEntity<AgendamentoResponseDto> buscarAgendamentoPorId(@PathVariable Long id) {
        AgendamentoResponseDto agendamento = agendamentoService.buscarPorId(id);
        return ResponseEntity.ok(agendamento);
    }

    @PutMapping("/{id}")
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
    @PreAuthorize("hasRole('BARBEIRO')")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable Long id) {
        agendamentoService.deletarAgendamento(id);
        return ResponseEntity.noContent().build();
    }

    // Rotas /me
    @GetMapping("/me")
    public ResponseEntity<Page<AgendamentoResponseDto>> buscarMeusAgendamentos(
            AgendamentoPessoalDto filtro,
            @PageableDefault(size = 10, sort = "dataHora") Pageable pageable,
            Authentication authentication
    ) {
        Page<AgendamentoResponseDto> agendamentosPage = agendamentoService.buscarPorIdCliente(filtro, pageable, authentication);
        return ResponseEntity.ok(agendamentosPage);
    }

}
