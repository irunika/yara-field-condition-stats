package org.irunika.yara.challenge.repository;

import org.irunika.yara.challenge.entity.FieldConditionDailySummary;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FieldConditionDailySummaryRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FieldConditionDailySummaryRepository dailySummaryRepository;

    private LocalDate localDate;

    @Before
    public void setup() {
        localDate = LocalDate.now();
        // Save entries without a date order.
        dailySummaryRepository.save(new FieldConditionDailySummary(localDate.plusDays(2), 0, 1));
        dailySummaryRepository.save(new FieldConditionDailySummary(localDate.plusDays(0), 0, 1));
        dailySummaryRepository.save(new FieldConditionDailySummary(localDate.plusDays(1), 0, 1));
    }

    @Test
    public void findAllByOrderByDateDesc_askForDataMoreThanAvailable_receiveAllAvailableDataInDecsOrder() {
        List<FieldConditionDailySummary> fieldConditionDailySummaries =
                dailySummaryRepository.findAllByOrderByDateDesc(PageRequest.of(0, 5));
        assertEquals(3, fieldConditionDailySummaries.size());
        for (int i = 0; i < 3; i++) {
            assertEquals(localDate.plusDays(2 - i), fieldConditionDailySummaries.get(i).getDate());
        }
    }

    @Test
    public void findAllByOrderByDateDesc_askForDataLessThanAvailable_receiveAllAvailableDataInDecsOrder() {
        List<FieldConditionDailySummary> fieldConditionDailySummaries =
                dailySummaryRepository.findAllByOrderByDateDesc(PageRequest.of(0, 2));
        assertEquals(2, fieldConditionDailySummaries.size());
        for (int i = 0; i < 2; i++) {
            assertEquals(localDate.plusDays(2 - i), fieldConditionDailySummaries.get(i).getDate());
        }
    }
}
