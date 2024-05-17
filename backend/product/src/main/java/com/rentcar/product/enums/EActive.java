package com.rentcar.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EActive {
    ACTIVE("Y", "Yes"),
    NOT_ACTIVE("N", "NO");

    private final String abbreviation;
    private final String label;

    public EActive parse(String abbreviation) {
        if(abbreviation.equals(EActive.ACTIVE.getAbbreviation())) return EActive.ACTIVE;
        if(abbreviation.equals(EActive.NOT_ACTIVE.getAbbreviation())) return EActive.NOT_ACTIVE;

        return null;
    }
}
