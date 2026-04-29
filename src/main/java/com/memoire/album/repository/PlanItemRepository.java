package com.memoire.album.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.memoire.album.model.PlanItem;

public interface PlanItemRepository extends JpaRepository<PlanItem, String> {

    List<PlanItem> findAllByOrderByNumAsc();

    @Query("SELECT COALESCE(MAX(p.num), 0) FROM PlanItem p")
    int findMaxNum();

}