package org.irunika.yara.challenge.controller;

import lombok.Getter;
import lombok.Setter;
import org.irunika.yara.challenge.model.FieldStatsSummary;

@Getter
@Setter
public class FieldStatsResponse {

    private FieldStatsSummary vegetation;

    public FieldStatsResponse() {
    }

    public FieldStatsResponse(FieldStatsSummary vegetation) {
        this.vegetation = vegetation;
    }
}
