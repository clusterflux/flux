package com.example.wandw;

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
	public Map<Integer,Bitmap> tileTranslator;
	//public Bitmap overlayBitmap;
	public Bitmap sprite;
	
	//hardcoded parameters for testing
	private int tile_width = 50;
	private int tile_height = 50;
	public int screen_width = 12;
	public int screen_height = 6;
	public int playerX = 4;
	public int playerY = 7;
	public int centerTileX = screen_height/2 - 1;
	public int centerTileY = screen_width/2 - 1;


	public MapView(Context context, AttributeSet attrs) {
	
		super(context, attrs);
		this.context = context;
		Log.d("LOGCAT", "MapView created");	
		
		sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite);
		//overlayBitmap = Bitmap.createBitmap(screen_width*tile_width, screen_height*tile_height);
		
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		
		//canvas = new Canvas(overlayBitmap);
		
		//Draw tiles onto canvas
		Log.d("LOGCAT", "drawing canvas");
			
		int screenX = -1;	// why?
		
		for (int worldX = (playerX - screen_height/2); worldX < (playerX + screen_height/2 + 1); worldX += 1) {
		
			screenX += 1; 
			int screenY = 0; //reset screenY each loop - this is where we will add an if statement to one column/row only
			
			for(int worldY = (playerY - screen_width/2);  worldY < (playerY + screen_width/2 + 1); worldY += 1) {
			
				//Log.d("LOGCAT", "Drawing tile @ = (" + screenX + ", " + screenY + 
				//      ") [ world = (" + worldX + ", " + worldY + ") ]");
			
				canvas.drawBitmap(tileTranslator.get(world.world_map[worldX][worldY]), screenY*tile_height , screenX*tile_width, null);
				//Log.d("LOGCAT", "World tile is" + world.world_map[worldX][worldY]); 
				screenY += 1;
			}
		}
		Log.d("LOGCAT", "Drawing sprite at " + (centerTileX*tile_height + tile_height/4) + "," + (centerTileY*tile_width + tile_width/4));
		//draw sprite in center
		canvas.drawBitmap(sprite, centerTileY*tile_height + tile_height/5, centerTileX*tile_width + tile_width/5, null);

		Log.d("LOGCAT", "done drawing canvas");	
		
	}
	
	//ugly method, fix!
	public void setArgs(World world, Map<Integer,Bitmap> tileTranslator){
		
		this.world = world;
		this.tileTranslator = tileTranslator;
		
	}
	
	public void movePlayer(int movePlayerX, int movePlayerY) {
	Log.d("LOGCAT", "Moving Player (" + movePlayerX + "," + movePlayerY + ")");
		
		
		//check if player's movement will take him out of bounds
		if ( playerY + movePlayerY < (world.world_width - screen_width/2 - 1) && playerX + movePlayerX < (world.world_height - screen_height/2 - 1) 
			 && playerY + movePlayerY > screen_width/2 && playerX + movePlayerX > screen_height/2) {
	
			this.playerX += movePlayerX;
			this.playerY += movePlayerY;
			
			invalidate();
			
		} else {
		
			//do nothing
			Toast.makeText(context, "REACHED THE END OF THE WORLD", Toast.LENGTH_SHORT).show();
			
		}
			
	}
	
}