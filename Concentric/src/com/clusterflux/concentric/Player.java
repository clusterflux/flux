package com.clusterflux.concentric;

import android.util.Log;

public class Player {

	public int x;
	public int y;
	
	public Player(int spawnX, int spawnY, World world) {
	
		x = spawnX;
		y = spawnY;
		
	}
	
	public void move(int moveX, int moveY) {
	
	Log.d("LOGCAT", "Player -> (" + moveX + "," + moveY + ")");

		x += moveX;
		y += moveY;
		
	Log.d("LOGCAT", "Moved Player. Player @  (" + x + "," + y + ")");
			
	}
		
}