package com.clusterflux.concentric;

import android.graphics.Canvas;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import java.util.*;
import java.math.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.Serializable;
import android.util.Log;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.graphics.BitmapFactory;


public class MapView extends View {

	protected Context context;
	public World world;
	public Map<Integer,Bitmap> TILE_MAP;
	//public Bitmap overlayBitmap;
	public Bitmap sprite;
	
	//hardcoded parameters for testing
	private int tile_width = 50;
	private int tile_height = 50;
	public int screen_width = 12;
	public int screen_height = 6;
	public int playerX = 100;
	public int playerY = 100;
	public int centerTileX = screen_height/2 - 1;
	public int centerTileY = screen_width/2 - 1;


	public MapView(Context context, AttributeSet attrs) {
	
		super(context, attrs);
		this.context = context;
		Log.d("LOGCAT", "MapView created");	
		
		sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite);
		
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		
		//Draw tiles onto canvas
		Log.d("LOGCAT", "drawing canvas");
			
		int screenX = 0; //reset screenX each loop - this is where we will add an if statement to draw one column only
		
		for (int worldX = (playerX - screen_height/2 + 1); worldX < (playerX + screen_height/2 + 1); worldX += 1, screenX += 1) {
			
			int screenY = 0; //reset screenY each loop - this is where we will add an if statement to draw one row only
			
			for (int worldY = (playerY - screen_width/2 + 1);  worldY < (playerY + screen_width/2 + 1); worldY += 1, screenY += 1) {
				
				if (screenX == 2 && screenY == 5) {
				Log.d("LOGCAT", "Drawing tile @ = (" + screenX + ", " + screenY + ") [ world = (" + worldX + ", " + worldY + ") ]");
				}
			
				canvas.drawBitmap(TILE_MAP.get(world.world_map[worldX][worldY]), screenY*tile_height , screenX*tile_width, null);

			}
		}
		
		//draw sprite in center (2,5) on screen map
		canvas.drawBitmap(sprite, centerTileY*tile_height + tile_height/5, centerTileX*tile_width + tile_width/5, null);

		Log.d("LOGCAT", "done drawing canvas");	
		
	}
	
	public void setWorld(World world){
		
		this.world = world;
		
	}
	
	public void setTileMap(Map<Integer, Bitmap> TILE_MAP){
		
		this.TILE_MAP = TILE_MAP;
		
	}
	
	public void movePlayer(int movePlayerX, int movePlayerY) {
	
	Log.d("LOGCAT", "Player @  (" + playerX + "," + playerY + ")");
	Log.d("LOGCAT", "Player -> (" + movePlayerX + "," + movePlayerY + ")");

		int newX = playerX + movePlayerX;
        int newY = playerY + movePlayerY;

		if (world.world_map[newX][newY] == 2) { //check if tile to move to is stone
        
            //do nothing
            Log.d("LOGCAT", "Trying to cross stone. Cancel move");
            
		} else { //check if player's movement will take him out of bounds

			if ( newY < (world.world_width - screen_width/2 - 1) && newX < (world.world_height - screen_height/2 - 1) 
				&& newY > screen_width/2 && newX > screen_height/2) {
	
				this.playerX += movePlayerX;
				this.playerY += movePlayerY;
			
				invalidate();
			
			} else {
		
			//do nothing
			Toast.makeText(context, "REACHED THE END OF THE WORLD", Toast.LENGTH_SHORT).show();
			
			}
			
		}
	}
	
}