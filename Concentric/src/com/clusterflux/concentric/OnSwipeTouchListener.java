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
	
		/*switch (event.getAction()) {
		
			case MotionEvent.ACTION_MOVE:
	 
				Log.d("LOGCAT", "SLIDE");
				return true;
		
			default:
				Log.d("LOGCAT", "USING GESTURE DETECTOR");*/
				return gestureDetector.onTouchEvent(event);
		
	}
	
	private final class GestureListener extends SimpleOnGestureListener {
	
		private static final int SWIPE_THRESHOLD = 5;
		private static final int SWIPE_VELOCITY_THRESHOLD = 100;
		private static final int SCROLL_THRESHOLD = 50;
		
		@Override
		public boolean onDown(MotionEvent event) {
		
			return true;
			
		}
		
		@Override
		public boolean onScroll(MotionEvent down, MotionEvent up, float distanceX, float distanceY) {
		Log.d("LOGCAT", "SCROLLING");
			try {
				float diffY = up.getY() - down.getY();
				float diffX = up.getX() - down.getX();
			
				if (Math.abs(diffX) > Math.abs(diffY)) { //scrolled on X axis
					
						if (Math.abs(diffX) > SCROLL_THRESHOLD) { //scroll is defined enough
							if (diffX > 0) { 
								onScrollRight();
							} else {
								onScrollLeft();
							}
						}
					} else { //scrolled on Y axis
					
						if (Math.abs(diffY) > SCROLL_THRESHOLD) { //scroll is defined enough
							if (diffY < 0) { //Y-axis is upside down
								onScrollUp();
							} else {
								onScrollDown();
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();;
				} 
			
			return false;
			
		}
	/***	
		@Override
		public boolean onFling(MotionEvent down, MotionEvent up, float velocityX, float velocityY) {
	Log.d("LOGCAT", "SWIPING");
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
		****/
	}
	
	public void onScrollRight() {
		//abstract method
		Log.d("LOGCAT", "Scrolling Right");

	}
	
	public void onScrollLeft() {
		//abstract method
		Log.d("LOGCAT", "Scrolling Left");

	}
	
	public void onScrollUp() {
		//abstract method
		Log.d("LOGCAT", "Scrolling Up");

	}
	
	public void onScrollDown() {
		//abstract method
		Log.d("LOGCAT", "Scrolling Down");
	}
	
	public void onSwipeRight() {
		//abstract method
		
	}
	
	public void onSwipeLeft() {
		//abstract method
	
	}
	
	public void onSwipeUp() {
	//abstract method
		
	}
	
	public void onSwipeDown() {
	//abstract method
		
	}
	
}
