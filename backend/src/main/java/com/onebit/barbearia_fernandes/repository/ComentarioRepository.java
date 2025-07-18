package com.onebit.barbearia_fernandes.repository;

import com.onebit.barbearia_fernandes.model.Comentario;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    boolean existsByAgendamento_AgendamentoId(
            @NotNull(message = "O ID do agendamento não pode estar em branco.")
            Long agendamentoId
    );

    Page<Comentario> findByBarbeiro_UserId(Long barbeiroId, Pageable pageable);

    Page<Comentario> findByCliente_UserId(Long userId, Pageable pageable);
}
