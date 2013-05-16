package com.tongji.sitp.canteatthose;

import java.io.Serializable;

import com.tongji.sitp.canteatthose.FoodType;

public class Food implements Serializable{
	private static final long serialVersionUID = -7060210544600464480L;
	public Food(String name, int id, FoodType type){
		this.name_ = name;
		this.id_ = id;
		this.type_ = type;
	}
	private int id_;
	private String name_;
	private FoodType type_;
	
	public String name(){
		return name_;
	}
	public int id(){
		return id_;
	}
	public int typeid(){
		return type_.id();
	}
	public String type(){
		return type_.name();
	}
}
