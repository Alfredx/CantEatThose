package com.tongji.sitp.canteatthose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tongji.sitp.canteatthose.database.DBData;
import com.tongji.sitp.canteatthose.database.DBManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.ImageView.ScaleType;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;

public class ChooseByNameActivity extends Activity {
	private final Bundle bundle = new Bundle();
	private DBManager manager;
	HashMap<Integer,Integer> collectedData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_by_name);
		collectedData = (HashMap<Integer, Integer>)getIntent().getSerializableExtra(getResources().getString(R.string.RESULT_SER_KEY));
		DBData d = (DBData)getIntent().getSerializableExtra(getResources().getString(R.string.DBDATA_SER_KEY));
		manager = new DBManager(this,d);
		
		EditText editText = (EditText)findViewById(R.id.editText1);
		editText.addTextChangedListener(new SearchTextWatcher());
		
		final Button button_submit2 = (Button) findViewById(R.id.button_submit2);
		button_submit2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(collectedData.isEmpty()){
					Toast.makeText(ChooseByNameActivity.this,
								   "您还没有选择任何食物呢！\n今天不吃东西了吗？ :)", 
								   Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = new Intent(ChooseByNameActivity.this, ResultActivity.class);
				bundle.putSerializable(getString(R.string.RESULT_SER_KEY), collectedData);
				bundle.putSerializable(getResources().getString(R.string.DBDATA_SER_KEY), manager.getData());
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_by_name, menu);
		return true;
	}
	@Override
	protected void onDestroy(){
		manager.closeDB();
		super.onDestroy();
	}
	
	private class SearchTextWatcher implements TextWatcher{
		@Override
		public void afterTextChanged(Editable s) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Cursor cursor = manager.query("select id,food_name from Food where food_name like \"%"+s.toString()+"\";");
			while(cursor.moveToNext()){
				int id = cursor.getInt(0);
				String name = cursor.getString(1);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("img", id);
				map.put("name", name);
				list.add(map);
			}
			ListView listView = (ListView)findViewById(R.id.search_result_listView);
			listView.setAdapter(new MyListAdapater(list));
			
		}@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
		}
	}
	
private class MyListAdapater extends BaseAdapter{
		
		private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
				
		
		public MyListAdapater(List<Map<String, Object>> data) {
			super();
			this.data = data;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}
		
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
		}
		
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			final Map<String, Object> currentData = data.get(position);
			
			RelativeLayout relativeLayout = new RelativeLayout(ChooseByNameActivity.this);
			
			ImageView leftImage = new ImageView(ChooseByNameActivity.this);
			leftImage.setId(0x7f101);
			
			leftImage.setScaleType(ScaleType.FIT_XY);
			
			TextView textView = new TextView(ChooseByNameActivity.this);
			textView.setGravity(Gravity.CENTER);
			
			
			RelativeLayout.LayoutParams leftImageLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);		
			
			leftImageLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			textLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);		
			textLayoutParams.addRule(RelativeLayout.RIGHT_OF,leftImage.getId());
			
			try{
				leftImage.setImageResource(R.drawable.class.getField("f_"+String.valueOf(currentData.get("img"))).getInt(R.drawable.class));
			}catch(IllegalAccessException e){
				e.printStackTrace();
			}catch(NoSuchFieldException e){
				e.printStackTrace();
			}
			textView.setText((String)currentData.get("name"));
			textView.setLongClickable(false);
			textView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					collectedData.put((Integer)currentData.get("img"), (Integer)currentData.get("img"));
					ChooseActivity.collectedData.put((Integer)currentData.get("img"), (Integer)currentData.get("img"));
					Toast.makeText(
							ChooseByNameActivity.this,
							new StringBuilder(String.valueOf(currentData.get("name"))).append("已加入选择\n长按移出选择").toString(),
							Toast.LENGTH_SHORT
						).show();
					v.setClickable(false);
					v.setLongClickable(true);
				}
			});
			textView.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					collectedData.remove((Integer)currentData.get("img"));
					ChooseActivity.collectedData.remove((Integer)currentData.get("img"));
					Toast.makeText(
							ChooseByNameActivity.this,
							new StringBuilder(String.valueOf(currentData.get("name"))).append("已移出选择").toString(),
							Toast.LENGTH_SHORT
						).show();
					v.setClickable(true);
					v.setLongClickable(false);
					return true;
				}
			});
			
			relativeLayout.addView(leftImage, leftImageLayoutParams);
			relativeLayout.addView(textView, textLayoutParams);
						
			return relativeLayout;
		}
	}

}
