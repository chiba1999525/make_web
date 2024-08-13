package com.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.ItemImage;

@Repository
public interface ItemImageRepository extends JpaRepository<ItemImage, Integer> {
    
    ItemImage findByIdAndItemIdAndImage(int id, int itemImage, byte[] image);
  
    boolean existsByIdAndItemIdAndImage(int id, int itemImage, byte[] image);
    
}