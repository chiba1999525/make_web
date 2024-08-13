package com.controller.item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entity.Genre;
import com.entity.Item;
import com.entity.ItemClick;
import com.repository.GenreRepository;
import com.repository.ItemClickRepository;
import com.repository.ItemRepository;
import com.rule.Rule;

@Controller
public class ItemController {
	
	//item一覧ページ
	private static final String PAGE_ITEMS = "item/items";
	
	//itemテーブル用のリポジトリを定義
	 @Autowired private ItemRepository itemRep;
	//genreテーブル用のリポジトリを定義
	@Autowired private GenreRepository genreRep;
	//ItemClickリポジトリを定義
	@Autowired private ItemClickRepository icRep;

	
	//itemリストの定義	
	List<String> itemlist = new ArrayList<String>();
	
	//Item一覧画面
	@RequestMapping("items")
	public String items(
			HttpServletRequest req, 
			Model model) {
		
		//  データベースからすべてのデータを取得する
		List<Item> itemAll = itemRep.findAll();
		
		for (Item itemall : itemAll) {
			
	        // 投稿時間を日本時間に変換
	        String n_japantime =  Rule.return_japantime(itemall.getNewDatetime());
			itemall.setJnewDatetime(n_japantime);
			
			//ジャンル名を取り出す
			Genre genren = genreRep.findById(itemall.getGenreId());
			String genreName = genren.getGenreName();
			itemall.setGenreName(genreName);
			
			//更新時間を日本時間に変換
			if (itemall.getEditDatetime() != null) {
				String e_japantime =  Rule.return_japantime(itemall.getEditDatetime());
				itemall.setJeditDatetime(e_japantime);	
			}
			
		}
		//itemリストを降順
		itemAll.sort(Comparator.comparing(Item::getNewDatetime).reversed());
		//item全データをモデルに追加
	    model.addAttribute("itemAll",itemAll);
	    
	  //ヘッダージャンル
    	List<Genre> genreAll = genreRep.findAll();
    	//モデルを追加
    	model.addAttribute("genreAll", genreAll);
			
		return PAGE_ITEMS;
	}
	
	// item詳細ページ
	@RequestMapping("/item/{title}")
	public String item(
			@PathVariable("title") String title, 
			Model model,
			//リダイレクト
			RedirectAttributes redirectAttrs,
			HttpServletRequest request
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
	        String referrer = request.getHeader("Referer");
            return "redirect:/";
        }
	    
	    //現在の日時情報を取得
	    LocalDateTime now = LocalDateTime.now();
	    
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
		
		//クリック数
		item.setClickNum(item.getClickNum() + 1);
		
		//ItemClicログ
		ItemClick ic = new ItemClick();
		//カラムを追加
		ic.setItemId(item.getId());
		ic.setDate(now);
		
		// データベースに保存
		icRep.save(ic);
        itemRep.save(item);
         
      //ヘッダージャンル
    	List<Genre> genreAll = genreRep.findAll();
    	
    	//モデルを追加
    	model.addAttribute("genreAll", genreAll);
	    
	    // Modelにitemを追加
	    model.addAttribute("item", item);

	    // 指定のitem詳細ページを表示
	    return "item/item"; // ビューの名前を返す
	}
	
		
}
