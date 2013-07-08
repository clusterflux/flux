package com.clusterflux.concentric;

import android.util.Log;
import android.graphics.Point;

public class Player {

	public int x;
	public int y;
	public String direction = "right";
	public int movement = 0;
	
	public Player(int spawnX, int spawnY) {
	
		x = spawnX;
		y = spawnY;
		
	}
	
	public void move(int moveX, int moveY) {
	
	//Log.d("LOGCAT", "Player -> (" + moveX + "," + moveY + ")");

		x += moveX;
		y += moveY;
		
	Log.d("LOGCAT", "Moved Player. Player @  (" + x + "," + y + ")");
			
	}
	
	public void changeDirection(String newDirection) {
	
		if (direction == newDirection) {
			if (movement == 2) {
				movement = 0;
			} else {
				movement++;
			}
		} else {
			direction = newDirection;
			movement = 0;
		}
		
	}
	
	public Point isFacing() {
	
		Point block = new Point();
		
		if (direction.equals("right")) { block.set(x,y+1); }
		else if (direction.equals("left")) { block.set(x, y-1); }
		else if (direction.equals("down")) { block.set(x+1, y); }
		else if (direction.equals("up")) { block.set(x-1,y); }
		
		return block;

	}
}