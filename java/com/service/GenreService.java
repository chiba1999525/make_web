package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Genre;
import com.repository.GenreRepository;

@Service
public class GenreService {
	
	@Autowired
	private GenreRepository genreRep;
	
	//idと紐づくレコードを返す
	public Genre findById(int id) {
		
		return genreRep.findById(id);
	}
	
	//genreNameと紐づくレコードを返す
	public Genre findByGenreName(String genreName) {
		
		return genreRep.findByGenreName(genreName);
	}

}
