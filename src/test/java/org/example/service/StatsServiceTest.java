package org.example.service;

import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class StatsServiceTest {
    @Mock
    private DnaRecordRepository dnaRecordRepository;
    @InjectMocks
    private StatsService statsService;
    @Test
    @DisplayName("Calculo y devolucion correcta de estadisticas")
    void testGetStatsWithData(){
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(40L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(100L);
        StatsResponse stats=statsService.obtenerStats();
        assertEquals(40,stats.count_mutant_dna());
        assertEquals(100,stats.count_human_dna());
        assertEquals(0.4,stats.ratio(),0.001);
    }
    @Test
    @DisplayName("Devolucion de estadisticas sin humanos")
    void testGetStatsWithNoHumans(){
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(40L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);
        StatsResponse stats=statsService.obtenerStats();
        assertEquals(40,stats.count_mutant_dna());
        assertEquals(0,stats.count_human_dna());
        assertEquals(40.0,stats.ratio(),0.001);
    }
    @Test
    @DisplayName("Sin datos (ratio 0)")
    void testGetStatsWithNoData(){
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(0L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);
        StatsResponse stats=statsService.obtenerStats();
        assertEquals(0,stats.count_mutant_dna());
        assertEquals(0,stats.count_human_dna());
        assertEquals(0.0,stats.ratio(),0.001);
    }
    @Test
    @DisplayName("Calculo de ratio con decimales")
    void testGetStatsWithDecimalRatio(){
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(11L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(15L);
        StatsResponse stats=statsService.obtenerStats();
        assertEquals(11,stats.count_mutant_dna());
        assertEquals(15,stats.count_human_dna());
        assertEquals(0.733,stats.ratio(),0.001);
    }
    @Test
    @DisplayName("Calculo de ratio con numeros iguales en ambos casos")
    void testGetStatsWithEqualCounts(){
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(5L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(5L);
        StatsResponse stats=statsService.obtenerStats();
        assertEquals(5,stats.count_mutant_dna());
        assertEquals(5,stats.count_human_dna());
        assertEquals(1.0,stats.ratio(),0.001);
    }
    @Test
    @DisplayName("Manejo de gran cantidad de datos")
    void  testGetStatsWithLargeNumbers(){
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(1000000000L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(2000000000L);
        StatsResponse stats=statsService.obtenerStats();
        assertEquals(1000000000,stats.count_mutant_dna());
        assertEquals(2000000000,stats.count_human_dna());
        assertEquals(0.5,stats.ratio(),0.001);
    }
}
