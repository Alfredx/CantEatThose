package com.tongji.sitp.canteatthose;

import java.io.Serializable;

public class Tip implements Serializable{
	private static final long serialVersionUID = -7060210544600464477L;
	private int food1_id;
	private int food2_id;
	private String food1_name;
	private String food2_name;
	
	
	private String description_;
	
	public Tip(int food1_id, int food2_id, String food1_name, String food2_name , String description){
		this.description_ = description;
		this.food1_id = food1_id;
		this.food2_id = food2_id;
		this.food1_name = food1_name;
		this.food2_name = food2_name;
	}

	public String description(){
		return description_;
	}
	public int getFood1_id() {
		return food1_id;
	}
	public int getFood2_id() {
		return food2_id;
	}
	public String getFood1_name(){
		return food1_name;
	}
	public String getFood2_name(){
		return food2_name;
	}

}
