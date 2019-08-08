package org.irunika.yara.challenge.service;

import org.irunika.yara.challenge.util.Constants;
import org.irunika.yara.challenge.exception.FieldStatsServiceException;
import org.irunika.yara.challenge.model.FieldCondition;
import org.irunika.yara.challenge.model.FieldConditionDailyStats;
import org.irunika.yara.challenge.model.FieldStatsSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link FieldStatsService} for in memory Field stat generation.
 */
@Component(Constants.IN_MEMORY_FIELD_STAT_SERVICE)
public class InMemoryFieldStatsService implements FieldStatsService {

    private static final Logger log = LoggerFactory.getLogger(InMemoryFieldStatsService.class);

    private final List<FieldConditionDailyStats> fieldConditionDailyStats;

    public InMemoryFieldStatsService() {
        this.fieldConditionDailyStats = new ArrayList<>(Constants.INITIAL_IN_MEMORY_STATS_CAPACITY);
    }

    @Override
    public synchronized void saveFieldCondition(FieldCondition fieldCondition) throws FieldStatsServiceException {
        try {
            if (!fieldConditionDailyStats.isEmpty() &&
                    fieldConditionDailyStats.get(fieldConditionDailyStats.size() - 1).getDate().equals(fieldCondition.getDate())) {
                FieldConditionDailyStats fieldConditionDailyStats = this.fieldConditionDailyStats.get(this.fieldConditionDailyStats.size() - 1);
                log.debug("Adding field condition entry to existing fieldConditionDayInfo: " + fieldConditionDailyStats.getDate());
                this.fieldConditionDailyStats.get(this.fieldConditionDailyStats.size() - 1).addVegetation(fieldCondition.getVegetation());
            } else {
                log.debug("Adding new field condition ");
                // If every detail is saved it will space complexity will be O(N)
                // Since maintaining a queue which only contains 30 elements.
                if (fieldConditionDailyStats.size() == Constants.INITIAL_IN_MEMORY_STATS_CAPACITY) {
                    fieldConditionDailyStats.remove(0);
                }

                FieldConditionDailyStats conditionDayInfo = new FieldConditionDailyStats(fieldCondition.getDate());
                conditionDayInfo.addVegetation(fieldCondition.getVegetation());
                fieldConditionDailyStats.add(conditionDayInfo);
            }
        } catch (ParseException e) {
            throw new FieldStatsServiceException("Unidentified date: " + fieldCondition.getOccurrenceAt(), e);
        }
    }

    @Override
    public List<FieldConditionDailyStats> getFieldConditionDailyStats() {
        return fieldConditionDailyStats;
    }

    /**
     * Since the no of elements in fieldConditionDailyStats is maintained to 30 elements.
     * This method will always run for 30 only elements.
     * Time complexity of this method is constant time.
     *
     * @return VegetationStats for last 30 days.
     */
    @Override
    public FieldStatsSummary generateVegetationStats() {
        double min = Double.MAX_VALUE;
        double max = 0;
        double totalVegetation = 0;
        int totalNoOfVegetation = 0;
        synchronized (fieldConditionDailyStats) {
            for (FieldConditionDailyStats fieldConditionsDayInfo : fieldConditionDailyStats) {
                if (min > fieldConditionsDayInfo.getMaxVegetation()) {
                    min = fieldConditionsDayInfo.getMinVegetation();
                }
                if (max < fieldConditionsDayInfo.getMaxVegetation()) {
                    max = fieldConditionsDayInfo.getMaxVegetation();
                }
                totalVegetation += fieldConditionsDayInfo.getTotalVegetation();
                totalNoOfVegetation += fieldConditionsDayInfo.getNoOfVegetationInfoReceived();
            }
        }
        return new FieldStatsSummary(min, max, totalVegetation / totalNoOfVegetation);
    }
}
