package com.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entity.Genre;
import com.entity.Item;
import com.repository.GenreRepository;
import com.repository.ItemRepository;
import com.rule.Rule;

@Controller
public class GenreItemController {
	
	@Autowired private GenreRepository genreRep;
	
	@Autowired private ItemRepository itemRep;
	
	//ジャンル別item表示処理
	@RequestMapping("/genre_item/{genreName}")
	public String genre_item(
			@PathVariable("genreName") String genreName, 
			Model model,
			//リダイレクト
			RedirectAttributes redirectAttrs
			) {
		
		//ジャンル名から紐づくレコードを取り出す
	    Genre genre = genreRep.findByGenreName(genreName);
	    //レコードからジャンルIDを取り出す
		int genreId = genre.getId();
		
		//itemレコードをすべて取り出す
		List<Item> itemAll = itemRep.findAll();		
		//ジャンルitemリストを定義
		List<Item> genre_itemList = new ArrayList<>();
		//紐づくレコードをgenre_itemリストに追加
		for (Item item : itemAll) {
			if(item.getGenreId() == genreId) {
				genre_itemList.add(item);
			}
		}
		
		for (Item item : genre_itemList) {
			 // 投稿時間を日本時間に変換
	        String n_japantime =  Rule.return_japantime(item.getNewDatetime());
			item.setJnewDatetime(n_japantime);
			//itemインスタンスにジャンル名を追加
			item.setGenreName(genreName);
			//ジャンルimageをitemに追加する
            byte[] image = genre.getImage();
            // Base64エンコード
            String encodedImage = Base64.getEncoder().encodeToString(image);
            item.setSimage(encodedImage);
		}
		model.addAttribute("genreName", genreName);
		model.addAttribute("genre_itemList", genre_itemList);
		
		return "genre_item";
	}

}
