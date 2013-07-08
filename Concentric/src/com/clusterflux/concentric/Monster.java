package com.clusterflux.concentric;

import android.util.Log;
import android.graphics.Point;

public class Monster {

	public int x;
	public int y;
	public String direction = "right";
	public int movement = 0;
	public int steps = 0;
	
	public Monster(int spawnX, int spawnY) {
	
		x = spawnX;
		y = spawnY;
		
	}
	
	public void move() {
	
		if ( direction == "right" ) {
		
			y += 1;
			
		} else {
		
			y -= 1;
			
		}
		Log.d("LOGCAT", "MONSTER LOCATION UPDATED TO: " + x + "," + y);		
	}
	
	public void checkDirection() {
	
		if ( steps == 7 ) {
		
			steps = 0;
			movement = 0;
			changeDirection();
				
		} else {
		
			steps++;
			updateMovement();
			
		}

		//Log.d("LOGCAT", "MONSTER STEPS UPDATED TO: " + steps);		
		//Log.d("LOGCAT", "MONSTER MOVEMENT UPDATED TO: " + movement);			
		//Log.d("LOGCAT", "MONSTER DIRECTION UPDATED TO: " + direction);				

	}
	
	private void changeDirection() {
	
		if ( direction == "right" ) {
		
			direction = "left";
			
		} else {
		
			direction = "right";
			
		}
	}
	
	private void updateMovement() {
	
		if (movement == 2) { //if we are on last movement, cycle back
			movement = 0;
		} else {
			movement++; 
		}
		
	}
	
}