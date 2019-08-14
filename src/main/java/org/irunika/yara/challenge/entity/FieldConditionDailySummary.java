package org.irunika.yara.challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class FieldConditionDailySummary {

    @Id
    private LocalDate date;
    private double totalVegetation;
    private double maxVegetation;
    private double minVegetation;
    private int noOfOccurrences;

    public FieldConditionDailySummary() {
        this.totalVegetation = 0;
        this.noOfOccurrences = 0;
        this.maxVegetation = Double.MIN_VALUE;
        this.minVegetation = Double.MAX_VALUE;
    }

    public FieldConditionDailySummary(LocalDate date, double totalVegetation, int noOfOccurrences) {
        this();
        this.date = date;
        this.totalVegetation = totalVegetation;
        this.noOfOccurrences = noOfOccurrences;
    }
}
