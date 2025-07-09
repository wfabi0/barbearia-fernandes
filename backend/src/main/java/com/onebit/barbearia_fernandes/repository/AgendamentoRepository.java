package com.onebit.barbearia_fernandes.repository;

import com.onebit.barbearia_fernandes.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    Optional<Agendamento> findByBarbeiroIdAndDataHoraBetween(Long barbeiroId, LocalDateTime start, LocalDateTime end);
}
