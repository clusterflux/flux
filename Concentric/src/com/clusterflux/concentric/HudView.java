package com.clusterflux.concentric;

import android.graphics.Canvas;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;


public class HudView extends View {

	public HudView(Context context, AttributeSet attrs) {
	
		super(context, attrs);
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
	
		super.onDraw(canvas);
		canvas.drawARGB(0, 0, 0, 0);
		
	}
	
}