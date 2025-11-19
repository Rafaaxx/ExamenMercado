package org.example.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.DnaRecord;
import org.example.exception.DnaHashCalculationException;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class MutantService {
    private final MutantDetector mutantDetector;
    private final DnaRecordRepository dnaRecordRepository;
    @Transactional
    public boolean analizarYPersistir(String[] dna){
        String hash=generarHash(dna);
        Optional<DnaRecord>existe=dnaRecordRepository.findByDnaHash(hash);
        if (existe.isEmpty()){
            boolean esMutante=mutantDetector.isMutant(dna);
            DnaRecord dnaRecord=new DnaRecord(hash,esMutante);
            dnaRecordRepository.save(dnaRecord);
            return esMutante;
        }else {
            log.info("El dna ya fue analizado, el resultado es: {}",existe.get().isMutant());
            return existe.get().isMutant();
        }
    }
    private String generarHash(String[] dna){
        try{
            MessageDigest messageDigest=MessageDigest.getInstance("SHA-256");
            byte[] hashBytes=messageDigest.digest(String.join(",", dna).toUpperCase().getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
        throw new DnaHashCalculationException("Error al generar el hash del dna", e);
    }
    }

}
