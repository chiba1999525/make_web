package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entity.Admin;
import com.repository.AdminRepository;

@Controller
public class AdminSignController {
	
	//adminテーブル用のリポジトリを定義
	@Autowired private AdminRepository adminRep;
	
	Admin admin = new Admin();
	
	//ログイン画面表示処理
	@RequestMapping("/admin_sign")
	public String admin_sign(
			Model model
			) {

		model.addAttribute("admin",admin);
		
		return "admin_sign";
	}
	
	//ログイン処理
	@RequestMapping("/disp_admin_sign")
	public String disp_admin_sign(
			HttpServletRequest req,			
			//POST時に自動的に入力チェック
			@Validated @ModelAttribute Admin admin,
		    //エラー内容表示(modelの隣に記述)
			BindingResult result,
			//リダイレクト
			RedirectAttributes redirectAttrs,
			//データベース
			Model mode
			) {
		
		//入力されたデータを取り出す
		String admin_id = admin.getAdminId();
		String admin_pass = admin.getAdminPassword();
		
		//admin全データを取り出す
		List<Admin> adminAll = adminRep.findAll();
		
		//adminレコードを繰り返し処理する
		for (Admin adminList : adminAll) {
			
			if (adminList.getAdminId().equals(admin_id)  &&  
					adminList.getAdminPassword().equals(admin_pass)) {
				return "top";
			}
				
			
		}
		
		return "redirect:/admin_sign";
	}

}
