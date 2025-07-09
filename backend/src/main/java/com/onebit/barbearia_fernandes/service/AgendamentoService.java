package com.onebit.barbearia_fernandes.service;

import com.onebit.barbearia_fernandes.dto.AgendamentoCreateDto;
import com.onebit.barbearia_fernandes.dto.AgendamentoResponseDto;
import com.onebit.barbearia_fernandes.model.Agendamento;
import com.onebit.barbearia_fernandes.model.StatusAgendamento;
import com.onebit.barbearia_fernandes.repository.AgendamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    @Autowired
    public AgendamentoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    @Transactional
    public AgendamentoResponseDto criarAgendamento(AgendamentoCreateDto createDto) {
        validarDisponibilidade(createDto.barbeiroId(), createDto.data_hora());

        Agendamento agendamento = new Agendamento();
        // TODO: pegar por segurança do security com jwt
        agendamento.setClienteId(createDto.clientId());
        agendamento.setBarbeiroId(createDto.barbeiroId());
        agendamento.setTipoCorteId(createDto.tipoCorteId());
        agendamento.setDataHora(createDto.data_hora());
        agendamento.setStatus(StatusAgendamento.AGENDADO);

        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);
        return toResponseDto(agendamentoSalvo);
    }

    public List<AgendamentoResponseDto> listarTodos() {
        return agendamentoRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public AgendamentoResponseDto buscarPorId(Long id) {
        return toResponseDto(findAgendamentoById(id));
    }

    @Transactional
    public AgendamentoResponseDto atualizarAgendamento(Long agendamentoId, AgendamentoCreateDto updateDto) {
        Agendamento agendamentoExistente = findAgendamentoById(agendamentoId);

        validarDisponibilidade(updateDto.barbeiroId(), updateDto.data_hora(), agendamentoId);

        agendamentoExistente.setClienteId(updateDto.clientId());
        agendamentoExistente.setBarbeiroId(updateDto.barbeiroId());
        agendamentoExistente.setTipoCorteId(updateDto.tipoCorteId());
        agendamentoExistente.setDataHora(updateDto.data_hora());

        Agendamento agendamentoAtualizado = agendamentoRepository.save(agendamentoExistente);
        return toResponseDto(agendamentoAtualizado);
    }

    @Transactional
    public void deletarAgendamento(Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new RuntimeException("Não foi possível encontrar o agendamento com ID: " + id);
        }
        agendamentoRepository.deleteById(id);
    }

    private void validarDisponibilidade(Long barbeiroId, LocalDateTime dataHora, Long... agendamentoIdParaIgnorar) {
        // TODO: relacionar com o tipoCabelo para fazer o calculo (Cabelo, Barba, Pezinho, Colorir, Sobrancelha)
        LocalDateTime inicio = dataHora.minusMinutes(44);
        LocalDateTime fim = dataHora.plusMinutes(44);

        agendamentoRepository.findByBarbeiroIdAndDataHoraBetween(barbeiroId, inicio, fim)
                .stream()
                .filter(agendamento -> agendamentoIdParaIgnorar.length == 0 || !agendamento.getAgendamentoId().equals(agendamentoIdParaIgnorar[0]))
                .findFirst()
                .ifPresent(agendamento -> {
                    throw new RuntimeException("O barbeiro já possui agendamento nesse horário.");
                });
    }

    private Agendamento findAgendamentoById(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado com ID: " + id));
    }

    private AgendamentoResponseDto toResponseDto(Agendamento agendamento) {
        return new AgendamentoResponseDto(
                agendamento.getAgendamentoId(),
                agendamento.getClienteId(),
                agendamento.getBarbeiroId(),
                agendamento.getTipoCorteId(),
                agendamento.getDataHora(),
                agendamento.getStatus(),
                agendamento.getCreatedAt()
        );
    }

}
