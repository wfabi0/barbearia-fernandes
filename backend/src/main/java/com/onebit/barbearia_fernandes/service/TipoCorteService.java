package com.onebit.barbearia_fernandes.service;

import com.onebit.barbearia_fernandes.dto.corte.TipoCorteCreateDto;
import com.onebit.barbearia_fernandes.dto.corte.TipoCorteResponseDto;
import com.onebit.barbearia_fernandes.model.TipoCorte;
import com.onebit.barbearia_fernandes.repository.TipoCorteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class TipoCorteService {

    private final TipoCorteRepository tipoCorteRepository;
    private static Locale PT_BR = new Locale("pt", "BR");
    private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(PT_BR);

    @Transactional
    public TipoCorteResponseDto cadastrarCorte(TipoCorteCreateDto createDto) {
        if (tipoCorteRepository.existsByNomeCorteIgnoreCase(createDto.nome())) {
            throw new DataIntegrityViolationException("Já existe um corte cadastrado com o nome: " + createDto.nome());
        }

        TipoCorte corte = new TipoCorte();
        corte.setNomeCorte(createDto.nome());
        corte.setPreco(createDto.preco());

        TipoCorte corteSalvo = tipoCorteRepository.save(corte);
        return toResponseDto(corteSalvo);
    }

    @Transactional(readOnly = true)
    public List<TipoCorteResponseDto> todosCortes() {
        List<TipoCorte> cortes = tipoCorteRepository.findAll();
        return cortes.stream()
                .map(this::toResponseDto)
                .toList();
    }

    private TipoCorteResponseDto toResponseDto(TipoCorte corte) {
        String precoFormatted = CURRENCY_FORMATTER.format(corte.getPreco());

        return TipoCorteResponseDto.builder()
                .corteId(corte.getCorteId())
                .nomeCorte(corte.getNomeCorte())
                .preco(corte.getPreco())
                .precoFormatted(precoFormatted)
                .build();
    }

}
