package org.example.service;


import lombok.RequiredArgsConstructor;
import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final DnaRecordRepository dnaRecordRepository;
    @Cacheable("stats")
    public StatsResponse obtenerStats(){
     long contadorDeMutantes=dnaRecordRepository.countByIsMutant(true);
     long contadorDeHumanos=dnaRecordRepository.countByIsMutant(false);
     double ratio=(contadorDeHumanos==0)? contadorDeMutantes:(double)contadorDeMutantes/contadorDeHumanos;
     return new StatsResponse(contadorDeMutantes,contadorDeHumanos,ratio);
    }
    public StatsResponse obtenerStatsFiltrado(LocalDateTime start,LocalDateTime end) {

        long contadormutantes = dnaRecordRepository.countByIsMutantAndCreatedAtBetween(true, start, end);
        long contadorhumanos = dnaRecordRepository.countByIsMutantAndCreatedAtBetween(false, start, end);

        double ratio = contadorhumanos == 0 ? contadormutantes : (double) contadormutantes / contadorhumanos;
        StatsResponse statsResponse=new StatsResponse(contadormutantes,contadorhumanos,ratio);
        return statsResponse;
    }
}
