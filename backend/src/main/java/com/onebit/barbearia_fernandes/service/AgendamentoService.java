package com.onebit.barbearia_fernandes.service;

import com.onebit.barbearia_fernandes.dto.AgendamentoCreateDto;
import com.onebit.barbearia_fernandes.dto.AgendamentoFilter;
import com.onebit.barbearia_fernandes.dto.AgendamentoPessoalDto;
import com.onebit.barbearia_fernandes.dto.AgendamentoResponseDto;
import com.onebit.barbearia_fernandes.exception.ResourceNotFoundException;
import com.onebit.barbearia_fernandes.model.Agendamento;
import com.onebit.barbearia_fernandes.model.StatusAgendamento;
import com.onebit.barbearia_fernandes.model.Usuario;
import com.onebit.barbearia_fernandes.repository.AgendamentoRepository;
import com.onebit.barbearia_fernandes.repository.UsuarioRepository;
import com.onebit.barbearia_fernandes.repository.specification.AgendamentoSpecification;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public AgendamentoResponseDto criarAgendamento(AgendamentoCreateDto createDto) {

        Usuario cliente = findUsuarioById(createDto.clientId());
        Usuario barbeiro = findUsuarioById(createDto.barbeiroId());

        validarDisponibilidade(barbeiro.getUserId(), createDto.data_hora());

        Agendamento agendamento = new Agendamento();
        // TODO: pegar o id por segurança do security com jwt
        agendamento.setCliente(cliente);
        agendamento.setBarbeiro(barbeiro);
        agendamento.setTipoCorteId(createDto.tipoCorteId());
        agendamento.setDataHora(createDto.data_hora());
        agendamento.setStatus(StatusAgendamento.AGENDADO);

        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);
        return toResponseDto(agendamentoSalvo);
    }

    @Transactional(readOnly = true)
    public Page<AgendamentoResponseDto> listarAgendamentos(AgendamentoFilter filter, Pageable pageable) {
        Specification<Agendamento> spec = AgendamentoSpecification.build(filter);
        Page<Agendamento> agendamentosPage = agendamentoRepository.findAll(spec, pageable);
        return agendamentosPage.map(this::toResponseDto);
    }

    @Transactional(readOnly = true)
    public Page<AgendamentoResponseDto> buscarPorCliente(Long clienteId, AgendamentoPessoalDto filtroCliente, Pageable pageable) {
        AgendamentoFilter filtroCompleto = new AgendamentoFilter(
                filtroCliente.barbeiroId(),
                clienteId,
                filtroCliente.status(),
                filtroCliente.data()
        );
        Specification<Agendamento> spec = AgendamentoSpecification.build(filtroCompleto);
        return agendamentoRepository.findAll(spec, pageable).map(this::toResponseDto);
    }

    public AgendamentoResponseDto buscarPorId(Long id) {
        return toResponseDto(findAgendamentoById(id));
    }

    @Transactional
    public AgendamentoResponseDto atualizarAgendamento(Long agendamentoId, AgendamentoCreateDto updateDto) {
        Agendamento agendamentoExistente = findAgendamentoById(agendamentoId);

        Usuario cliente = findUsuarioById(updateDto.clientId());
        Usuario barbeiro = findUsuarioById(updateDto.barbeiroId());

        validarDisponibilidade(barbeiro.getUserId(), updateDto.data_hora(), agendamentoId);

        agendamentoExistente.setCliente(cliente);
        agendamentoExistente.setBarbeiro(barbeiro);
        agendamentoExistente.setTipoCorteId(updateDto.tipoCorteId());
        agendamentoExistente.setDataHora(updateDto.data_hora());

        Agendamento agendamentoAtualizado = agendamentoRepository.save(agendamentoExistente);
        return toResponseDto(agendamentoAtualizado);
    }

    @Transactional
    public void deletarAgendamento(Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Não foi possível encontrar o agendamento com ID: " + id);
        }
        agendamentoRepository.deleteById(id);
    }

    private void validarDisponibilidade(Long barbeiroId, LocalDateTime dataHora, Long... agendamentoIdParaIgnorar) {
        // TODO: relacionar com o tipoCabelo para fazer o calculo (Cabelo, Barba, Pezinho, Colorir, Sobrancelha)
        LocalDateTime inicio = dataHora.minusMinutes(44);
        LocalDateTime fim = dataHora.plusMinutes(44);

        agendamentoRepository.findByBarbeiro_UserIdAndDataHoraBetween(barbeiroId, inicio, fim)
                .stream()
                .filter(agendamento -> agendamentoIdParaIgnorar.length == 0 || !agendamento.getAgendamentoId().equals(agendamentoIdParaIgnorar[0]))
                .findFirst()
                .ifPresent(agendamento -> {
                    throw new DataIntegrityViolationException("O barbeiro já possui agendamento nesse horário.");
                });
    }

    private Usuario findUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(("Usuário pelo ID " + id + " não encontrado."))
        );
    }

    private Agendamento findAgendamentoById(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com ID: " + id));
    }

    private AgendamentoResponseDto toResponseDto(Agendamento agendamento) {
        return new AgendamentoResponseDto(
                agendamento.getAgendamentoId(),
                agendamento.getCliente().getUserId(),
                agendamento.getBarbeiro().getUserId(),
                agendamento.getTipoCorteId(),
                agendamento.getDataHora(),
                agendamento.getStatus(),
                agendamento.getCreatedAt()
        );
    }

}
