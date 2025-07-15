package com.onebit.barbearia_fernandes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "agendamento")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agendamentoId;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "userId", nullable = false)
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "barbeiro_id", referencedColumnName = "userId", nullable = false)
    private Usuario barbeiro;

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

}
