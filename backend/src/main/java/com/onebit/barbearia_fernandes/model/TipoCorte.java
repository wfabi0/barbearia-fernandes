package com.onebit.barbearia_fernandes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "tipo_corte")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoCorte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long corteId;

    @Column(name = "nome_corte", nullable = false, unique = true)
    private String nomeCorte;

    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
