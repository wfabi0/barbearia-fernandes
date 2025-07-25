package com.onebit.barbearia_fernandes.repository;

import com.onebit.barbearia_fernandes.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>, JpaSpecificationExecutor<Agendamento> {
    List<Agendamento> findByBarbeiro_UserIdAndDataHoraBetween(Long barbeiroId, LocalDateTime start, LocalDateTime end);
}
