package org.irunika.yara.challenge.model;

import lombok.Getter;
import lombok.Setter;
import org.irunika.yara.challenge.dto.VegetationStatsResponse;

@Getter
@Setter
public class Vegetation {

    private VegetationStatsResponse vegetation;

    public Vegetation() {
    }

    public Vegetation(VegetationStatsResponse vegetation) {
        this.vegetation = vegetation;
    }
}
