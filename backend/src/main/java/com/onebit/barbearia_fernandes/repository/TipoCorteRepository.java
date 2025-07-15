package com.onebit.barbearia_fernandes.repository;

import com.onebit.barbearia_fernandes.model.TipoCorte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoCorteRepository extends JpaRepository<TipoCorte, Long> {
    boolean existsByNomeCorteIgnoreCase(String nome);
}
