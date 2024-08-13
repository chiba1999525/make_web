package com.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Genre;
import com.entity.Item;
import com.repository.GenreRepository;
import com.repository.ItemRepository;
import com.rule.Rule;

@Controller
public class SearchController {
    
    @Autowired
    private ItemRepository itemRep;
    
    @Autowired
    private GenreRepository genreRep;
    
    // 検索結果表示処理
    @RequestMapping("/search")
    public String search(
    		@RequestParam("search") String search,
    		HttpServletRequest request,
    		Model model
    		) {
    	
    	// 検索結果が空の場合、前のページにリダイレクト
        if (search.isEmpty()) {
            String referrer = request.getHeader("Referer");
            return "redirect:" + referrer;
        }
        
        List<Item> itemAll = itemRep.findAll();
        List<Item> searchItem = new ArrayList<>();
        
        //itemインスタンスを取り出す
        for (Item item : itemAll) {
        	
        	//ジャンル名をitemに追加する
        	Genre genre = genreRep.findById(item.getGenreId());
			String genreName = genre.getGenreName();
			item.setGenreName(genreName);
			
        	//コンテンツから探す
            if (item.getContent().contains(search)) {
                searchItem.add(item);
                
            }//タイトルから探す
            else if (item.getTitle().contains(search)) {
                searchItem.add(item);
                
            } //ジャンルから探す
            else if (item.getGenreName().contains(search)) {
                searchItem.add(item);
            }
            
        }
    	
        //検索にヒットしたitemデータを定義
        for (Item item : searchItem) {
            
           // Genre genre = genreRep.findById(item.getGenreId()).orElse(null); 
            Genre genre = genreRep.findById(item.getGenreId());
            if (genre != null) {
            	 // 投稿時間を日本時間に変換
		        String n_japantime =  Rule.return_japantime(item.getNewDatetime());
				item.setJnewDatetime(n_japantime);;
				
				//ジャンルimageをitemに追加する
	            byte[] image = genre.getImage();
	            // Base64エンコード
	            String encodedImage = Base64.getEncoder().encodeToString(image);
	            item.setSimage(encodedImage);
            }
        }
        
        //検索結果が見つらなかった場合
        if (searchItem.isEmpty()) {
        	model.addAttribute("searchNum", 0);
            model.addAttribute("message", "検索結果が見つかりませんでした。");
        }
        //検索結果が見つかった場合
        else {
        	model.addAttribute("searchNum", searchItem.size());
            model.addAttribute("searchItem", searchItem);
        }
        //検索した値をモデルに
        model.addAttribute("search", search);
        return "search"; // "search"はビュー名
    }
}
