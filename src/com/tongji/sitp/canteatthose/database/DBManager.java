package com.tongji.sitp.canteatthose.database;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import com.tongji.sitp.canteatthose.FoodType;
import com.tongji.sitp.canteatthose.Food;
import com.tongji.sitp.canteatthose.Tip;

public class DBManager implements Serializable{
	private DBHelper helper;
	private SQLiteDatabase db;
	private DBData data;
	
	private static final long serialVersionUID = -7060210544600464481L;
	
	public DBManager(Context context){
		helper = new DBHelper(context);
		data = new DBData();
		db = helper.getDatabase();
		this.queryTypes();
		this.queryFoodByTypeId(1);	//preload some data
	}
	
	public DBManager(Context context, DBData data){
		helper = new DBHelper(context);
		this.data = data;
		db = helper.getDatabase();
	}
	
	public void closeDB(){
		db.close();
	}
	
	public DBData getData(){
		return data;
	}
	
	public String[] queryGood(int id1, int id2){
		Cursor cursor = query("SELECT a.food_name, b.food_name, Description " +
							  "from Food as a, Food as b ,GoodCollocation " +
							  "where a.id="+id1+" and b.id="+id2+" and ((Food1_id="+id1+" and Food2_id="+id2+") or (Food1_id="+id2+" and Food2_id="+id1+"));");
		String[] str = null;
		try{
			cursor.moveToFirst();
			str = new String[]{cursor.getString(0),cursor.getString(1),cursor.getString(2)};
		}catch(CursorIndexOutOfBoundsException e){
			e.printStackTrace();
		}
		cursor.close();
		return str;
	}
	
	public String[] queryBad(int id1, int id2){
		Cursor cursor = query("SELECT a.food_name, b.food_name, Description " +
							  "from Food as a, Food as b ,BadCollocation " +
							  "where a.id="+id1+" and b.id="+id2+" and ((Food1_id="+id1+" and Food2_id="+id2+") or (Food1_id="+id2+" and Food2_id="+id1+"));");
		String[] str = null;
		try{
			cursor.moveToFirst();
			str = new String[]{cursor.getString(0),cursor.getString(1),cursor.getString(2)};
		}catch(CursorIndexOutOfBoundsException e){
			e.printStackTrace();
		}
		cursor.close();
		return str;
	}
	
	public List<FoodType> queryTypes(){
		if(!data.getTypes().isEmpty()){
			return data.getTypes();
		}
		Cursor cursor = foodtypeCursor();
		while(cursor.moveToNext()){
			data.getTypes().add(new FoodType(cursor.getInt(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("type_name"))));
		}
		cursor.close();
		return data.getTypes();
	}
	
	public List<Food> queryFoodByTypeId(int typeid){
		if(data.getFood(typeid) != null && !data.getFood(typeid).isEmpty()){
			return data.getFood(typeid);
		}
		ArrayList<Food> foods = new ArrayList<Food>();
		Cursor cursor = foodCursorByTypeId(String.valueOf(typeid));
		AddFoodFromCursor(cursor, foods);
		cursor.close();
		data.putFood(typeid, foods);
		return foods;
	}
	
	public List<Food> queryFoodByTypeName(String name){
		int typeid = getFoodTypeIdByTypeName(name);
		return queryFoodByTypeId(typeid);
	}
	
	protected void AddFoodFromCursor(Cursor cursor, List<Food> foods){
		while(cursor.moveToNext()){
			FoodType type = new FoodType(cursor.getInt(cursor.getColumnIndex("FoodType_id")),
										 cursor.getString(cursor.getColumnIndex("type_name")));
			Food food = new Food(cursor.getString(cursor.getColumnIndex("food_name")),
								 cursor.getInt(cursor.getColumnIndex("id")),
								 type);
			foods.add(food);
		}
	}
	
	public List<Tip> queryTips(){
		if(!data.getTips().isEmpty()){
			return data.getTips();
		}
		Cursor cursor = tipCursor();
		while(cursor.moveToNext()){
			data.getTips().add(new Tip(cursor.getInt(0),
									   cursor.getInt(1),
									   cursor.getString(2),
									   cursor.getString(3),
									   cursor.getString(cursor.getColumnIndex("Description"))));
		}
		cursor.close();
		return data.getTips();
		
	}
	
	public Cursor query(String sql){
		return db.rawQuery(sql, null);
	}
	protected int getFoodTypeIdByTypeName(String name){
		Cursor cursor = db.rawQuery("SELECT id FROM FoodType WHERE type_name=\""+name+"\";", null);
		cursor.moveToFirst();
		return cursor.getInt(cursor.getColumnIndex("id"));
	}
	protected Cursor foodtypeCursor(){
		return db.rawQuery("SELECT * FROM FoodType;", null);
	}
	protected Cursor foodCursorByTypeId(String id){
		return db.rawQuery("SELECT Food.*, FoodType.type_name " +
						   "from Food,FoodType " +
						   "where Food.FoodType_id = FoodType.id and FoodType.id = "+id+";", null);
	}
	protected Cursor foodCursorByTypeName(String name){
		return db.rawQuery("SELECT Food.*, FoodType.type_name " +
						   "from Food,FoodType " +
						   "where Food.FoodType_id = FoodType.id and FoodType.type_name = \""+name+"\";", null);
	}
	protected Cursor tipCursor(){
		return db.rawQuery("SELECT Tips.Food1_id, Tips.Food2_id, a.food_name, b.food_name, Tips.Description " +
						   "FROM Tips, Food as a, Food as b " +
						   "WHERE a.id = Food1_id and b.id = Food2_id;", null);
	}
}
