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
        Locale ptBr = new Locale("pt", "BR");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(ptBr);
        String precoFormatted = numberFormat.format(corte.getPreco());

        return TipoCorteResponseDto.builder()
                .corteId(corte.getCorteId())
                .nomeCorte(corte.getNomeCorte())
                .preco(corte.getPreco())
                .precoFormatted(precoFormatted)
                .build();
    }

}
