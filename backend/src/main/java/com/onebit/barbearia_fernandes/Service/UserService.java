package com.onebit.barbearia_fernandes.Service;

import com.onebit.barbearia_fernandes.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public Usuario salvar(Usuario user) {
        return repository.save(user);
    }

    // Você pode colocar aqui depois: listar(), atualizar(), deletar()
}
