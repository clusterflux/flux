package com.clusterflux.concentric;

import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import java.io.*;
import android.util.Log;

public class OnSwipeTouchListener implements OnTouchListener {

	public final GestureDetector gestureDetector = new GestureDetector(new GestureListener());
	
	public float swipeVelocity;
	
	public boolean onTouch(View view, MotionEvent event) {
	
		return gestureDetector.onTouchEvent(event);
		
	}
	
	private final class GestureListener extends SimpleOnGestureListener {
	
		private static final int SWIPE_THRESHOLD = 50;
		private static final int SWIPE_VELOCITY_THRESHOLD = 100;
		
		@Override
		public boolean onDown(MotionEvent event) {
		
			return true;
			
		}
		
		@Override
		public boolean onFling(MotionEvent down, MotionEvent up, float velocityX, float velocityY) {

			try {
				float diffY = up.getY() - down.getY();
				float diffX = up.getX() - down.getX();
				
				if (Math.abs(diffX) > Math.abs(diffY)) { //swiped on X axis
				
					swipeVelocity = velocityX;
												
					if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) { //swipe is defined enough
						if (diffX > 0) { 
							onSwipeRight();
						} else {
							onSwipeLeft();
						}
					}
				} else { //swiped on Y axis
				
					swipeVelocity = velocityY;
					
					if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) { //swipe is defined enough
						if (diffY < 0) { //Y-axis is upside down
							onSwipeUp();
						} else {
							onSwipeDown();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();;
			} 
			
			return false;
		
		}
	}
	
	public void onSwipeRight() {
	//override this in calling Activity
		
	}
	
	public void onSwipeLeft() {
	//override this in calling Activity
	
	}
	
	public void onSwipeUp() {
	//override this in calling Activity
		
	}
	
	public void onSwipeDown() {
	//override this in calling Activity
		
	}
	
}
