package com.onebit.barbearia_fernandes.controller;

import com.onebit.barbearia_fernandes.model.Usuario;
import com.onebit.barbearia_fernandes.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDto saveUsuario(@RequestBody CreateUserDto createUserDto){
        return userService.saveUsuario(createUserDto);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<Usuario> getUserById(@PathVariable("userId") String userId){
        //
        return null;
    }
}
