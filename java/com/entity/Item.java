package com.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Item {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    
    //タイトル
    @Column
    private String title;
    
    //コンテンツ
    @Column
    private String content;
    
    //投稿日時
    @Column(name = "new_datetime")
    private LocalDateTime newDatetime;
    
    //編集日時
    @Column(name = "edit_datetime")
    private LocalDateTime editDatetime;
    
    //ジャンルID
    @Column(name = "genre_id")
    private int genreId;
    
    //クリック数
    @Column(name = "click_num")
    private int clickNum;
    
  //(mysqlのカラムにない一時的データ)
  	@Transient 
  	private String genreName;
  	
  //(mysqlのカラムにない一時的データ)
  	@Transient
  	private String simage;
    
	//(mysqlのカラムにない一時的データ)
  	@Transient 
  	private String jnewDatetime;
  	
  	 //(mysqlのカラムにない一時的データ)
  	@Transient 
  	private String jeditDatetime;
  	
  	public String getSimage() {
		return simage;
	}

	public void setSimage(String simage) {
		this.simage = simage;
	}

	public int getClickNum() {
		return clickNum;
	}

	public void setClickNum(int clickNum) {
		this.clickNum = clickNum;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	public String getJnewDatetime() {
		return jnewDatetime;
	}

	public void setJnewDatetime(String jnewDatetime) {
		this.jnewDatetime = jnewDatetime;
	}

	public String getJeditDatetime() {
		return jeditDatetime;
	}

	public void setJeditDatetime(String jeditDatetime) {
		this.jeditDatetime = jeditDatetime;
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

	public LocalDateTime getNewDatetime() {
		return newDatetime;
	}

	public void setNewDatetime(LocalDateTime newDatetime) {
		this.newDatetime = newDatetime;
	}

	public LocalDateTime getEditDatetime() {
		return editDatetime;
	}

	public void setEditDatetime(LocalDateTime editDatetime) {
		this.editDatetime = editDatetime;
	}

	public int getGenreId() {
		return genreId;
	}

	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}

	public Item orElse(Object object) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

    
}
