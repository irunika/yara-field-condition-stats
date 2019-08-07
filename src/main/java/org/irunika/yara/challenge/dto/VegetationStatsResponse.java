package org.irunika.yara.challenge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VegetationStatsResponse {

    private double min;
    private double max;
    private double avg;

    public VegetationStatsResponse() {
    }

    public VegetationStatsResponse(double min, double max, double avg) {
        this.min = min;
        this.max = max;
        this.avg = avg;
    }
}
