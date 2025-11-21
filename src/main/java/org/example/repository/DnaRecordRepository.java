package org.example.repository;

import org.example.entity.DnaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
@Repository
public interface DnaRecordRepository extends JpaRepository<DnaRecord,Long> {
    Optional<DnaRecord>findByDnaHash(String dnaHash);
    long countByIsMutant(boolean isMutant);
    long countByIsMutantAndCreatedAtBetween(boolean isMutant, LocalDateTime start, LocalDateTime end);
}
