package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "genre")
public class Genre {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;
	
	@Column(name = "genre_name")
	private String genreName;
	
	//ファイルアップロード機能
	@Column(name = "genre_image")
	private byte[] image;
	
	//(mysqlのカラムにない一時的データ)
  	@Transient 
  	private String simage;
    
	
	public String getSimage() {
		return simage;
	}

	public void setSimage(String simage) {
		this.simage = simage;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	public Genre orElse(Object object) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}


}
