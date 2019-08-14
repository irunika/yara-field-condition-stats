package org.irunika.yara.challenge.repository;

import org.irunika.yara.challenge.entity.FieldCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldConditionRepository extends JpaRepository<FieldCondition, Long> {
}
