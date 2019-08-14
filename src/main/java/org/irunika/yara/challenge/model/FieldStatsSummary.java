package org.irunika.yara.challenge.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class FieldStatsSummary {

    @ApiModelProperty(example = "0.01")
    private final double min;

    @ApiModelProperty(example = "0.8")
    private final double max;

    @ApiModelProperty(example = "0.4")
    private final double avg;

    public FieldStatsSummary(double min, double max, double avg) {
        this.min = min;
        this.max = max;
        this.avg = avg;
    }
}
