package com.onebit.barbearia_fernandes.controller;

import com.onebit.barbearia_fernandes.dto.AgendamentoCreateDto;
import com.onebit.barbearia_fernandes.dto.AgendamentoFilter;
import com.onebit.barbearia_fernandes.dto.AgendamentoPessoalDto;
import com.onebit.barbearia_fernandes.dto.AgendamentoResponseDto;
import com.onebit.barbearia_fernandes.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @Autowired
    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @PostMapping
    public ResponseEntity<AgendamentoResponseDto> criarAgendamento(@Valid @RequestBody AgendamentoCreateDto createDto) {
        AgendamentoResponseDto novoAgendamento = agendamentoService.criarAgendamento(createDto);
        return new ResponseEntity<>(novoAgendamento, HttpStatus.CREATED);
    }

    // TODO: precisa finalizar a autenticaçao/crud usuario
    @GetMapping("/me")
    public ResponseEntity<Page<AgendamentoResponseDto>> buscarMeusAgendamentos(
            AgendamentoPessoalDto filtro,
            @PageableDefault(size = 10, sort = "dataHora") Pageable pageable) {

        Long clienteId = 1L;
        Page<AgendamentoResponseDto> agendamentosPage = agendamentoService.buscarPorIdCliente(clienteId, filtro, pageable);
        return ResponseEntity.ok(agendamentosPage);
    }

    @GetMapping
    public ResponseEntity<Page<AgendamentoResponseDto>> listarAgendamentos(
            AgendamentoFilter filter,
            @PageableDefault(size = 10, sort = "dataHora") Pageable pageable
    ) {
        Page<AgendamentoResponseDto> agendamentosPage = agendamentoService.listarAgendamentos(filter, pageable);
        return ResponseEntity.ok(agendamentosPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDto> buscarAgendamentoPorId(@PathVariable Long id) {
        AgendamentoResponseDto agendamento = agendamentoService.buscarPorId(id);
        return ResponseEntity.ok(agendamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDto> atualizarAgendamento(@PathVariable Long id, @Valid @RequestBody AgendamentoCreateDto updateDto) {
        AgendamentoResponseDto agendamentoAtualizado = agendamentoService.atualizarAgendamento(id, updateDto);
        return ResponseEntity.ok(agendamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable Long id) {
        agendamentoService.deletarAgendamento(id);
        return ResponseEntity.noContent().build();
    }

}
