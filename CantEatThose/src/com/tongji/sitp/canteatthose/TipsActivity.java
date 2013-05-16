package com.tongji.sitp.canteatthose;

import java.util.ArrayList;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class TipsActivity extends Activity {
	ArrayList<Tip> tips;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tips);
		tips = (ArrayList<Tip>)getIntent().getSerializableExtra(getString(R.string.TIP_SER_KEY));
		Random rand = new Random();
		TextView text = (TextView)findViewById(R.id.text_tip);
		text.setText(tips.get(rand.nextInt(tips.size())).description());
		final Button button_iknow = (Button) findViewById(R.id.button_iknow);
		button_iknow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tips, menu);
		return true;
	}

}
