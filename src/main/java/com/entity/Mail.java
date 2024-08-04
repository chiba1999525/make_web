package com.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mail {
    private String title;
    private String email;
    private String body;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}