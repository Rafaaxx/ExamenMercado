package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "dna_records",indexes = {
       @Index(name = "idx_hashDna",columnList = "dnaHash"),
        @Index(name = "idx_isMutant",columnList = "isMutant")
})
@Getter
@Setter
@NoArgsConstructor
public class DnaRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "dna_hash", nullable = false, unique = true, length = 64)
    private String dnaHash;
    @Column(nullable = false)
    private boolean isMutant;
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public DnaRecord(String hash, boolean isMutant) {
        this.isMutant = isMutant;
        this.dnaHash =hash;
    }
}
