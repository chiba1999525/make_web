package com.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.Inquiry;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Integer> {
    
	Inquiry findByIdAndTitleAndContentAndDateAndNameAndAddressAndCheck
	(int id, String title, String content, int check, LocalDateTime date, String name, String address);
	
	boolean existsByIdAndTitleAndContentAndDateAndNameAndAddressAndCheck
    (int id, String title, String content, int check, LocalDateTime date, String name, String address);
}

