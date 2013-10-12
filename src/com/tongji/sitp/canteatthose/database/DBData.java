package com.tongji.sitp.canteatthose.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.tongji.sitp.canteatthose.Food;
import com.tongji.sitp.canteatthose.FoodType;
import com.tongji.sitp.canteatthose.Tip;

public class DBData implements Serializable{
	private static final long serialVersionUID = -7060210544600464481L;
	private ArrayList<FoodType> types;
	private ArrayList<Tip> tips;
	private HashMap<Integer,ArrayList<Food>> foods_set;
	
	public DBData() {
		types = new ArrayList<FoodType>();
		tips = new ArrayList<Tip>();
		foods_set = new HashMap<Integer, ArrayList<Food>>();
	}
	
	public void setTypes(ArrayList<FoodType> types){
		this.types = types;
	}
	public ArrayList<FoodType> getTypes(){
		return types;
	}
	public void setTips(ArrayList<Tip> tips){
		this.tips = tips;
	}
	public ArrayList<Tip> getTips(){
		return tips;
	}
	public void putFood(int id,ArrayList<Food> foods){
		foods_set.put(id, foods);
	}
	public ArrayList<Food> getFood(int typeid){
		return foods_set.get(typeid);
	}
}
