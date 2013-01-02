package com.example.wandw;

import android.graphics.Canvas;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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


public class MapView extends View implements OnClickListener {

	private int tile_width = 50;
	private int tile_height = 50;
	protected Context context;
	public World world;
	public int screen_width = 12;
	public int screen_height = 6;
	public int playerX = 4;
	public int playerY = 7;


	public MapView(Context context, AttributeSet attrs) {
	
		super(context, attrs);
		this.context = context;
		Log.d("LOGCAT", "MapView created");	
		
		setOnClickListener(this);
		
	}
	
	@Override
	public void onDraw(Canvas canvas) {
	Log.d("LOGCAT", "drawing canvas");
	
		//get the TileTranslator
		Map<Integer,Bitmap> tile_translator = createTileTranslator();
		Log.d("LOGCAT", "Tile Translator received");	
		
		//Hardcoded player movement
		Log.d("LOGCAT", "Player moed to (" + playerX + ", " + playerY + ")");
		
		//Draw tiles onto canvas
		int screenX = -1;	// why?
		
		for (int worldX = (playerX - screen_height/2); worldX < (playerX + screen_height/2 + 1); worldX += 1) {
		
			screenX += 1; 
			int screenY = 0; //reset screenY each loop - this is where we will add an if statement to one column/row only
			
			for(int worldY = (playerY - screen_width/2);  worldY < (playerY + screen_width/2 + 1); worldY += 1) {
			
				//Log.d("LOGCAT", "Drawing tile @ = (" + screenX + ", " + screenY + 
				//      ") [ world = (" + worldX + ", " + worldY + ") ]");
			
				canvas.drawBitmap(tile_translator.get(world.world_map[worldX][worldY]), 
							      screenY*tile_height, screenX*tile_width, null);
				screenY += 1;
			}
		}
		Log.d("LOGCAT", "done drawing canvas");	
		
	}
	
	@Override
	public void onClick(View v) { //hardcoded move right function
		
		//if in bounds, move the player and redraw
		if ( playerY < (world.world_width - screen_width/2 - 1) && playerX < (world.world_height - screen_height/2 - 1)) {
		
			//Toast.makeText(context, "CLICK DETECTED, MOVING RIGHT", Toast.LENGTH_SHORT).show();;
			playerY += 1;
			invalidate(); 
			
		} else { 
			//do nothing
			Toast.makeText(context, "REACHED THE END OF THE WORLD", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	public void setWorld(World world){
		
		this.world = world;
		
	}
	
	public Map<Integer,Bitmap> createTileTranslator() {  //move to start up activity!
	Log.d("LOGCAT", "getting TileTranslator");
		//create hash for bitmap tiles
		Map<Integer, Bitmap> tile_translator = new HashMap<Integer, Bitmap>();

		//add integer keys and tiles to hash
		tile_translator.put(0, BitmapFactory.decodeResource(getResources(), R.drawable.dirt));
		tile_translator.put(1, BitmapFactory.decodeResource(getResources(), R.drawable.grass));
		tile_translator.put(2, BitmapFactory.decodeResource(getResources(), R.drawable.stone));
		
		return tile_translator;

	}
	
}