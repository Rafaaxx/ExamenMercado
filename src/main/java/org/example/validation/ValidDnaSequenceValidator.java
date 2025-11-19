package org.example.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ValidDnaSequenceValidator
        implements ConstraintValidator<ValidDnaSequence, String[]> {

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        if (dna == null || dna.length == 0) return false;

        int n = dna.length;
        Pattern pattern = Pattern.compile("^[ATCG]+$");
        if(n<4){
            return false;
        }
        for (String row : dna) {
            if (row == null || row.length() != n) return false;
            if (!pattern.matcher(row).matches()) return false;
        }
        return true;
    }
}