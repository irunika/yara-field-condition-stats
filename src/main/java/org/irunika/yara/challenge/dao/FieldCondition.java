package org.irunika.yara.challenge.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class FieldCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double vegetation;
    private LocalDateTime occurrenceAt;

    public FieldCondition(double vegetation, LocalDateTime occurrenceAt) {
        this.vegetation = vegetation;
        this.occurrenceAt = occurrenceAt;
    }
}
