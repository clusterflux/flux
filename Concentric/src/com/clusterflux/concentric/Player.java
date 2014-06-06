package com.clusterflux.concentric;

import android.util.Log;
import android.graphics.Point;

public class Player {

	public float x;
	public float y;
	public String direction = "right";
	public int movement = 0;
	boolean changed;
	public String lastDirection;
	
	public Player(int spawnX, int spawnY) {
	
		x = spawnX;
		y = spawnY;
		
	}
	
	public void move(float moveX, float moveY) {
	
	//Log.d("LOGCAT", "Player -> (" + moveX + "," + moveY + ")");
    Log.d("LOGCAT", "Moving Player x,y = " + x + "," + y + ")");
    Log.d("LOGCAT", "Moving Player dx,dy = " + moveX + "," + moveY + ")");

		x += moveX;
		y += moveY;
	Log.d("LOGCAT", "Moved Player. Player @  (" + x + "," + y + ")");
			
	}
	
	public boolean changeDirection(String newDirection) {
	
		lastDirection = direction;
			
		if (direction == newDirection) {
		
			changed = false;
			
			if (movement == 2) {
				movement = 0;
			} else {
				movement++;
			}
		} else {
		
			changed = true;
			
			direction = newDirection;
			movement = 0;
		}
		
		return changed;
		
	}
	
	public Point isFacing() {
	
		Point block = new Point();
		
		if (direction.equals("right")) { block.set((int)x,(int)y+1); }
		else if (direction.equals("left")) { block.set((int)x, (int)y-1); }
		else if (direction.equals("down")) { block.set((int)x+1, (int)y); }
		else if (direction.equals("up")) { block.set((int)x-1,(int)y); }
		
		return block;

	}
}