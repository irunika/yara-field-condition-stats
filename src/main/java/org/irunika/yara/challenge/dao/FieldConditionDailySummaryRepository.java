package org.irunika.yara.challenge.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FieldConditionDailySummaryRepository extends JpaRepository<FieldConditionDailySummary, LocalDate> {

    List<FieldConditionDailySummary> findAllByOrderByDateDesc(Pageable pageable);
}
