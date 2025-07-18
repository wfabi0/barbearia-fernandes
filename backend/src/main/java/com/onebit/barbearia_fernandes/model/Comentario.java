package com.onebit.barbearia_fernandes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Table(name = "comentario")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "comentarioId")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comentarioId;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "userId", nullable = false)
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "barbeiro_id", referencedColumnName = "userId", nullable = false)
    private Usuario barbeiro;

    @OneToOne
    @JoinColumn(name = "agendamento_id", referencedColumnName = "agendamentoId", nullable = false, unique = true)
    private Agendamento agendamento;

    @Column(name = "nota", nullable = false)
    @Min(0)
    @Max(10)
    private Integer nota;

    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
