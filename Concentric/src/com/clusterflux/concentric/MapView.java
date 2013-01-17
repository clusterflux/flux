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
import android.view.SurfaceView;
import android.view.SurfaceHolder;


public class MapView extends SurfaceView implements SurfaceHolder.Callback {

	protected Context context;
	public World world;
	public Map<Integer,Bitmap> TILE_MAP;
	public Bitmap SPRITE;
	public Player player;
	public Camera camera;
	//public Bitmap overlayBitmap;
	
	//hardcoded parameters for testing
	private int tile_width = 24;
	private int tile_height = 24;
	public int screen_width = 24;
	public int screen_height = 12;
	public int centerTileX = screen_height/2 - 1;
	public int centerTileY = screen_width/2 - 1;

	public MapThread mapThread; 
	
	public MapView(Context context, AttributeSet attrs) {
	
		super(context, attrs);
		this.context = context;
		Log.d("LOGCAT", "MapView created");	
		
		//get the tile map
		WorldFeatures worldFeatures = new WorldFeatures(context);		
		TILE_MAP = worldFeatures.TILE_MAP;
		SPRITE = worldFeatures.SPRITE;
		
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) { 
	
		mapThread = new MapThread(holder, context, this);
		mapThread.setRunning(true);
		mapThread.start();
		
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) { 
	
		mapThread.setRunning(false);
		boolean retry = true;
		
		while (retry) {
		
			try {
				mapThread.join();
				retry = false;
			} catch (Exception e) {
				Log.d("LOGCAT", e.getMessage());
			}
		
		}
		
	}
	
	public void doDraw(Canvas canvas) {
		
		//Draw tiles onto canvas
		//Log.d("LOGCAT", "drawing canvas");
			
		int screenX = 0; //reset screenX each loop - this is where we will add an if statement to draw one column only
		
		for (int worldX = (camera.x - screen_height/2 + 1); worldX < (camera.x + screen_height/2 + 1); worldX += 1, screenX += 1) {
			
			int screenY = 0; //reset screenY each loop - this is where we will add an if statement to draw one row only
			
			for (int worldY = (camera.y - screen_width/2 + 1);  worldY < (camera.y + screen_width/2 + 1); worldY += 1, screenY += 1) {
			
				canvas.drawBitmap(TILE_MAP.get(world.world_map[worldX][worldY]), screenY*tile_height , screenX*tile_width, null);
				
				//Log.d("LOGCAT", "worldX,y (" + worldX + "," + worldY + ")");
				//Log.d("LOGCAT", "playerx,y (" + player.x + "," + player.y + ")");

				if (player.x == worldX && player.y == worldY) { //if the player is standing here, draw the sprite
					//Log.d("LOGCAT", "DRAWING SPRITE AT (" + player.x + "," + player.y + ")");
					canvas.drawBitmap(SPRITE, screenY*tile_height + tile_height/5, screenX*tile_width + tile_width/5, null);

				}

			}
		}
		
		//Log.d("LOGCAT", "done drawing canvas");	
		
	}
	
	public void setWorld(World world){
		
		this.world = world;
		
	}
	
	public void setPlayer(Player player) {
	
		this.player = player;
		
	}
	
	public void setCamera(Camera camera) {
	
		this.camera = camera;
		
	}

}