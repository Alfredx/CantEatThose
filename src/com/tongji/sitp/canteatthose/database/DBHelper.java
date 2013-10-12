package com.tongji.sitp.canteatthose.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tongji.sitp.canteatthose.R;

public class DBHelper extends SQLiteOpenHelper implements Serializable{
	private static final long serialVersionUID = -7060210544600464480L;
	private Context context;
	private InputStream is;
	private FileOutputStream fos;
	private static final String DATABASE_NAME = "db_cet.s3db";
	private static final String SD_PATH = android.os.Environment.getExternalStorageDirectory().getPath() + "/CantEatThose";
	private static final int DATABASE_VERSION = 1;
	public DBHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
//		db.execSQL("CREATE TABLE IF NOT EXISTS FoodType"+
//					"(id INTEGER PRIMARY KEY AUTOINCREMENT NOTNULL, name VARCHAR);");
//		db.execSQL("CREATE TABLE IF NOT EXISTS Food"+
//					"(id INTEGER PRIMARY KEY AUTOINCREMENT NOTNULL, name VARCHAR, FoodType_id INTEGER PRIMARY KEY NOTNULL);");
//		db.execSQL("CREATE TABLE IF NOT EXISTS Tips"+
//					"(Food_id INTEGER PRIMARY KEY NOTNULL, FoodType_id INTEGER PRIMARY KEY NOTNULL, Description TEXT);");
//		db.execSQL("CREATE TABLE IF NOT EXISTS BadCollocation"+
//					"(Food1_id INTEGER PRIMARY KEY NOTNULL, Food2_id INTEGER PRIMARY KEY NOTNULL, " +
//					"FoodType1_id INTEGER PRIMARY KEY NOTNULL, FoodType2_id INTEGER PRIMARY KEY NOTNULL" +
//					"Description TEXT);");
//		db.execSQL("CREATE TABLE IF NOT EXISTS GoodCollocation"+
//					"(Food1_id INTEGER PRIMARY KEY NOTNULL, Food2_id INTEGER PRIMARY KEY NOTNULL, " +
//					"FoodType1_id INTEGER PRIMARY KEY NOTNULL, FoodType2_id INTEGER PRIMARY KEY NOTNULL" +
//					"Description TEXT);");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	public SQLiteDatabase getDatabase(){
		String final_path = "";
		try{
			final_path  = SD_PATH + "/" + DATABASE_NAME;
			File dir = new File(SD_PATH);
			File f = new File(final_path);
			if(!dir.exists())
				dir.mkdir();
//			if(!(f.exists())){
				f.createNewFile();
				is = context.getResources().openRawResource(R.raw.db_cet);
				fos = new FileOutputStream(f);
				byte[] buffer = new byte[7168];
				int count = 0;
				while((count = is.read(buffer)) > 0){
					fos.write(buffer, 0 ,count);
				}
				fos.close();
				is.close();
//			}
		}
		catch (Exception e){
			Log.e(SD_PATH, "error");
		}

		return SQLiteDatabase.openOrCreateDatabase(final_path, null);
	}
}
