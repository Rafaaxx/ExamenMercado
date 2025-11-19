package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MutantServiceTest {
    @Mock
    private MutantDetector mutantDetector;
    @Mock
    private DnaRecordRepository dnaRecordRepository;
    @InjectMocks
    private MutantService mutantService;

    @Test
    @DisplayName("Analizar dna mutante y guardarlo en la base de datos")
    void testAnalyzeMutantDnaAndSave(){
        String[] dna = {"AAAA", "CAGT", "TTAT", "AGGA"};
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dna)).thenReturn(true);
        when(dnaRecordRepository.save(any(DnaRecord.class))).thenReturn(new DnaRecord());
        boolean resultado=mutantService.analizarYPersistir(dna);
        assertTrue(resultado);
        verify(mutantDetector,times(1)).isMutant(dna);
        verify(dnaRecordRepository,times(1)).save(any(DnaRecord.class));
    }
    @Test
    @DisplayName("Analizar dna humano y guardarlo en la base de datos")
    void testAnalyzeHumanDnaAndSave(){
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGGG"};
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dna)).thenReturn(false);
        when(dnaRecordRepository.save(any(DnaRecord.class))).thenReturn(new DnaRecord());
        boolean resultado=mutantService.analizarYPersistir(dna);
        assertFalse(resultado);
        verify(mutantDetector,times(1)).isMutant(dna);
        verify(dnaRecordRepository,times(1)).save(any(DnaRecord.class));
    }
    @Test
    @DisplayName("Retorno de resultado cacheado (el dna ya fue analizado)")
    void  testReturnCachedResultForAnalyzedDna(){
        String[] dna = {"AAAA", "CAGT", "TTAT", "AGGA"};
        DnaRecord cachedRecord = new DnaRecord("somehash", true);
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.of(cachedRecord));
        boolean resultado=mutantService.analizarYPersistir(dna);
        assertTrue(resultado);
        verify(mutantDetector,never()).isMutant(dna);
        verify(dnaRecordRepository,never()).save(any(DnaRecord.class));
    }
    @Test
    @DisplayName("Generacion del mismo hash para el mismo dna")
    void testConsistentHashGeneration(){
        String[] dna = {"AAAA", "CAGT", "TTAT", "AGGA"};
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dna)).thenReturn(true);
        boolean resultado=mutantService.analizarYPersistir(dna);
        boolean resultado2=mutantService.analizarYPersistir(dna);
        verify(dnaRecordRepository,times(2)).findByDnaHash(anyString());
    }
    @Test
    @DisplayName("Confeccion del registro con el hash correcto")
    void  testSavesRecordWithCorrectHash(){
        String[] dna = {"AAAA", "CAGT", "TTAT", "AGGA"};
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dna)).thenReturn(true);
        mutantService.analizarYPersistir(dna);
        verify(dnaRecordRepository).save(argThat(dnaRecord ->
                dnaRecord.getDnaHash()!=null &&
                dnaRecord.getDnaHash().length()==64 &&
                dnaRecord.isMutant()));
    }
}
