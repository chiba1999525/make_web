package com.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
    
    Genre findByIdAndGenreNameAndImage(int id, String genreName, byte[] image);
    
    Genre findById(int id);
    
    Genre findByGenreName(String genreName);

    boolean existsByIdAndGenreNameAndImage(int id, String genreName, byte[] image);
}