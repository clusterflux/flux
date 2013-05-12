package com.clusterflux.concentric;

import android.graphics.Canvas;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;


public class DPadView extends View {

	public DPadView(Context context, AttributeSet attrs) {
	
		super(context, attrs);
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
	
		super.onDraw(canvas);
		canvas.drawARGB(200, 200, 200, 200);
		
	}
	
}