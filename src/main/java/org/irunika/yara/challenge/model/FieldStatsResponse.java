package org.irunika.yara.challenge.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldStatsResponse {

    private FieldStatsSummary vegetation;

    public FieldStatsResponse(FieldStatsSummary vegetation) {
        this.vegetation = vegetation;
    }
}
