package org.irunika.yara.challenge.service;

import org.irunika.yara.challenge.exception.FieldStatsServiceException;
import org.irunika.yara.challenge.model.FieldCondition;
import org.irunika.yara.challenge.model.FieldConditionDayInfo;
import org.irunika.yara.challenge.dto.VegetationStatsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FieldStatsService {

    void saveFieldCondition(FieldCondition fieldCondition) throws FieldStatsServiceException;

    List<FieldConditionDayInfo> getFieldConditionsDayInfos();

    VegetationStatsResponse generateVegetationStats();
}
