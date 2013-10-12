package com.tongji.sitp.canteatthose;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.tjerkw.slideexpandable.library.ActionSlideExpandableListView;
import com.tongji.sitp.canteatthose.database.DBData;
import com.tongji.sitp.canteatthose.database.DBManager;
import com.tongji.sitp.canteatthose.verticalgallery.VerticalGalleryAdapterView;

public class ChooseActivity extends Activity {
	private Gallery gallery_horizon;
	private Gallery gallery_vertical;
	private ActionSlideExpandableListView list;
	public static DBManager manager;
	public static HashMap<Integer, Integer> collectedData;
//	private static HashMap<Integer, SimpleAdapter> hashmap_foods;
	public static ArrayList<FoodType> types;
	public static HashMap<String, FoodType> type_set;
	public static ArrayList<Food> foods;
	private TextView text;
	private final Bundle bundle = new Bundle();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose);
//		hashmap_foods = new HashMap<Integer, SimpleAdapter>();
		collectedData = new HashMap<Integer, Integer>();
		DBData d = (DBData)getIntent().getSerializableExtra(getString(R.string.DBDATA_SER_KEY));
		if(d == null)
			manager = new DBManager(this);
		else
			manager = new DBManager(this,d);
		
		types = (ArrayList<FoodType>)manager.queryTypes();
		type_set = new HashMap<String, FoodType>();
		for(FoodType type:types){
			type_set.put(type.name(), type);
		}
		list = (ActionSlideExpandableListView) findViewById(R.id.actionSlideExpandableListView1);
		SimpleAdapter adapter = new SimpleAdapter(this, getTypeData(), R.layout.expandable_list_item, new String[]{"img","title"}, new int[]{R.id.imageView1,R.id.expandable_toggle_text});
		new Task(list,adapter).execute();
		
		
		
		final Button button_cantfind = (Button) findViewById(R.id.button_cantfind);
		final Button button_submit = (Button) findViewById(R.id.button_submit);
		button_cantfind.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ChooseActivity.this, ChooseByNameActivity.class);
				bundle.putSerializable(getString(R.string.RESULT_SER_KEY), collectedData);
				bundle.putSerializable(getString(R.string.DBDATA_SER_KEY), manager.getData());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		button_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(collectedData.isEmpty()){
					Toast.makeText(ChooseActivity.this,
								   "您还没有选择任何食物呢！\n今天不吃东西了吗？ :)", 
								   Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = new Intent(ChooseActivity.this, ResultActivity.class);
				bundle.putSerializable(getString(R.string.RESULT_SER_KEY), collectedData);
				bundle.putSerializable(getString(R.string.DBDATA_SER_KEY), manager.getData());
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
			}
		});
		
	}
	private List<Map<String, Object>> getTypeData(){
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		Object[] imgs = {
				R.drawable.type1,
				R.drawable.type2,
				R.drawable.type3,
				R.drawable.type4,
				R.drawable.type5,
				R.drawable.type6,
				R.drawable.type7,
				R.drawable.type8,
				R.drawable.type9,
				R.drawable.type10,
				R.drawable.type11
		};
		final int size = imgs.length;
		for(int i = 0; i < size; ++i){
			map = new HashMap<String,Object>();
			map.put("img", imgs[i]);
			map.put("title",types.get(i).name());
			list.add(map);
		}	
		
		return list;
	}
//	public static SimpleAdapter getSubListAdapter(Context context,int typeid){
//		ArrayList<Object> imgs = new ArrayList<Object>();
//		foods = (ArrayList<Food>)manager.queryFoodByTypeId(typeid);
//		if(hashmap_foods.containsKey(typeid))
//			return hashmap_foods.get(typeid);
//		else{
//			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//			for(Food food : foods){
//				boolean flag = false;
//				Field[] fields = R.drawable.class.getDeclaredFields();
//				for(Field field : fields){
//					if(field.getName().equals("f_"+food.id())){
//						try{
//							imgs.add(field.getInt(R.drawable.class));
//						}catch(IllegalAccessException e){
//							e.printStackTrace();
//						}
//						flag = true;
//						break;
//					}
//				}
//				if(!flag)
//					imgs.add(R.drawable.f_0);
//			}
//			final int size = foods.size();
//			Map<String, Object> map;
//			for(int i = 0; i < size; ++i){
//				map = new HashMap<String, Object>();
//				map.put("img", imgs.get(i));
//				map.put("title", foods.get(i).name());
//				list.add(map);
//			}
//			SimpleAdapter sa = new SimpleAdapter(context,list,R.layout.sub_listview,new String[]{"img","title"},new int[]{R.id.sublistview_img,R.id.sublistview_text});
//			hashmap_foods.put(typeid, sa);
//			return sa;
//		}
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose, menu);
		return true;
	}
	
	protected void onDestory(){
		manager.closeDB();
		super.onDestroy();
	}
	
	private class Task extends AsyncTask<Integer, Void, Void>{
		private ActionSlideExpandableListView list;
		private SimpleAdapter adapter;
		public Task(ActionSlideExpandableListView list, SimpleAdapter adapter) {
			this.list = list;
			this.adapter = adapter;
		}
		@Override
		protected Void doInBackground(Integer... params) {
			// TODO Auto-generated method stub

			return null;
		}
		@Override
		protected void onPostExecute(Void result){
			super.onPostExecute(result);
			list.setAdapter(adapter,R.id.expandable_toggle_text);
			list.setItemActionListener(new ActionSlideExpandableListView.OnActionClickListener() {

				@Override
				public void onClick(View listView, View buttonview, int position) {

				}
			},R.id.expandable_toggle_text);
		}
	}
	
	
}
