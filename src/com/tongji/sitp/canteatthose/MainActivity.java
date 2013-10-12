package com.tongji.sitp.canteatthose;

import java.io.Serializable;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.tongji.sitp.canteatthose.database.*;

public class MainActivity extends Activity {
	private DBManager manager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Button button_start = (Button) findViewById(R.id.button_start);
		final Button button_tips = (Button) findViewById(R.id.button_tips);
		final Bundle bundle = new Bundle();
		manager = new DBManager(this);
		bundle.putSerializable(getString(R.string.DBDATA_SER_KEY), manager.getData());
		button_start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v){
				Intent intent = new Intent(MainActivity.this,ChooseActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		button_tips.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,TipsActivity.class);
				ArrayList<Tip> tips = (ArrayList<Tip>)manager.queryTips();
				bundle.putSerializable(getString(R.string.TIP_SER_KEY), tips);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	protected void onDestory(){
		manager.closeDB();
		super.onDestroy();
	}
}
