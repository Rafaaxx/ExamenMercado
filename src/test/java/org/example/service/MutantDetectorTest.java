package org.example.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MutantDetectorTest {
    private final MutantDetector mutantDetector=new MutantDetector();
    @Test
    public void testNullArray(){
        assertFalse(mutantDetector.isMutant(null));
    }
    @Test
    public void testDnaArrayVacio(){
        String[] dna={};
        assertFalse(mutantDetector.isMutant(dna));
    }
    @Test
    public void testMutantWithMultipleHorizontalSequences(){
        String[]dna={"AAAAAA",
                "TTTTTT",
                "CCCCCC",
                "GGGGGG",
                "ATGCAT",
                "TGCATG"};
        assertTrue(mutantDetector.isMutant(dna));
    }
    @Test
    public void testNotMutantWithTooSmallDna(){
        String[]dna={
                "ATG",
                "CAG",
                "TTA"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }
    @Test
    public void testMutantDiagonalInCorner(){
        String[]dna={
                "AAGTGC",
                "CAATGC",
                "TTAAGT",
                "AGTAGG",
                "CCCGTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }
    @Test
    public void testMutantAllSameCharacter(){
        String[]dna={
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }
    @Test
    public void testArrayNoCuadrado(){
        String[] dna={"ATG", "CAG", "TTA", "AGG"};
        assertFalse(mutantDetector.isMutant(dna));
    }
    @Test
    public void testCaracterIncorrecto(){
        String[] dna={"ATGC", "CAXT", "TTAT", "AGGG"};
        assertFalse(mutantDetector.isMutant(dna));
    }
   @Test
   public void testEsHumano(){
        String[] dna={"ATGCGA", "CAGTGC", "TTATGT", "AGAACG", "CACCTA", "TCACTG"};
        assertFalse(mutantDetector.isMutant(dna));
   }
   @Test
   public void testNotMutantWithSequenceLongerThanFour(){
        String[]dna= {
                "AGAAC",
                "CAGTC",
                "TTATT",
                "AGGAG",
                "CCCAC"
        };
        assertFalse(mutantDetector.isMutant(dna));
   }
   @Test
   public void testEsHumanoConUnaSecuencia(){
        String[] dna={"AAAAGA", "CAGTGC", "TTGTGT", "AGTACG", "CACCTA", "TCACTG"};
        assertFalse(mutantDetector.isMutant(dna));
   }
       @Test
    public void testConversionAMatrix(){
        String[] dna={"ATGC", "CAGT", "TTAT", "AGGG"};
        assertFalse(mutantDetector.isMutant(dna));
    }
    @Test
    public void testDiagonalAscendente(){
        String[] dna={  "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCATA", "TCACTG"};
        assertTrue(mutantDetector.isMutant(dna));
    }
    @Test
    public void testFilaConLongitudDiferente(){
        String[] dna={"ATGCGA", "CAGTGC", "TTATG", "AGAAGG", "CCCCTA", "TCACTG"};
        assertFalse(mutantDetector.isMutant(dna));
    }
    @Test
    public void testFilaNula(){
        String[] dna={"ATGCGA", null, "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertFalse(mutantDetector.isMutant(dna));
    }
    @Test
    public void testMatrizGrande(){
        String[]dna= {"ATGCATGCAT", "CAGTCAGTCA", "TTATTATTAT", "AGGAAGGAAG", "CCCACCCACC", "TCATTCATTC", "GATCGATCGA", "TACGTACGTA", "CGTACGTACG", "ATGCATGCAT"};
         assertTrue(mutantDetector.isMutant(dna));
    }
    @Test
    public void testMatrizMinimaHumano(){
        String[] dna={ "AAAA", "CAGT", "TTAT", "AGGG"};
        assertFalse(mutantDetector.isMutant(dna));
    }
    @Test
    public void testMatrizMinimaMutante(){
        String[] dna={ "AAAA", "CAGT", "TTTT", "AGGG"};
        assertTrue(mutantDetector.isMutant(dna));
    }
    @Test
    public void testRendimientoEarlyTermination(){
        String[] dna={   "AAAACTGCAT", "CAGTCAGTCA", "TTATTATTAT", "AGGAAGGAAG", "CCCAATCCCC", "TCACTCACTC", "GGTCGATCGA", "TGCGTACGTA", "CGTACGTACG", "AGGCATGCAT"};
         assertTrue(mutantDetector.isMutant(dna));
    }
    @Test
    public void testMinusculas(){
        String[] dna = {"atgcga", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertFalse(mutantDetector.isMutant(dna));
    }
    @Test
    public void testHorizontal(){
        String[] dna={"AAAAAA", "CTGTGT", "TTTTGT", "AGGCGC", "CCCATA", "TCACTG"};
        assertTrue(mutantDetector.isMutant(dna));
    }
    @Test
    public void testVertical(){
        String[] dna={  "ATGCGA", "AAGTGC", "ATATGT", "AGGAGG", "ACCCTA", "ACAATG"};
        assertTrue(mutantDetector.isMutant(dna));
    }
    @Test
    public void testDiagonalDescendente(){
        String[]dna={"AAAAAA", "CAAAGC", "TTAAGT", "AGTAGG", "CCCGTA", "TCACTG"};
        assertTrue(mutantDetector.isMutant(dna));
    }
    @Test
    public void testMutantWithTwoSequences(){
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertTrue(mutantDetector.isMutant(dna));
    }
}
