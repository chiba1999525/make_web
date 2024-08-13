package com.controller.admin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Item;
import com.entity.ItemClick;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.repository.ItemClickRepository;
import com.repository.ItemRepository;
import com.rule.Rule;


@Controller
public class Admin_ItemClickController {
	
	//ItemClickリポジトリを定義
	@Autowired private ItemClickRepository icRep;
	//Itemリポジトリを定義
	@Autowired private ItemRepository itemRep;
	
	//クリックページ表示処理
	@RequestMapping("admin/item_click")
	public String item_click(
			Model model
			) throws JsonProcessingException {
		
		//ItemClickをすべて取り出す
		List<ItemClick> icAll = icRep.findAll();
		//本日のアクセルレコードのリストを定義
		List<ItemClick> ic_today = new ArrayList<>();
		
		//本日の日付
		LocalDate nowDate =  LocalDate.now();
		//日付ごとにデータを入れる
		int click_all = icAll.size();
		int today = 0;
		int yesterday = 0;
		int days_ago3 = 0;
		
		//icリストを新しい時間に降順する
		icAll.sort(Comparator.comparing(ItemClick::getDate).reversed());
		
		for (ItemClick ic : icAll) {	
			
			// 投稿時間を日本時間に変換
	        String n_japantime =  Rule.return_japantime2(ic.getDate());
			ic.setDatej(n_japantime);
			
			//item_idをタイトル名に変換
			Item item_= itemRep.findById(ic.getItemId()).orElse(null);
			ic.setTitle(item_.getTitle());
			
			// 日時を日付に変換
		    LocalDate icd = ic.getDate().toLocalDate();
			
			//本日のアクセス数取得
			if (nowDate.isEqual(icd)) {
				today++;
				ic_today.add(ic);
				continue;
		    }
			//昨日のアクセス数取得
			if(nowDate.minusDays(1).isEqual(icd)) {
				yesterday++;
				continue;
			}
			//三日前のアクセス数取得
			if(nowDate.minusDays(2).isEqual(icd)) {
				days_ago3++;
				continue;
			}
			
			break;
		}
		
        model.addAttribute("click_all", click_all);
        model.addAttribute("today", today);
        model.addAttribute("yesterday", yesterday);
        model.addAttribute("days_ago3", days_ago3);
		
		//ItemClickをモデルに追加
		model.addAttribute("ic_today", ic_today);
		
		return "admin/item_click";
	}


}
