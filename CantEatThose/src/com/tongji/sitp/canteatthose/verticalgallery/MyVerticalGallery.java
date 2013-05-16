package com.tongji.sitp.canteatthose.verticalgallery;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.Gallery;

public class MyVerticalGallery extends Gallery{
	public MyVerticalGallery(Context context) {
		super(context);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.translate(0, 0);
		canvas.rotate(-90);
		super.onDraw(canvas);
	}
}
