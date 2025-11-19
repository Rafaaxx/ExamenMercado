package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final DnaRecordRepository dnaRecordRepository;
    public StatsResponse obtenerStats(){
     long contadorDeMutantes=dnaRecordRepository.countByIsMutant(true);
     long contadorDeHumanos=dnaRecordRepository.countByIsMutant(false);
     double ratio=(contadorDeHumanos==0)? contadorDeMutantes:(double)contadorDeMutantes/contadorDeHumanos;
     return new StatsResponse(contadorDeMutantes,contadorDeHumanos,ratio);
    }
}
