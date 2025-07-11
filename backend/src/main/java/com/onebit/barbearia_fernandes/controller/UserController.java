package com.onebit.barbearia_fernandes.controller;

import com.onebit.barbearia_fernandes.dto.CreateUserDto;
import com.onebit.barbearia_fernandes.dto.UserResponseDto;
import com.onebit.barbearia_fernandes.model.Usuario;
import com.onebit.barbearia_fernandes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDto saveUsuario(@RequestBody CreateUserDto createUserDto) {
        return userService.saveUsuario(createUserDto);
    }

}
