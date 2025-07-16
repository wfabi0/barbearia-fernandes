package com.onebit.barbearia_fernandes.service;

import com.onebit.barbearia_fernandes.dto.agendamento.AgendamentoCreateDto;
import com.onebit.barbearia_fernandes.dto.agendamento.AgendamentoFilter;
import com.onebit.barbearia_fernandes.dto.agendamento.AgendamentoPessoalDto;
import com.onebit.barbearia_fernandes.dto.agendamento.AgendamentoResponseDto;
import com.onebit.barbearia_fernandes.enums.PerfilUsuario;
import com.onebit.barbearia_fernandes.enums.StatusAgendamento;
import com.onebit.barbearia_fernandes.exception.ResourceNotFoundException;
import com.onebit.barbearia_fernandes.model.Agendamento;
import com.onebit.barbearia_fernandes.model.TipoCorte;
import com.onebit.barbearia_fernandes.model.Usuario;
import com.onebit.barbearia_fernandes.repository.AgendamentoRepository;
import com.onebit.barbearia_fernandes.repository.TipoCorteRepository;
import com.onebit.barbearia_fernandes.repository.UsuarioRepository;
import com.onebit.barbearia_fernandes.repository.specification.AgendamentoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TipoCorteRepository tipoCorteRepository;

    @Transactional
    public AgendamentoResponseDto criarAgendamento(
            AgendamentoCreateDto createDto,
            Authentication authentication
    ) {

        String email = authentication.getName();

        Usuario cliente = findUsuarioByEmail(email);
        Usuario barbeiro = findUsuarioById(createDto.barbeiroId());

        if (barbeiro.getPerfil() != PerfilUsuario.BARBEIRO) {
            throw new ResourceNotFoundException("Usuário " + barbeiro.getUserId() + " não é um barbeiro.");
        }

        if (Objects.equals(cliente.getUserId(), barbeiro.getUserId())) {
            throw new DataIntegrityViolationException("Cliente e barbeiro não podem ser o mesmo usuário.");
        }

        TipoCorte tipoCorte = findTipoCorteById(createDto.tipoCorteId());

        validarDisponibilidade(barbeiro.getUserId(), createDto.data_hora());

        Agendamento agendamento = new Agendamento();
        agendamento.setCliente(cliente);
        agendamento.setBarbeiro(barbeiro);
        agendamento.setTipoCorte(tipoCorte);
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
    public Page<AgendamentoResponseDto> buscarPorIdCliente(Long clienteId, AgendamentoPessoalDto filtroCliente, Pageable pageable) {
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
    public AgendamentoResponseDto atualizarAgendamento(
            Long agendamentoId, AgendamentoCreateDto updateDto,
            Authentication authentication
    ) {
        Agendamento agendamentoExistente = findAgendamentoById(agendamentoId);

        String email = authentication.getName();

        Usuario cliente = findUsuarioByEmail(email);
        Usuario barbeiro = findUsuarioById(updateDto.barbeiroId());
        TipoCorte tipoCorte = findTipoCorteById(updateDto.tipoCorteId());

        validarDisponibilidade(barbeiro.getUserId(), updateDto.data_hora(), agendamentoId);

        agendamentoExistente.setCliente(cliente);
        agendamentoExistente.setBarbeiro(barbeiro);
        agendamentoExistente.setTipoCorte(tipoCorte);
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

    @Transactional(readOnly = true)
    private TipoCorte findTipoCorteById(Long tipoCorteId) {
        return tipoCorteRepository.findById(tipoCorteId)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de corte não encontrado com ID: " + tipoCorteId));
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    private Usuario findUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(("Usuário pelo ID " + id + " não encontrado."))
        );
    }

    @Transactional(readOnly = true)
    private Usuario findUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com email: " + email));
    }

    @Transactional(readOnly = true)
    private Agendamento findAgendamentoById(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com ID: " + id));
    }

    private AgendamentoResponseDto toResponseDto(Agendamento agendamento) {
        return new AgendamentoResponseDto(
                agendamento.getAgendamentoId(),
                agendamento.getCliente().getUserId(),
                agendamento.getBarbeiro().getUserId(),
                agendamento.getTipoCorte().getCorteId(),
                agendamento.getDataHora(),
                agendamento.getStatus(),
                agendamento.getCreatedAt()
        );
    }

}
