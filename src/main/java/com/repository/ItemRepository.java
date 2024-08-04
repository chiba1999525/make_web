package com.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    
	Item findByIdAndTitleAndContentAndNewDatetimeAndEditDatetimeAndClickNum
	(int id, String title, String content, LocalDateTime newDatetime, LocalDateTime editDatetime, int clickNum);
	
//	Item findById(int id);
	
    Item findByGenreId(int genreId);
    
	boolean existsByIdAndTitleAndContentAndNewDatetimeAndEditDatetimeAndClickNum
    (int id, String title, String content, LocalDateTime newDatetime, LocalDateTime editDatetime, int clickNum);

	Item findByTitle(String title);
}
