package com.tongji.sitp.canteatthose;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.tongji.sitp.canteatthose.database.DBData;
import com.tongji.sitp.canteatthose.database.DBManager;

public class ResultActivity extends Activity {
	private final Bundle bundle = new Bundle();
	private DBManager manager;
	HashMap<Integer,Integer> collectedData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		DBData d = (DBData)getIntent().getSerializableExtra(getResources().getString(R.string.DBDATA_SER_KEY));
		collectedData = (HashMap<Integer,Integer>)getIntent().getSerializableExtra(getResources().getString(R.string.RESULT_SER_KEY));
		manager = new DBManager(this,d);
		
		List<Map<String, Object>> list = null;
		ListView listView_bad = (ListView)findViewById(R.id.result_bad_listView);
		ListView listView_good = (ListView)findViewById(R.id.result_good_listView);
		
		list = getBadCollocationData();
		if(list.isEmpty()){
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("img1", R.drawable.f_0);
			map.put("name1", "");
			map.put("des","太好了！没有不良搭配喔！");
			map.put("img2", R.drawable.f_0);
			map.put("name2","");
			list.add(map);
		}
		listView_bad.setAdapter(new MyListAdapater(list));
		list = getGoodCollocationData();
		if(list.isEmpty()){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("img1", R.drawable.f_0);
			map.put("name1", "");
			map.put("des","不太妙，没什么有益搭配喔！");
			map.put("img2", R.drawable.f_0);
			map.put("name2","");
			list.add(map);
		}
		listView_good.setAdapter(new MyListAdapater(list));
		
		final Button button_tryagain = (Button) findViewById(R.id.button_tryagain);
		button_tryagain.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ResultActivity.this, ChooseActivity.class);
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
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}
	@Override
	protected void onDestroy(){
		manager.closeDB();
		super.onDestroy();
	}
	
	private List<Map<String, Object>> getBadCollocationData(){
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		Collection<Integer> values = collectedData.values();
		Integer[] vs = (Integer[])(values.toArray(new Integer[values.size()]));
		for(int i = 0; i < vs.length-1; i ++){
			for(int j = i+1; j < vs.length; j ++){
				String[] result = manager.queryBad(vs[i], vs[j]);
				if(result != null){
					map = new HashMap<String,Object>();
					try{
						map.put("img1", R.drawable.class.getField("f_"+vs[i]).getInt(R.drawable.class));
						map.put("name1", result[0]);
						map.put("des",result[2]);
						map.put("img2", R.drawable.class.getField("f_"+vs[j]).getInt(R.drawable.class));
						map.put("name2",result[1]);
						list.add(map);
					}catch(NoSuchFieldException e){
						e.printStackTrace();
					}catch(IllegalAccessException e){
						e.printStackTrace();
					}
					
				}
			}
		}
		return list;
	}
	private List<Map<String, Object>> getGoodCollocationData(){
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		Collection<Integer> values = collectedData.values();
		Integer[] vs = (Integer[])(values.toArray(new Integer[values.size()]));
		for(int i = 0; i < vs.length-1; i ++){
			for(int j = i+1; j < vs.length; j ++){
				String[] result = manager.queryGood(vs[i], vs[j]);
				if(result != null){
					map = new HashMap<String,Object>();
					try{
						map.put("img1", R.drawable.class.getField("f_"+vs[i]).getInt(R.drawable.class));
						map.put("name1", result[0]);
						map.put("des",result[2]);
						map.put("img2", R.drawable.class.getField("f_"+vs[j]).getInt(R.drawable.class));
						map.put("name2",result[1]);
						list.add(map);
					}catch(NoSuchFieldException e){
						e.printStackTrace();
					}catch(IllegalAccessException e){
						e.printStackTrace();
					}
					
				}
			}
		}
		return list;
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

			Map<String, Object> currentData = data.get(position);
			
			RelativeLayout relativeLayout = new RelativeLayout(ResultActivity.this);
			
			ImageView leftImage = new ImageView(ResultActivity.this);
			leftImage.setId(0x7f101);
			ImageView rightImage = new ImageView(ResultActivity.this);
			rightImage.setId(0x7f102);
			
			leftImage.setScaleType(ScaleType.FIT_XY);
			rightImage.setScaleType(ScaleType.FIT_XY);
			
			TextView textView = new TextView(ResultActivity.this);
			textView.setGravity(Gravity.CENTER);
			TextView leftTextView = new TextView(ResultActivity.this);
			leftTextView.setGravity(Gravity.CENTER);
			TextView rightTextView = new TextView(ResultActivity.this);
			rightTextView.setGravity(Gravity.CENTER);
			
			RelativeLayout.LayoutParams leftImageLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			RelativeLayout.LayoutParams rightImageLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);		
			RelativeLayout.LayoutParams leftTextLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			RelativeLayout.LayoutParams rightTextLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			
			leftImageLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			rightImageLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
			textLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);		
			textLayoutParams.addRule(RelativeLayout.RIGHT_OF,leftImage.getId());
			textLayoutParams.addRule(RelativeLayout.LEFT_OF, rightImage.getId());
			leftTextLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
			leftTextLayoutParams.addRule(RelativeLayout.BELOW,leftImage.getId());
			rightTextLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
			rightTextLayoutParams.addRule(RelativeLayout.BELOW,rightImage.getId());
			
			leftImage.setImageResource((Integer)currentData.get("img1"));
			leftTextView.setText((String)currentData.get("name1"));
			rightImage.setImageResource((Integer)currentData.get("img2"));
			rightTextView.setText((String)currentData.get("name2"));
			textView.setText((String)currentData.get("des"));
			
			leftTextView.setWidth(140);
			rightTextView.setWidth(140);
			
			relativeLayout.addView(leftImage, leftImageLayoutParams);
			relativeLayout.addView(rightImage, rightImageLayoutParams);
			relativeLayout.addView(textView, textLayoutParams);
			relativeLayout.addView(leftTextView, leftTextLayoutParams);
			relativeLayout.addView(rightTextView, rightTextLayoutParams);
						
			return relativeLayout;
		}
	}
}
