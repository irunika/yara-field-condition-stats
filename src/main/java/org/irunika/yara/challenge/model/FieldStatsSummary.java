package org.irunika.yara.challenge.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldStatsSummary {

    private double min;
    private double max;
    private double avg;

    public FieldStatsSummary() {
    }

    public FieldStatsSummary(double min, double max, double avg) {
        this.min = min;
        this.max = max;
        this.avg = avg;
    }
}
