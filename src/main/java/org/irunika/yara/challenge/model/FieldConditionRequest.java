package org.irunika.yara.challenge.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FieldConditionRequest {

    @ApiModelProperty(example = "0.5")
    private double vegetation;

    @ApiModelProperty(notes = "This value should be in ISO_8601 date time format", example = "2019-04-23T08:50Z")
    private String occurrenceAt;
}
