package com.controller.admin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entity.Genre;
import com.entity.Item;
import com.entity.ItemClick;
import com.repository.GenreRepository;
import com.repository.ItemClickRepository;
import com.repository.ItemRepository;
import com.rule.Rule;

@Controller
public class Admin_ItemController {
	
	//item一覧ページ
	private static final String PAGE_ITEMS = "/admin/item/items";
	
	//itemテーブル用のリポジトリを定義
	 @Autowired private ItemRepository itemRep;
	//genreテーブル用のリポジトリを定義
	@Autowired private GenreRepository genreRep;
	//ItemClickリポジトリを定義
	@Autowired private ItemClickRepository icRep;

	
	//itemリストの定義	
	List<String> itemlist = new ArrayList<String>();
	
	//Item一覧画面
	@RequestMapping("/admin/items")
	public String items(
			HttpServletRequest req,
			//検索値を受け取るデフォルトは中身は空
			@RequestParam(value = "search", required = false, defaultValue = "") 
			String search,
			Model model) {
		
		//  データベースからすべてのデータを取得する
		List<Item> itemAll = itemRep.findAll();
		//検索結果用のリストを定義
		List<Item> searchItem = new ArrayList<>();
		
		for (Item item : itemAll) {
			
	        // 投稿時間を日本時間に変換
	        String n_japantime =  Rule.return_japantime(item.getNewDatetime());
			item.setJnewDatetime(n_japantime);
			
			//ジャンル名を取り出す
			Genre genren = genreRep.findById(item.getGenreId());
			String genreName = genren.getGenreName();
			item.setGenreName(genreName);
			
			//更新時間を日本時間に変換
			if (item.getEditDatetime() != null) {
				String e_japantime =  Rule.return_japantime(item.getEditDatetime());
				item.setJeditDatetime(e_japantime);	
			}
			
			//検索処理
			if (search != "") {
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
			
		}
		
		if (search.isEmpty()) {
			//itemリストを投稿時間で降順
			itemAll.sort(Comparator.comparing(Item::getNewDatetime).reversed());
			//item全データをモデルに追加
		    model.addAttribute("itemAll",itemAll);
		} else {
			if (searchItem.isEmpty()) {
	            model.addAttribute("success", "検索結果が見つかりませんでした");
	          //item全データをモデルに追加
			    model.addAttribute("itemAll",itemAll);
	        } else {
	            model.addAttribute("searchItem", searchItem);
	        }
		}
			
		return PAGE_ITEMS;
	}
	
	// item詳細ページ
	@RequestMapping("/admin/item/{title}")
	public String item(
			@PathVariable("title") String title, 
			Model model,
			//リダイレクト
			RedirectAttributes redirectAttrs
			) {
	   
		//itemインスタンスを定義
		Item item = new Item();
		item = null;
		
	    // 指定のitemレコードを取得
		List<Item> itemAll = itemRep.findAll();
		
		//指定のレコードを追加
		for (Item itemall : itemAll) {
			if (itemall.getTitle().equals(title)) {
				item = itemall;
				break;
			}
		}
	    
	    if (item == null) {
	    	//nullメッセージの定義
	    	List<String> nullMessages = new ArrayList<String>();
	    	nullMessages.add("投稿は削除されたか存在しません");
	        redirectAttrs.addFlashAttribute("success", nullMessages);	
            return "redirect:/";
        }
        
	    // 投稿時間を日本時間に変換
        String n_japantime =  Rule.return_japantime(item.getNewDatetime());
		item.setJnewDatetime(n_japantime);
		
		//ジャンル名を取り出す
		Genre genren = genreRep.findById(item.getGenreId());
		String genreName = genren.getGenreName();
		item.setGenreName(genreName);
		
		//更新時間を日本時間に変換
		if (item.getEditDatetime() != null) {
			String e_japantime =  Rule.return_japantime(item.getEditDatetime());
			item.setJeditDatetime(e_japantime);	
		}
		
		
		// データベースに保存
        itemRep.save(item);
	    
	    // Modelにitemを追加
	    model.addAttribute("item", item);

	    // 指定のitem詳細ページを表示
	    return "admin/item/item"; // ビューの名前を返す
	}
	
	//item削除処理
		@RequestMapping("/admin/delete_item/{id}")	
		//@RequestMapping("/delete_item")
	    public String delete_item(
	    		@PathVariable("id") int id
	    		) {
			
	        // 指定されたIDのItemレコードを削除
	        itemRep.deleteById(id);
	        
	        //指定されたIDのクリック情報も消す
	        List<ItemClick> icAll = icRep.findAll();
	        
	        for (ItemClick ic : icAll) {
	        	if (ic.getItemId() == id) {
	        		//ジャンルIDと一致するレコードのidを取り出す
	        		int ic_id = ic.getId();
	        		//一致するレコードを削除する
	        		icRep.deleteById(ic_id);
	        	}
	        }
	        
	        // 一覧画面にリダイレクト
	        return "redirect:/admin/items";
	    }
			
		
}
