package com.tongji.sitp.canteatthose;

import java.util.ArrayList;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class TipsActivity extends Activity {
	ArrayList<Tip> tips;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tips);
		tips = (ArrayList<Tip>)getIntent().getSerializableExtra(getString(R.string.TIP_SER_KEY));
		final Random rand = new Random();
		final ImageView img1 = (ImageView)findViewById(R.id.tips_img1);
		final ImageView img2 = (ImageView)findViewById(R.id.tips_img2);
		final TextView txt1 = (TextView)findViewById(R.id.tips_txt1);
		final TextView txt2 = (TextView)findViewById(R.id.tips_txt2);
		final TextView text = (TextView)findViewById(R.id.text_tip);

		try{
			Tip tip = tips.get(rand.nextInt(tips.size()));
			img1.setImageResource(R.drawable.class.getField("f_"+tip.getFood1_id()).getInt(R.drawable.class));
			img2.setImageResource(R.drawable.class.getField("f_"+tip.getFood2_id()).getInt(R.drawable.class));
			txt1.setText(tip.getFood1_name());
			txt2.setText(tip.getFood2_name());
			text.setText(tip.description());
		}catch(Exception e){
			e.printStackTrace();
		}
		final Button button_iknow = (Button) findViewById(R.id.button_iknow);
		final Button button_change = (Button)findViewById(R.id.button_change_tip);
		button_iknow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		button_change.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					Tip tip = tips.get(rand.nextInt(tips.size()));
					img1.setImageResource(R.drawable.class.getField("f_"+tip.getFood1_id()).getInt(R.drawable.class));
					img2.setImageResource(R.drawable.class.getField("f_"+tip.getFood2_id()).getInt(R.drawable.class));
					txt1.setText(tip.getFood1_name());
					txt2.setText(tip.getFood2_name());
					text.setText(tip.description());
				}catch(Exception e){
					e.printStackTrace();
				}
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
