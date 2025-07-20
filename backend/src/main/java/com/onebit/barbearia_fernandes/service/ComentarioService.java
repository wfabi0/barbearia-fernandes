package com.onebit.barbearia_fernandes.service;

import com.onebit.barbearia_fernandes.dto.comentario.ComentarioCreateDto;
import com.onebit.barbearia_fernandes.dto.comentario.ComentarioResponseDto;
import com.onebit.barbearia_fernandes.dto.user.UserResumeResponseDto;
import com.onebit.barbearia_fernandes.enums.PerfilUsuario;
import com.onebit.barbearia_fernandes.enums.StatusAgendamento;
import com.onebit.barbearia_fernandes.exception.BusinessRuleException;
import com.onebit.barbearia_fernandes.exception.ResourceNotFoundException;
import com.onebit.barbearia_fernandes.model.Agendamento;
import com.onebit.barbearia_fernandes.model.Comentario;
import com.onebit.barbearia_fernandes.model.Usuario;
import com.onebit.barbearia_fernandes.repository.AgendamentoRepository;
import com.onebit.barbearia_fernandes.repository.ComentarioRepository;
import com.onebit.barbearia_fernandes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public ComentarioResponseDto criarComentario(
            ComentarioCreateDto dto,
            Authentication authentication
    ) {
        String emailCliente = authentication.getName();
        Usuario cliente = findUsuarioByEmail(emailCliente);

        Agendamento agendamento = findAgendamentoById(dto.agendamentoId());

        if (!Objects.equals(agendamento.getCliente().getUserId(), cliente.getUserId())) {
            throw new BusinessRuleException("Você só pode comentar em seus próprios agendamentos.");
        }

        if (agendamento.getStatus() != StatusAgendamento.CONCLUIDO) {
            throw new BusinessRuleException("Só é possível avaliar agendamentos concluídos.");
        }

        if (comentarioRepository.existsByAgendamento_AgendamentoId(dto.agendamentoId())) {
            throw new BusinessRuleException("Este agendamento já foi avaliado.");
        }

        Comentario comentario = new Comentario();
        comentario.setDescricao(dto.descricao());
        comentario.setNota(dto.nota());
        comentario.setAgendamento(agendamento);
        comentario.setCliente(agendamento.getCliente());
        comentario.setBarbeiro(agendamento.getBarbeiro());

        Comentario novoComentario = comentarioRepository.save(comentario);

        return toResponseDto(novoComentario);
    }

    @Transactional(readOnly = true)
    public Page<ComentarioResponseDto> listarComentariosPorAutenticacao(Authentication authentication, Pageable pageable) {
        String emailClienteLogado = authentication.getName();
        Usuario usuarioLogado = findUsuarioByEmail(emailClienteLogado);

        if (usuarioLogado.getPerfil().equals(PerfilUsuario.BARBEIRO)) {
            return listarPorBarbeiro(usuarioLogado.getUserId(), pageable);
        }

        Page<Comentario> comentarios = comentarioRepository.findByCliente_UserId(usuarioLogado.getUserId(), pageable);

        return comentarios.map(this::toResponseDto);
    }

    private ComentarioResponseDto toResponseDto(Comentario comentario) {
        return new ComentarioResponseDto(
                comentario.getComentarioId(),
                comentario.getAgendamento().getAgendamentoId(),
                new UserResumeResponseDto(
                        comentario.getCliente().getUserId(),
                        comentario.getCliente().getNomeUsuario()
                ),
                new UserResumeResponseDto(
                        comentario.getBarbeiro().getUserId(),
                        comentario.getBarbeiro().getNomeUsuario()

                ),
                comentario.getNota(),
                comentario.getDescricao(),
                comentario.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public Page<ComentarioResponseDto> listarPorBarbeiro(Long barbeiroId, Pageable pageable) {
        Usuario barbeiro = usuarioRepository.findById(barbeiroId)
                .orElseThrow(() -> new ResourceNotFoundException("Barbeiro não encontrado com o ID: " + barbeiroId));

        if (!barbeiro.getPerfil().equals(PerfilUsuario.BARBEIRO)) {
            throw new BusinessRuleException("O usuário informado não é um barbeiro.");
        }

        Page<Comentario> comentarios = comentarioRepository.findByBarbeiro_UserId(barbeiroId, pageable);
        return comentarios.map(this::toResponseDto);
    }

    @Transactional
    public void deletarComentario(Long comentarioId, Authentication authentication) {
        if (comentarioId == null) {
            throw new BusinessRuleException("O ID do comentário não pode ser nulo.");
        }
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessRuleException("Usuário não autenticado.");
        }

        String emailClienteLogado = authentication.getName();
        Usuario usuarioLogado = findUsuarioByEmail(emailClienteLogado);

        Comentario comentario = findComentarioById(comentarioId);

        boolean isOwner = Objects.equals(comentario.getCliente().getUserId(), usuarioLogado.getUserId());
        boolean isAdmin = usuarioLogado.getPerfil().equals(PerfilUsuario.BARBEIRO);

        if (!isOwner && !isAdmin) {
            throw new BusinessRuleException("Você não tem permissão para deletar este comentário.");
        }

        comentarioRepository.delete(comentario);
    }

    private Agendamento findAgendamentoById(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado com o ID: " + id));
    }

    private Usuario findUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com email: " + email));
    }

    private Comentario findComentarioById(Long id) {
        return comentarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comentário não encontrado com o ID: " + id));
    }


}
