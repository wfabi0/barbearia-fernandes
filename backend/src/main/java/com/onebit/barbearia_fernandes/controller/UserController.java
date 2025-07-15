package com.onebit.barbearia_fernandes.controller;

import com.onebit.barbearia_fernandes.dto.CreateUserDto;
import com.onebit.barbearia_fernandes.dto.UserResponseDto;
import com.onebit.barbearia_fernandes.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponseDto saveUsuario(@Valid @RequestBody CreateUserDto createUserDto) {
        return userService.saveUsuario(createUserDto);
    }

}
