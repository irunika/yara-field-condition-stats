package org.irunika.yara.challenge.service;

import org.irunika.yara.challenge.exception.FieldStatsServiceException;
import org.irunika.yara.challenge.model.FieldCondition;
import org.irunika.yara.challenge.model.FieldConditionDailyStats;
import org.irunika.yara.challenge.model.FieldStatsSummary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FieldStatsService {

    void saveFieldCondition(FieldCondition fieldCondition) throws FieldStatsServiceException;

    List<FieldConditionDailyStats> getFieldConditionDailyStats();

    FieldStatsSummary generateVegetationStats();
}
