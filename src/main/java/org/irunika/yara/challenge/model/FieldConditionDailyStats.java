package org.irunika.yara.challenge.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Store the field stats of a given date.
 */
@Getter
@Setter
public class FieldConditionDailyStats {

    private final Date date;

    private double totalVegetation;
    private double maxVegetation;
    private double minVegetation;
    private int noOfVegetationInfoReceived;

    public FieldConditionDailyStats(Date date) {
        this.date = date;
        this.totalVegetation = 0;
        this.noOfVegetationInfoReceived = 0;
        this.minVegetation = Double.MAX_VALUE;
    }

    public void addVegetation(double vegetation) {
        if (maxVegetation < vegetation) {
            maxVegetation = vegetation;
        }

        if (minVegetation > vegetation) {
            minVegetation = vegetation;
        }

        totalVegetation += vegetation;
        noOfVegetationInfoReceived++;
    }
}
