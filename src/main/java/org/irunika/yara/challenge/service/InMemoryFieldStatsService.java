package org.irunika.yara.challenge.service;

import org.irunika.yara.challenge.util.Constants;
import org.irunika.yara.challenge.exception.FieldStatsServiceException;
import org.irunika.yara.challenge.model.FieldCondition;
import org.irunika.yara.challenge.model.FieldConditionDayInfo;
import org.irunika.yara.challenge.dto.VegetationStatsResponse;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link FieldStatsService} for in memory Field stat generation.
 */
@Component(Constants.IN_MEMORY_FIELD_STAT_SERVICE)
public class InMemoryFieldStatsService implements FieldStatsService {

    private final List<FieldConditionDayInfo> fieldConditionsDayInfos;

    public InMemoryFieldStatsService() {
        this.fieldConditionsDayInfos = new ArrayList<>(30);
    }

    @Override
    public synchronized void saveFieldCondition(FieldCondition fieldCondition) throws FieldStatsServiceException {
        try {
            if (!fieldConditionsDayInfos.isEmpty() && fieldConditionsDayInfos.get(fieldConditionsDayInfos.size() - 1).getDate().equals(fieldCondition.getDate())) {
                System.out.println("Adding to existing");
                fieldConditionsDayInfos.get(fieldConditionsDayInfos.size() - 1).addVegetation(fieldCondition.getVegetation());
            } else {
                System.out.println("Adding to new");
                // If every detail is saved it will space complexity will be O(N)
                // Since maintaining a queue which only contains 30 elements.
                if (fieldConditionsDayInfos.size() == 30) {
                    fieldConditionsDayInfos.remove(0);
                }

                FieldConditionDayInfo conditionDayInfo = new FieldConditionDayInfo(fieldCondition.getDate());
                conditionDayInfo.addVegetation(fieldCondition.getVegetation());
                fieldConditionsDayInfos.add(conditionDayInfo);
            }
        } catch (ParseException e) {
            throw new FieldStatsServiceException("Unidentified date: " + fieldCondition.getOccurrenceAt(), e);
        }
    }

    @Override
    public List<FieldConditionDayInfo> getFieldConditionsDayInfos() {
        return fieldConditionsDayInfos;
    }

    /**
     * Since the no of elements in fieldConditionsDayInfos is maintained to 30 elements.
     * This method will always run for 30 only elements.
     * Time complexity of this method is constant time.
     *
     * @return VegetationStats for last 30 days.
     */
    @Override
    public VegetationStatsResponse generateVegetationStats() {
        double min = Double.MAX_VALUE;
        double max = 0;
        double totalVegetation = 0;
        int totalNoOfVegetation = 0;
        synchronized (fieldConditionsDayInfos) {
            for (FieldConditionDayInfo fieldConditionsDayInfo : fieldConditionsDayInfos) {
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
        return new VegetationStatsResponse(min, max, totalVegetation / totalNoOfVegetation);
    }
}
