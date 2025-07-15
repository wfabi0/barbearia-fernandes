package com.onebit.barbearia_fernandes.repository;

import com.onebit.barbearia_fernandes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);

    boolean existsByTelefone(String telefone);
}
