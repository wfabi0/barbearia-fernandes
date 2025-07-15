package com.onebit.barbearia_fernandes.model;

import com.onebit.barbearia_fernandes.enums.StatusAgendamento;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "agendamento")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "agendamentoId")
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

    @ManyToOne
    @JoinColumn(name = "tipo_corte_id", referencedColumnName = "corteId", nullable = false)
    private TipoCorte tipoCorte;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusAgendamento status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
