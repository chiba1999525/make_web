package com.controller.admin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entity.Genre;
import com.entity.Item;
import com.repository.GenreRepository;
import com.repository.ItemRepository;

@Controller
public class Admin_Edit_ItemController {
	
	//editページ
	private static final String EDIT_ITEM = "/admin/item/edit_item";
	
	//itemテーブル用のリポジトリを定義
	@Autowired private ItemRepository itemRep;
	//genreテーブル用のリポジトリを定義
	@Autowired private GenreRepository genreRep;
	
	//edit_itemページの表示処理
	@RequestMapping("/admin/edit_item")
	public String edit_item(
		@RequestParam("id") int id, 
		Model model) {
		
		//Itemインスタンス生成を定義
		Item item = new Item();
		
		//更新の場合
		if (id != 0) {				
			//更新　指定のレコードが入る
			item = itemRep.findById(id).orElse(null);				
		}
		
		List<Genre> genreAll = genreRep.findAll();
		
		//genreデータを出力
		model.addAttribute("genreAll", genreAll);	
		// itemオブジェクトをモデルに追加
	    model.addAttribute("item", item);
	
		return EDIT_ITEM;
	}

	//新規・編集処理
	@RequestMapping(value = "/disp_edit_item", method = RequestMethod.POST)
	public String disp_edit_item(
			HttpServletRequest req,			
			//POST時に自動的に入力チェック
			@Validated @ModelAttribute Item item,
			//リダイレクト
			RedirectAttributes redirectAttrs,
			//ファイルアップロード
//    		@RequestParam("file") List<MultipartFile> fileList,
			//データベース
			Model model) {
		
	
		//エラーメッセージのリストを定義
    	List<String> errorMessages = new ArrayList<String>();
    	//保存成功メッセージの定義
    	List<String> successMessages = new ArrayList<String>();
		
		//入力チェック
    	//タイトルチェック
		if (item.getTitle().isEmpty()) {
			errorMessages.add("タイトルを入力してください");
		}
		
		//コンテンツチェック
		if (item.getContent().isEmpty()) {
			errorMessages.add("コンテンツを入力してください");
		} 
		
		if (item.getContent().length() > 65535 ) {
			errorMessages.add("65535文字以下で入力してください");
		}
		
		//ジャンルチェック
		if (item.getGenreId() == 0) {
			errorMessages.add("ジャンルを選択してください");
		}
		
		//エラー処理
		if (!errorMessages.isEmpty()) {
            redirectAttrs.addFlashAttribute("errorMessages", errorMessages);
            return "redirect:admin/edit_item?id=" + item.getId();
        } 
		
	    //現在の日時情報を取得
	    LocalDateTime now = LocalDateTime.now();
	    

	    //新規の場合
	    if (item.getId() == 0) {
	    	//投稿日時を挿入
			item.setNewDatetime(now);
			//クリック数デフォルト設定
			item.setClickNum(0);

			// データベースに保存
	        itemRep.save(item);
		//更新の場合
	    } else {
	    	// 更新処理の場合指定のレコードを取り出す
	        Item existingItem = itemRep.findById(item.getId()).orElse(null);
	        if (existingItem != null) {
	            // 更新日時を挿入
	            item.setEditDatetime(now);
	            // 既存のアイテムの情報を更新
	            existingItem.setTitle(item.getTitle());
	            existingItem.setContent(item.getContent());
	            existingItem.setGenreId(item.getGenreId());
	            existingItem.setEditDatetime(now);
	            // データベースに保存
	            itemRep.save(existingItem);
	        }
	    }
	   
	     // 保存成功メッセージ
        redirectAttrs.addFlashAttribute("success", successMessages);	        
           
	    /// 新規投稿または編集後の詳細ページにリダイレクト
	    return "redirect:/admin/items/";
		
	}
	
}
