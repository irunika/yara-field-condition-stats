package org.irunika.yara.challenge.model;

import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class FieldCondition {

    private static final SimpleDateFormat ISO8601_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private double vegetation;
    private String occurrenceAt;

    public FieldCondition() {
    }

    public Date getDate() throws ParseException {
        return ISO8601_DATE_FORMAT.parse(occurrenceAt.split("T")[0]);
    }
}
