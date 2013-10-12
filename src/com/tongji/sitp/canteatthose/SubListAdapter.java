package com.tongji.sitp.canteatthose;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;


public class SubListAdapter extends BaseAdapter implements ListAdapter{
	private Context context;
	private int galleryItemBackground;
	private int[] imagesId = {
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
	
	public SubListAdapter(Context context){
		this.context = context;
		
	}
	@Override
	public int getCount(){
		return imagesId.length;
	}
	@Override
	public Object getItem(int position){
		return position; 
	}
	@Override
	public long getItemId(int position){
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		ImageView image = new ImageView(context);
		image.setImageResource(imagesId[(position) % imagesId.length]);
		image.setScaleType(ImageView.ScaleType.FIT_CENTER);
		image.setLayoutParams(new Gallery.LayoutParams(140*2,165));
		return image;
	}
	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
		return super.areAllItemsEnabled();
	}
	
}
