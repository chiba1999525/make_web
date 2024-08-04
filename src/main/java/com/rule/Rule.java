package com.rule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;

@Controller
public class Rule {

	//日付を日本版に変換
	public  static String return_japantime(LocalDateTime time) {
		
		if (time == null) {
	        return "";
	    }
		
		// フォーマッターを定義（日本語の年-月-日形式）
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        // LocalDateTime をフォーマット
        String japantime = time.format(formatter);  
        // フォーマットした日付をモデルに追加
        return japantime;	
	}
	
	//日付・時間を日本版に変換
		public  static String return_japantime2(LocalDateTime time) {
			
			if (time == null) {
		        return "";
		    }
			
			// フォーマッターを定義（日本語の年-月-日形式）
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");
	        // LocalDateTime をフォーマット
	        String japantime2 = time.format(formatter);  
	        // フォーマットした日付をモデルに追加
	        return japantime2;	
		}

}
