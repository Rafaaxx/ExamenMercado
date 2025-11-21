package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class MutantDetector {
    private static final int  SEQUENCE_LENGTH = 4;
    public boolean isMutant(String[] dna ){
        if (!isValidDna(dna)){
         return false;
        }
        char[][] matrix=toMatrix(dna);
        int n=matrix.length;
        int sequenceCount=0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }
                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalDescending(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }
                if (row >= SEQUENCE_LENGTH - 1 && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalAscending(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean isValidDna(String[] dna){
        if (dna==null || dna.length==0){
            return false;
        }
        final int n = dna.length;

        for (String row : dna) {
            if (row == null || row.length() != n) {
                return false;
            }

            for (char c : row.toCharArray()) {
                if (c != 'A' && c != 'T' && c != 'C' && c != 'G') {
                    return false;
                }
            }
        }
        return true;
    }
    private char[][] toMatrix(String[] dna ){
        int n= dna.length;
        char[][] matrix = new char[n][];
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }
        return matrix;
    }
    private boolean checkHorizontal(char[][] matrix, int row, int col){
        final char base = matrix[row][col];
        boolean encontrado=matrix[row][col + 1] == base &&
                matrix[row][col + 2] == base &&
                matrix[row][col + 3] == base;
        if (encontrado){
            log.debug("Patron horizontal encontrado en fila {} col {}", row, col);
        }
        return encontrado;
    }
    private boolean checkVertical(char[][] matrix, int row, int col){
        final char base = matrix[row][col];
        boolean encontrado=matrix[row + 1][col] == base &&
                matrix[row + 2][col] == base &&
                matrix[row + 3][col] == base;
        if (encontrado){
            log.debug("Patron vertical encontrado en fila {} col {}", row, col);
        }
        return encontrado;
    }
    private boolean checkDiagonalDescending(char[][] matrix, int row, int col){
        final char base = matrix[row][col];
        boolean encontrado= matrix[row + 1][col + 1] == base &&
                matrix[row + 2][col + 2] == base &&
                matrix[row + 3][col + 3] == base;
        if (encontrado){
            log.debug("Patron diagonal descendente encontrado en fila {} col {}", row, col);
        }
        return encontrado;
    }
    private boolean checkDiagonalAscending(char[][] matrix, int row, int col){
        final char base = matrix[row][col];
        boolean encontrado=matrix[row - 1][col + 1] == base &&
                matrix[row - 2][col + 2] == base &&
                matrix[row - 3][col + 3] == base;
        if (encontrado){
            log.debug("Patron diagonal ascendente encontrado en fila {} col {}", row, col);
        }
        return encontrado;
    }

}
