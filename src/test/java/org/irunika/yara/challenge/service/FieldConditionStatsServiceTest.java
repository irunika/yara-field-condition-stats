package org.irunika.yara.challenge.service;

import org.irunika.yara.challenge.entity.FieldConditionDailySummary;
import org.irunika.yara.challenge.model.FieldStatsSummary;
import org.irunika.yara.challenge.repository.FieldConditionDailySummaryRepository;
import org.irunika.yara.challenge.repository.FieldConditionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class FieldConditionStatsServiceTest {

    @MockBean
    private FieldConditionDailySummaryRepository fieldConditionDailySummaryRepository;

    @MockBean
    private FieldConditionRepository fieldConditionRepository;

    @Autowired
    private FieldConditionStatsService fieldConditionStatsService;

    @TestConfiguration
    static class FieldConditionStatsServiceTestContextConfiguration {

        @Bean
        public FieldConditionStatsService fieldConditionStatsService() {
            return new FieldConditionStatsService();
        }
    }

    @Before
    public void setup() {
        List<FieldConditionDailySummary> fieldConditionDailySummaries = Stream.of(
                new FieldConditionDailySummary(LocalDate.now(), 2.0, 0.8, 0.2, 3),
                new FieldConditionDailySummary(LocalDate.now().plusDays(1), 1.2, 0.5, 0.1, 2),
                new FieldConditionDailySummary(LocalDate.now().plusDays(2), 0.8, 0.6, 0.05, 3)
        ).collect(Collectors.toList());

        when(fieldConditionDailySummaryRepository.findById(LocalDate.now())).thenReturn(Optional.of(fieldConditionDailySummaries.get(0)));
        when(fieldConditionDailySummaryRepository.findById(LocalDate.now().plusDays(-1))).thenReturn(Optional.empty());
        when(fieldConditionDailySummaryRepository.findAllByOrderByDateDesc(PageRequest.of(0, 3)))
                .thenReturn(fieldConditionDailySummaries);

        // Need to test FieldConditionStatsService when the list of items are empty.
        // But here if PageRequest.of requires size >= 1 or else it throws run time exception.
        // So consider this situation when user asking for items size = 1 the table is empty.
        when(fieldConditionDailySummaryRepository.findAllByOrderByDateDesc(PageRequest.of(0, 1)))
                .thenReturn(new ArrayList<>());
    }

    @Test
    public void saveFieldCondition_withValidData_saveSuccessfully() {
        LocalDateTime localDateTime = LocalDateTime.now();
        fieldConditionStatsService.saveFieldCondition(0.5, localDateTime);
        verify(fieldConditionDailySummaryRepository, Mockito.times(1)).findById(localDateTime.toLocalDate());
        verify(fieldConditionDailySummaryRepository, Mockito.times(1)).save(any());
        verify(fieldConditionRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void generateFieldStatsSummary_whenDataIsAvailable_successfullyGenerateSummary() {
        FieldStatsSummary fieldStatsSummary = fieldConditionStatsService.generateFieldStatsSummary(3);
        assertEquals(0.8, fieldStatsSummary.getMax(), 0);
        assertEquals(0.05, fieldStatsSummary.getMin(), 0);
        assertEquals(0.5, fieldStatsSummary.getAvg(), 0);
        verify(fieldConditionDailySummaryRepository, Mockito.times(1)).findAllByOrderByDateDesc(PageRequest.of(0, 3));
    }

    @Test
    public void generateFieldStatsSummary_whenDataIsNotAvailable_successfullyGenerateSummary() {
        FieldStatsSummary fieldStatsSummary = fieldConditionStatsService.generateFieldStatsSummary(1);
        assertEquals(0.0, fieldStatsSummary.getMax(), 0);
        assertEquals(0.0, fieldStatsSummary.getMin(), 0);
        assertEquals(0.0, fieldStatsSummary.getAvg(), 0);
        verify(fieldConditionDailySummaryRepository, Mockito.times(1)).findAllByOrderByDateDesc(PageRequest.of(0, 1));
    }
}
