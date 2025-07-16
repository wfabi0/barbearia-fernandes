package com.onebit.barbearia_fernandes.controller;

import com.onebit.barbearia_fernandes.dto.auth.LoginRequestDto;
import com.onebit.barbearia_fernandes.dto.auth.LoginResponseDto;
import com.onebit.barbearia_fernandes.dto.auth.RegisterReponseDto;
import com.onebit.barbearia_fernandes.dto.auth.RegisterRequestDto;
import com.onebit.barbearia_fernandes.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterReponseDto> register(@Valid @RequestBody RegisterRequestDto requestDto) {
        return ResponseEntity.ok(authService.register(requestDto));
    }

}
