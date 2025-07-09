package com.onebit.barbearia_fernandes.controller;

import com.onebit.barbearia_fernandes.dto.AgendamentoCreateDto;
import com.onebit.barbearia_fernandes.dto.AgendamentoResponseDto;
import com.onebit.barbearia_fernandes.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDto>> listarAgendamentos() {
        List<AgendamentoResponseDto> agendamentos = agendamentoService.listarTodos();
        return ResponseEntity.ok(agendamentos);
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
