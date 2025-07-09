package com.onebit.barbearia_fernandes.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "agendamentos")
@Entity
public class Agendamento {

    public Agendamento() {
    }

    public Agendamento(Long agendamentoId, Long clienteId, Long barbeiroId, Long tipoCorteId, LocalDateTime dataHora, StatusAgendamento status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.agendamentoId = agendamentoId;
        this.clienteId = clienteId;
        this.barbeiroId = barbeiroId;
        this.tipoCorteId = tipoCorteId;
        this.dataHora = dataHora;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agendamentoId;

    @Column(name = "clienteId", nullable = false)
    private Long clienteId;

    @Column(name = "barbeiroId", nullable = false)
    private Long barbeiroId;

    @Column(name = "tipo_corteId", nullable = false)
    private Long tipoCorteId;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusAgendamento status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Long getAgendamentoId() {
        return agendamentoId;
    }

    public void setAgendamentoId(Long agendamentoId) {
        this.agendamentoId = agendamentoId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getBarbeiroId() {
        return barbeiroId;
    }

    public void setBarbeiroId(Long barbeiroId) {
        this.barbeiroId = barbeiroId;
    }

    public Long getTipoCorteId() {
        return tipoCorteId;
    }

    public void setTipoCorteId(Long tipoCorteId) {
        this.tipoCorteId = tipoCorteId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime data_hora) {
        this.dataHora = data_hora;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
