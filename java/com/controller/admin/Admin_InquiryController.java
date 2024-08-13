package com.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Inquiry;
import com.repository.InquiryRepository;
import com.rule.Rule;

@Controller
public class Admin_InquiryController {
	
	@Autowired private InquiryRepository inquiryRep;
	
	//お問い合わせ一覧画面
	@RequestMapping("admin/inquirys")
	public String inquirys(
			Model model
			){
		
		//inquiryデータをすべて取り出す
		List<Inquiry> inquiryAll = inquiryRep.findAll();
		
		for (Inquiry inq : inquiryAll) {
			// 投稿時間を日本時間に変換
	        String n_japantime =  Rule.return_japantime2(inq.getDate());
			inq.setDatej(n_japantime);

		}
		
		if (inquiryAll.size() != 0) {
			//すべてのinquiryデータをmodelに追加
			model.addAttribute("inquiryAll",  inquiryAll);
			
		}

		return "admin/inquiry/inquirys";
	}
	
	
	//お問い合わせ詳細画面
	@RequestMapping("/admin/inquiry/{id}")
	public String inquiry(
			@PathVariable("id") int id,
			Model model
			) {
		
		//指定のidのinquiryを定義
		Inquiry inquiry = inquiryRep.findById(id).orElse(null);
		
		//既読チェック
		if (inquiry.getCheck() == 0) {
			//既読にする
			inquiry.setCheck(1);
		}
		
		// 投稿時間を日本時間に変換
        String n_japantime =  Rule.return_japantime2(inquiry.getDate());
		inquiry.setDatej(n_japantime);
		
		//セーブ
		inquiryRep.save(inquiry);
		
		//モデルにinquiryを追加
		model.addAttribute("inquiry", inquiry);
		
		return "/admin/inquiry/inquiry";
	}
	
	//お問い合わせ削除処理
	@RequestMapping("/delete_inquiry/{id}")	
    public String delete_inquiry(
    		@PathVariable("id") int id
    		) {
		
        // 指定されたIDのItemレコードを削除
        inquiryRep.deleteById(id);
        // 一覧画面にリダイレクト
        return "redirect:/admin/inquirys";
    }

}
