package com.controller.inquiry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.entity.Genre;
import com.entity.Inquiry;
import com.repository.GenreRepository;
import com.repository.InquiryRepository;

@Controller
public class EditInquiryController {
	
	//inquiryテーブル用のリポジトリを定義
	@Autowired private InquiryRepository inquiryRep;
	//メールメソッド
    @Autowired private MailSender mailSender;
    //ジャンルリポジトリ
    @Autowired private GenreRepository genreRep;
	
	//お問い合わせページ表示処理
	@RequestMapping("edit_inquiry")
	public String edit_inquiry (
			Model model
			) {
		//Inquiryインスタンス生成
		Inquiry inquiry = new Inquiry();
		
		//ヘッダージャンル
    	List<Genre> genreAll = genreRep.findAll();
    	
    	//モデルを追加
    	model.addAttribute("genreAll", genreAll);
		
		//inquriをモデルに追加
		model.addAttribute("inquiry", inquiry);
		
		return "inquiry/edit_inquiry";
	}
	
	//お問い合わせ登録処理
	@RequestMapping("disp_edit_inquiry")
	public String disp_edit_inquiry(
			HttpServletRequest req,			
			//POST時に自動的に入力チェック
			@Validated @ModelAttribute Inquiry inquiry,
			//リダイレクト
			RedirectAttributes redirectAttrs,
			//データベース
			Model model) {
		
		//エラーメッセージのリストを定義
    	List<String> errorMessages = new ArrayList<String>();
    	//保存成功メッセージの定義
    	String successMessages = "送信完了しました！";
		
		//入力チェック
    	//名前チェック
		if (inquiry.getName().isEmpty()) {
			String name = "名前を入力してください";
			errorMessages.add(name);
		}
		
		//連絡先チェック
		if (inquiry.getContent().isEmpty()) {
			String address = "連絡先を入力してください";
			errorMessages.add(address);
		}
    			
    	//タイトルチェック
		if (inquiry.getTitle().isEmpty()) {
			String title = "タイトルを入力してください";
			errorMessages.add(title);
		}
		
		//お問い合わせ内容チェック
		if (inquiry.getContent().isEmpty()) {
			String content = "お問い合わせ内容を入力してください";
			errorMessages.add(content);
		}

		//エラー処理
		if (!errorMessages.isEmpty()) {
            redirectAttrs.addFlashAttribute("errorMessages", errorMessages);
            return "redirect:/edit_inquiry?id=" + inquiry.getId();
        }
		
		//現在の日時情報を取得
	    LocalDateTime now = LocalDateTime.now();
		//投稿時間を挿入
		inquiry.setDate(now);
		//データベースに保存
		inquiryRep.save(inquiry);

		// メール送信処理を追加
//        sendMail(inquiry);
		
		// 保存成功メッセージ
        redirectAttrs.addFlashAttribute("success", successMessages);
		
		return "redirect:/edit_inquiry";
	} 
	
    
    // 登録処理後のメール送信メソッド
    private void sendMail(Inquiry inquiry) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("y.chiba@ebacorp.jp");
        message.setTo("ychibavolleyball@gmail.com");  // 実際の宛先に変更してください
        message.setSubject(inquiry.getTitle());
        message.setText("お問い合わせ内容:\n\n" +
                        "名前: " + inquiry.getName() + "\n" +
                        "連絡先: " + inquiry.getAddress() + "\n" +
                        "内容: " + inquiry.getContent());

        // メール送信
        mailSender.send(message);
    }
}
