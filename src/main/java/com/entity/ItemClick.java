package com.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity(name = "item_click")
public class ItemClick {
	
	@Id
	@Column
	private int id;
	
	@Column(name = "item_id")
	private int itemId;
	
	@Column
	private LocalDateTime date;
	
	//(mysqlのカラムにない一時的データ)
    //日本時間
  	@Transient 
  	private String datej;
  	
  //(mysqlのカラムにない一時的データ)
    //日本時間
  	@Transient 
  	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getDatej() {
		return datej;
	}

	public void setDatej(String datej) {
		this.datej = datej;
	}


}
