package org.irunika.yara.challenge.repository;

import org.irunika.yara.challenge.entity.FieldConditionDailySummary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FieldConditionDailySummaryRepository extends JpaRepository<FieldConditionDailySummary, LocalDate> {

    /**
     * Find all with the size defined by {@link Pageable} and order by Date DESC.
     *
     * @param pageable {@link Pageable} to represent the no of elements to be searched from the table.
     * @return a list of {@link FieldConditionDailySummary} with size defined by pageable.
     */
    List<FieldConditionDailySummary> findAllByOrderByDateDesc(Pageable pageable);
}
