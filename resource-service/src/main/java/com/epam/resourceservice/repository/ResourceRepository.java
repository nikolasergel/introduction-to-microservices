package com.epam.resourceservice.repository;

import com.epam.resourceservice.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    @Query("SELECT s.id FROM Resource s WHERE s.id IN :ids")
    List<Long> findExistingIdsByIds(Iterable<Long> ids);
}
