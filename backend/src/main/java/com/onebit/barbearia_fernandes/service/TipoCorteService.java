package com.onebit.barbearia_fernandes.service;

import com.onebit.barbearia_fernandes.dto.corte.TipoCorteCreateDto;
import com.onebit.barbearia_fernandes.dto.corte.TipoCorteResponseDto;
import com.onebit.barbearia_fernandes.mapper.TipoCorteMapper;
import com.onebit.barbearia_fernandes.model.TipoCorte;
import com.onebit.barbearia_fernandes.repository.TipoCorteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TipoCorteService {

    private final TipoCorteRepository tipoCorteRepository;
    private final TipoCorteMapper tipoCorteMapper;

    @Transactional
    public TipoCorteResponseDto cadastrarCorte(TipoCorteCreateDto createDto) {
        if (tipoCorteRepository.existsByNomeCorteIgnoreCase(createDto.nome())) {
            throw new DataIntegrityViolationException("Já existe um corte cadastrado com o nome: " + createDto.nome());
        }

        TipoCorte corte = new TipoCorte();
        corte.setNomeCorte(createDto.nome());
        corte.setPreco(createDto.preco());

        TipoCorte corteSalvo = tipoCorteRepository.save(corte);
        return tipoCorteMapper.toResponseDto(corteSalvo);
    }

    @Transactional(readOnly = true)
    public List<TipoCorteResponseDto> todosCortes() {
        List<TipoCorte> cortes = tipoCorteRepository.findAll();
        return cortes.stream()
                .map(tipoCorteMapper::toResponseDto)
                .toList();
    }

}
