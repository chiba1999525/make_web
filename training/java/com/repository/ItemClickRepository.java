package com.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.ItemClick;

@Repository
public interface ItemClickRepository extends JpaRepository<ItemClick, Integer> {
    
	ItemClick findByIdAndItemIdAndDate
	(int id, int itemId, LocalDateTime date);
	
	boolean existsByIdAndItemIdAndDate
    (int id, int itemId, LocalDateTime date);
}