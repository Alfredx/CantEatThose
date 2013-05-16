package com.tongji.sitp.canteatthose;

import com.tongji.sitp.canteatthose.database.DBData;
import com.tongji.sitp.canteatthose.database.DBManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import android.view.View.OnClickListener;

public class ChooseByNameActivity extends Activity {
	private final Bundle bundle = new Bundle();
	private DBManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_by_name);
		DBData d = (DBData)getIntent().getSerializableExtra(getResources().getString(R.string.DBDATA_SER_KEY));
		manager = new DBManager(this,d);
		final Button button_submit2 = (Button) findViewById(R.id.button_submit2);
		button_submit2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ChooseByNameActivity.this, ResultActivity.class);
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

}
