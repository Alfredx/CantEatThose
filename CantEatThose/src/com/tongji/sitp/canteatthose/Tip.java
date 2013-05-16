package com.tongji.sitp.canteatthose;

import java.io.Serializable;

public class Tip implements Serializable{
	private static final long serialVersionUID = -7060210544600464477L;
	private Food food_;
	private String description_;
	public Tip(Food food, String description){
		this.food_ = food;
		this.description_ = description;
	}
	public Tip(String description){
		this.description_ = description;
		this.food_ = null;
	}
	public int id(){
		return food_.id();
	}
	public String type(){
		return food_.type();
	}
	public String description(){
		return description_;
	}
}
