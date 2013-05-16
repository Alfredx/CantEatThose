package com.tongji.sitp.canteatthose;

import com.tongji.sitp.canteatthose.database.DBData;
import com.tongji.sitp.canteatthose.database.DBManager;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.content.Intent;

public class ResultActivity extends Activity {
	private final Bundle bundle = new Bundle();
	private DBManager manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		DBData d = (DBData)getIntent().getSerializableExtra(getResources().getString(R.string.DBDATA_SER_KEY));
		manager = new DBManager(this,d);
		
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
}
