package com.clusterflux.concentric;

import android.util.Log;

public class Camera {

	public float x;
	public float y;
	
	public Camera(int spawnX, int spawnY) {
	
		x = spawnX;
		y = spawnY;
		
	}
	
	public void move(float moveX, float moveY) {
	
		x += moveX;
		y += moveY;
	
	//Log.d("LOGCAT", "Camera -> (" + moveX + "," + moveY + ")");

	Log.d("LOGCAT", "Moved Camera. Camera @  (" + x + "," + y + ")");
		
	}

}