package com.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Genre;
import com.entity.Item;
import com.repository.GenreRepository;
import com.repository.ItemRepository;
import com.rule.Rule;

@Controller
public class HomeController {
	
	//itemリポジトリテーブル
    @Autowired  private ItemRepository itemRep;
    //genreリポジトリテーブル
    @Autowired private GenreRepository genreRep;
	
    //topページ
	@RequestMapping("/")
	 public String top(
	    		Model model
	    		) {
		
	    	//itemデータをすべて取り出す
	    	List<Item> itemAll = itemRep.findAll();
	    	
	    	//itemリストを投稿時間で降順
	    	itemAll.sort(Comparator.comparing(Item::getNewDatetime).reversed());
	    	
	    	//新着itemリスト
	    	List<Item> newItem = new ArrayList<>();
	    	
	    	//新着３件取り出す
	    	for (int i = 0; i < 3; i++) {
	    		 // リストのサイズを超えたらループを抜ける
	    		if (itemAll.size() <= i || itemAll.get(i) == null) {
	    	        break;
	    	    }
	    		//指定のitemレコードを取り出す
	    		Item item = itemAll.get(i);

	    		 // 投稿時間を日本時間に変換
		        String n_japantime =  Rule.return_japantime(item.getNewDatetime());
				item.setJnewDatetime(n_japantime);
				
				//ジャンルレコードを取り出す
				Genre genre = genreRep.findById(item.getGenreId());
				
				//ジャンル名をitemに追加する
				String genreName = genre.getGenreName();
				item.setGenreName(genreName);
				
				//ジャンルimageをitemに追加する
	            byte[] image = genre.getImage();
	            // Base64エンコード
	            String encodedImage = Base64.getEncoder().encodeToString(image);
	            item.setSimage(encodedImage);
	    		
	    		newItem.add(item);
	    	}
	    	
	    	//itemリストをクリック数で降順
	    	itemAll.sort(Comparator.comparing(Item::getClickNum).reversed());
	    	
	    	//新着itemリスト
	    	List<Item> clickItem = new ArrayList<>();
	    	
	    	//閲覧上位３件取り出す
	    	for (int i = 0; i < 3; i++) {
	    		 // リストのサイズを超えたらループを抜ける
	    		if (itemAll.size() <= i || itemAll.get(i) == null) {
	    	        break;
	    	    }
	    		//指定のitemレコードを取り出す
	    		Item item = itemAll.get(i);

	    		 // 投稿時間を日本時間に変換
		        String n_japantime =  Rule.return_japantime(item.getNewDatetime());
				item.setJnewDatetime(n_japantime);
				
				//ジャンルレコードを取り出す
				Genre genre = genreRep.findById(item.getGenreId());
				
				//ジャンル名をitemに追加する
				String genreName = genre.getGenreName();
				item.setGenreName(genreName);
				
				//ジャンルimageをitemに追加する
	            byte[] image = genre.getImage();
	            // Base64エンコード
	            String encodedImage = Base64.getEncoder().encodeToString(image);
	            item.setSimage(encodedImage);
	    		
	    		clickItem.add(item);
	    	}
	    	
	    	//ヘッダージャンル
	    	List<Genre> genreAll = genreRep.findAll();
	    	
	    	//モデルを追加
	    	model.addAttribute("genreAll", genreAll);
	    	model.addAttribute("newItem", newItem);
	    	model.addAttribute("clickItem", clickItem);
	    	
	    	//topページを表示
	        return "top";
	    }
	
	@GetMapping("/login")
    public String login() {
        return "login"; // ログインページ
    }
	
	@GetMapping("/logout")
    public String logout() {
        return "login"; // ログインページ
    }


}
