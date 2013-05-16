package com.tongji.sitp.canteatthose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
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
	private DBManager manager;
	private ArrayList<FoodType> types;
	private ArrayList<Food> foods;
	private TextView text;
	private final Bundle bundle = new Bundle();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose);
		DBData d = (DBData)getIntent().getSerializableExtra(getString(R.string.DBDATA_SER_KEY));
		if(d == null)
			manager = new DBManager(this);
		else
			manager = new DBManager(this,d);
		
		types = (ArrayList<FoodType>)manager.queryTypes();
		list = (ActionSlideExpandableListView) findViewById(R.id.actionSlideExpandableListView1);
		SimpleAdapter adapter = new SimpleAdapter(this, getTypeData(), R.layout.expandable_list_item, new String[]{"img","title"}, new int[]{R.id.imageView1,R.id.expandable_toggle_text});
		list.setAdapter(adapter,R.id.expandable_toggle_text);
		list.setItemActionListener(new ActionSlideExpandableListView.OnActionClickListener() {

			@Override
			public void onClick(View listView, View buttonview, int position) {

				/**
				 * Normally you would put a switch
				 * statement here, and depending on
				 * view.getId() you would perform a
				 * different action.
				 */
//				String actionName = "";
//				if(buttonview.getId()==R.id.buttonA) {
//					actionName = "buttonA";
//				} else {
//					actionName = "ButtonB";
//				}
//				/**
//				 * For testing sake we just show a toast
//				 */
//				Toast.makeText(
//					ChooseActivity.this,
//					"Clicked Action: "+actionName+" in list item "+position,
//					Toast.LENGTH_SHORT
//				).show();
				
				ListView sublist = (ListView) findViewById(R.id.sublist);
				SimpleAdapter subadapter = new SimpleAdapter(ChooseActivity.this, getSubListData(position+1),R.layout.sub_listview, new String[]{"img","title"}, new int[]{R.id.sublistview_img,R.id.sublistview_text});
				sublist.setAdapter(subadapter);
				setContentView(sublist);
			}

		// note that we also add 1 or more ids to the setItemActionListener
		// this is needed in order for the listview to discover the buttons
		}, R.id.buttonA, R.id.buttonB);
		
		final Button button_cantfind = (Button) findViewById(R.id.button_cantfind);
		final Button button_submit = (Button) findViewById(R.id.button_submit);
		button_cantfind.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ChooseActivity.this, ChooseByNameActivity.class);
				//TODO: intent's content!
				bundle.putSerializable(getString(R.string.DBDATA_SER_KEY), manager.getData());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		button_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ChooseActivity.this, ResultActivity.class);
				//TODO: intent's content!
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
	private List<Map<String, Object>> getSubListData(int typeid){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		//TODO read imgs from file;
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
		foods = (ArrayList<Food>)manager.queryFoodByTypeId(typeid);
		final int size = foods.size();
		for(int i = 0; i < size; ++i){
			map = new HashMap<String, Object>();
			map.put("img", imgs[0]);
			map.put("title", foods.get(i).name());
			list.add(map);
		}
		return list;
	}

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
	
	private class OnHorizontalGalleryItemSelectedListener implements OnItemSelectedListener{
		private int lastposition;
		public OnHorizontalGalleryItemSelectedListener(){
			lastposition = types.size()-1;
		}
		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int position, long id){
			new Task().execute(position,lastposition);
			lastposition = position;
		}
		@Override
		public void onNothingSelected(AdapterView<?> parent){
			
		}
		
		private class Task extends AsyncTask<Integer, Void, Void>{
			private String pret;
			private String t;
			private String aftt;
			int position;
			int lastposition;
			@Override
			protected Void doInBackground(Integer... params) {
				// TODO Auto-generated method stub
				position = params[0];
				lastposition = params[1];
				if(pret != null && t != null && aftt != null){
					if((lastposition+12)%11 == position){
						pret = t;
						t = aftt;
						aftt = "";
						foods = (ArrayList<Food>)manager.queryFoodByTypeId(((position%11)+12)%11+1);
						for(Food food : foods){
							aftt += (food.name() + ";");
						}
					}
					else{
						aftt = t;
						t = pret;
						pret = "";
						foods = (ArrayList<Food>)manager.queryFoodByTypeId((position%11+10)%11+1);
						for(Food food : foods){
							pret += (food.name() + ";");
						}
					}
				}
				else{
					pret = "";
					t = "";
					aftt = "";
					foods = (ArrayList<Food>)manager.queryFoodByTypeId((position%11)+1);
					for(Food food : foods){
						t += (food.name() + ";");
					}
					foods = (ArrayList<Food>)manager.queryFoodByTypeId((position%11+10)%11+1);
					for(Food food : foods){
						pret += (food.name() + ";");
					}
					foods = (ArrayList<Food>)manager.queryFoodByTypeId(((position%11)+12)%11+1);
					for(Food food : foods){
						aftt += (food.name() + ";");
					}
				}
				return null;
			}
			@Override
			protected void onPostExecute(Void result){
				super.onPostExecute(result);
				text.setText(t);
			}
		}
		
	}
	
	private class OnVerticalGalleryItemSelectedListener implements VerticalGalleryAdapterView.OnItemSelectedListener{
		public OnVerticalGalleryItemSelectedListener() {
			// TODO Auto-generated constructor stub
		}
		@Override
		public void onItemSelected(VerticalGalleryAdapterView<?> parent,
				View view, int position, long id) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onNothingSelected(VerticalGalleryAdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	}
	private class OnVerticalGalleryItemClickListener implements VerticalGalleryAdapterView.OnItemClickListener{
		public OnVerticalGalleryItemClickListener() {
			// TODO Auto-generated constructor stub
		}
		@Override
		public void onItemClick(VerticalGalleryAdapterView<?> parent,
				View view, int position, long id) {
			// TODO Auto-generated method stub
			
		}
	}
}
