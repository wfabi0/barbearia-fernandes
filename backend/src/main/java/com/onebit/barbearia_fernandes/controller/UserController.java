package com.onebit.barbearia_fernandes.controller;

import com.onebit.barbearia_fernandes.model.Usuario;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public String test(){
        return "Funcionando...";
    }

    @PostMapping
    public Usuario salvar(@RequestBody Usuario user){
        return service.save(user);
    }


}
