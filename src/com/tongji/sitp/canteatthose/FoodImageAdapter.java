package com.tongji.sitp.canteatthose;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class FoodImageAdapter extends BaseAdapter{
	private ArrayList<Integer> imagesId = new ArrayList<Integer>();
	private Context context;
	public FoodImageAdapter(Context context){
		this.context = context;
	}
	@Override
	public int getCount() {
		return imagesId.size()*5000;
	}
	
	@Override
	public Object getItem(int position) {
		return position;
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView image = new ImageView(context);
		return image;
	}
	
	
}
