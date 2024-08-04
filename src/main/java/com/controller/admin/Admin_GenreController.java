package com.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entity.Genre;
import com.repository.GenreRepository;

@Controller
public class Admin_GenreController {
	
	//genreテーブル用のリポジトリを定義
	@Autowired private GenreRepository genreRep;
	//genreインスタンス生成
	Genre genre = new Genre();
	
	//ジャンルページ表示処理
	@RequestMapping("/admin/genre")
	public String genre(
			@RequestParam(name = "id", required = false, defaultValue = "0") int id,
			Model model
			) {
		
	    List<Genre> genreAll = genreRep.findAll();
		
		//更新の場合
		if (id != 0) {				
			//更新　指定のレコードが入る
			for (Genre genreall : genreAll) {
		    	
	            if (genreall.getId() == id ) {
	            	// 画像ファイル
		            byte[] image = genreall.getImage();
	                // Base64エンコード
	                String encodedImage = Base64.getEncoder().encodeToString(image);
	                genre.setSimage(encodedImage);
	            }
		    }
		}

		//新規用フィール
		model.addAttribute("genre", genre);
		
		//データベースからすべてのgenreデータを取得
	    for (Genre genre : genreAll) {
	    	// 画像ファイル
            byte[] image = genre.getImage();
            if (image != null) {
                // Base64エンコード
                String encodedImage = Base64.getEncoder().encodeToString(image);
                genre.setSimage(encodedImage);
            }
	    }
		//モデルに追加
		model.addAttribute("genreAll", genreAll);
		
		return "admin/genre/genre";
	}
	
	//genre登録・編集処理
	@RequestMapping("/disp_genre")
	public String desp_genre(
			//POST時に自動的に入力チェック
			@Validated @ModelAttribute Genre genre,
			//ファイルアップロード
    		@RequestParam("file") MultipartFile file,
		    //エラー内容表示(modelの隣に記述)
			BindingResult result,
			//リダイレクト
			RedirectAttributes redirectAttrs,
			Model model
			) {
		
		//エラーメッセージのリストを定義
    	List<String> errorMessages = new ArrayList<String>();
    	//保存成功メッセージの定義
    	List<String> successMessages = new ArrayList<String>();
		
		//ジャンル名チェック
		if (genre.getGenreName().isEmpty()) {
			errorMessages.add("ジャンル名を入力してください");
		}
		
		//imageチェック
		if (file == null || file.isEmpty()) {
			errorMessages.add("imageを選択してください");
		}
		
		//エラー処理
		if (!errorMessages.isEmpty()) {
            redirectAttrs.addFlashAttribute("errorMessages", errorMessages);
            return "redirect:admin/genre?id=" + genre.getId();
        }
				
		
		//ファイルアップロード
	    try {
	    	 // MultipartFileからバイト配列を取得
            byte[] genre_byte = file.getBytes();
            //配列をcustomerテーブルに紐づける
        	genre.setImage(genre_byte);	    	
	    } catch (IOException e)  {
	    	// エラーが発生した場合の処理を記述
	        e.printStackTrace(); // エラー情報を出力
	    }
		
		//データベースに保存
		genreRep.save(genre);
		
		//データベースからすべてのgenreデータを取得
	    List<Genre> genreAll = genreRep.findAll();
		
		//モデルに追加
		model.addAttribute("genreAll", genreAll);
		 
		return "redirect:/admin/genre";
	}
	
	//genre削除処理
	@RequestMapping("/delete_genre/{id}")	
	//@RequestMapping("/delete_genre")
    public String delete_genre(
    		@PathVariable("id") int id,
    		//リダイレクト
			RedirectAttributes redirectAttrs
    		) {
		
        // 指定されたIDのgenreレコードを削除
        genreRep.deleteById(id);
        // 一覧画面にリダイレクト
        return "redirect:/admin/genre";
    }
	
	

}
