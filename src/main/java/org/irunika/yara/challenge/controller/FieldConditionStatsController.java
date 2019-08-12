package org.irunika.yara.challenge.controller;

import lombok.extern.slf4j.Slf4j;
import org.irunika.yara.challenge.model.FieldConditionRequest;
import org.irunika.yara.challenge.model.FieldStatsResponse;
import org.irunika.yara.challenge.service.FieldConditionStatsService;
import org.irunika.yara.challenge.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
@RestController
public class FieldConditionStatsController {

    @Autowired
    private FieldConditionStatsService fieldConditionStatsService;

    @RequestMapping(Constants.URI_FIELD_STATISTICS)
    public FieldStatsResponse getFieldStatistics() {
        return new FieldStatsResponse(fieldConditionStatsService.generateFieldStatsSummary(30));
    }

    @RequestMapping(method = RequestMethod.POST, value = Constants.URI_FIELD_CONDITIONS)
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addFieldCondition(@RequestBody FieldConditionRequest fieldConditionRequest) {
        fieldConditionStatsService.saveFieldCondition(fieldConditionRequest.getVegetation(),
                LocalDateTime.parse(fieldConditionRequest.getOccurrenceAt(), DateTimeFormatter.ISO_DATE_TIME));
    }

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public String handleDateTimeParseException(DateTimeParseException e) {
        log.error("Could not parse the date time.", e);
        return "Invalid date " + e.getParsedString() + ". Expecting ISO_8601 date time format for field 'occurrenceAt'";
    }
}
