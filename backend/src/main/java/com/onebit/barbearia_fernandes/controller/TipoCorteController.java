package com.onebit.barbearia_fernandes.controller;

import com.onebit.barbearia_fernandes.dto.corte.TipoCorteCreateDto;
import com.onebit.barbearia_fernandes.dto.corte.TipoCorteResponseDto;
import com.onebit.barbearia_fernandes.service.TipoCorteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cortes")
public class TipoCorteController {

    private final TipoCorteService tipoCortesService;

    @PostMapping
    @PreAuthorize("hasRole('BARBEIRO')")
    public ResponseEntity<TipoCorteResponseDto> cadastrarCorte(
            @Valid @RequestBody TipoCorteCreateDto createDto
    ) {
        TipoCorteResponseDto novoCorte = tipoCortesService.cadastrarCorte(createDto);
        return new ResponseEntity<>(novoCorte, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TipoCorteResponseDto>> getAllTiposCortes() {
        return ResponseEntity.ok(tipoCortesService.todosCortes());
    }

}
