package org.irunika.yara.challenge.service;

import org.irunika.yara.challenge.dao.FieldCondition;
import org.irunika.yara.challenge.dao.FieldConditionDailySummary;
import org.irunika.yara.challenge.dao.FieldConditionDailySummaryRepository;
import org.irunika.yara.challenge.dao.FieldConditionRepository;
import org.irunika.yara.challenge.model.FieldStatsSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FieldConditionStatsService {

    @Autowired
    private FieldConditionDailySummaryRepository fieldConditionDailySummaryRepository;

    @Autowired
    private FieldConditionRepository fIeldConditionRepository;

    @Transactional
    public void saveFieldCondition(double vegetation, LocalDateTime occurrenceAt) {
        fIeldConditionRepository.save(new FieldCondition(vegetation, occurrenceAt));

        Optional<FieldConditionDailySummary> dailySummaryOptional = fieldConditionDailySummaryRepository.findById(occurrenceAt.toLocalDate());
        FieldConditionDailySummary dailySummary;
        if (dailySummaryOptional.isPresent()) {
            dailySummary = dailySummaryOptional.get();
        } else {
            dailySummary = new FieldConditionDailySummary();
            dailySummary.setDate(occurrenceAt.toLocalDate());
        }

        dailySummary.setTotalVegetation(dailySummary.getTotalVegetation() + vegetation);
        dailySummary.setNoOfOccurrences(dailySummary.getNoOfOccurrences() + 1);

        if (dailySummary.getMaxVegetation() < vegetation) {
            dailySummary.setMaxVegetation(vegetation);
        }

        if (dailySummary.getMinVegetation() > vegetation) {
            dailySummary.setMinVegetation(vegetation);
        }

        fieldConditionDailySummaryRepository.save(dailySummary);
    }

    /**
     * Generates the field statistics summary for a given no of dates.
     * eg: 30 days, year (365 days).
     *
     * When consuming this method, if the user always gives the same input value,
     * <ul>
     *     <li>this method runs in constant time</lu>
     *     <li>this method has O(1) space complexity</lu>
     * </ul>
     *
     * @param noOfDays no of dates to summarize data.
     *
     * @return FieldStatsSummary for a given no of dates.
     */
    public FieldStatsSummary generateFieldStatsSummary(int noOfDays) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        double totalVegetation = 0;
        int totalNoOfOccurrences = 0;

        Pageable pageable = PageRequest.of(0, noOfDays);
        for (FieldConditionDailySummary dailySummary :
                fieldConditionDailySummaryRepository.findAllByOrderByDateDesc(pageable)) {
            if (min > dailySummary.getMinVegetation()) {
                min = dailySummary.getMinVegetation();
            }
            if (max < dailySummary.getMaxVegetation()) {
                max = dailySummary.getMaxVegetation();
            }
            totalVegetation += dailySummary.getTotalVegetation();
            totalNoOfOccurrences += dailySummary.getNoOfOccurrences();
        }

        if (min == Double.MAX_VALUE) {
            min = 0;
        }

        if (max == Double.MIN_VALUE) {
            max = 0;
        }

        double avg = 0;
        if (totalNoOfOccurrences != 0 && totalVegetation != 0) {
            avg = totalVegetation / totalNoOfOccurrences;
        }

        return new FieldStatsSummary(min, max, avg);
    }
}
