package org.irunika.yara.challenge.model;

import lombok.Getter;

@Getter
public class FieldStatsSummary {

    private final double min;
    private final double max;
    private final double avg;

    public FieldStatsSummary(double min, double max, double avg) {
        this.min = min;
        this.max = max;
        this.avg = avg;
    }
}
