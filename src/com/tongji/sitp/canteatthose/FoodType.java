package com.tongji.sitp.canteatthose;

import java.io.Serializable;

public class FoodType implements Serializable{
	private static final long serialVersionUID = -7060210544600464479L;
	private int id_;
	private String name_;
	public CharSequence listname = "";
	public FoodType(int id, String name){
		this.id_ = id;
		this.name_ = name;
	}
	public int id(){
		return id_;
	}
	public String name(){
		return name_;
	}
}

