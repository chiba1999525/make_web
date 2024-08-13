package com.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Inquiry {
	
	@Id
	@Column
	private int id;
	
	//タイトル
    @Column
    private String title;
    
    //コンテンツ
    @Column
    private String content;
    
    //名前
    @Column
    private String name;
    
    //連絡先
    @Column
    private String address;
    
    //既読チェック
    @Column(name = "inquiry_check")
    private int check;
    
    //投稿時間
    @Column
    private LocalDateTime date;
    
     //(mysqlのカラムにない一時的データ)
    //日本時間
  	@Transient 
  	private String datej;

	public int getCheck() {
		return check;
	}

	public void setCheck(int check) {
		this.check = check;
	}

	public String getDatej() {
		return datej;
	}

	public void setDatej(String datej) {
		this.datej = datej;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

}
