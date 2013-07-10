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
	public Map<String,Bitmap> SHADOW;
	public Bitmap SPRITE;
	public Bitmap MONSTER;
	public Player player;
	public Monster monster;
	public Camera camera;
	public SurfaceHolder holder;
	//public Bitmap overlayBitmap;
	
	//hardcoded parameters for testing
	public int tile_width;
	public int tile_height;
	public int screen_width;
	public int screen_height;
	public int spriteX = 0;
	public int spriteY = 0;
	public int monsterX = 0;
	public int monsterY = 0;
	public float translateX = 0f;
	public float translateY = 0f;

	public MapThread mapThread; 
	
	public MapView(Context context, AttributeSet attrs) {
	
		super(context, attrs);
		this.context = context;
		Log.d("LOGCAT", "MapView created");	
		
		//get the tile map
		WorldFeatures worldFeatures = new WorldFeatures(context);		
		TILE_MAP = worldFeatures.TILE_MAP;
		SHADOW = worldFeatures.SHADOW;
		SPRITE = worldFeatures.SPRITE;
		MONSTER = worldFeatures.MONSTER;
		tile_height = worldFeatures.tile_height;
		tile_width = worldFeatures.tile_width;
		
		Log.d("LOGCAT", "tile_height = " + tile_height);
		Log.d("LOGCAT", "tile_width = " + tile_width);
		
		holder = getHolder();
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
	Log.d("LOGCAT", "SURFACE DESTROYED");
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
	
		canvas.drawColor(Color.BLACK);
		
		canvas.translate(0, - tile_height/2); //account for null space at top of tiles
		
		if (!player.changed) {
		
			if (player.direction.equals("right")) { 
				translateY = -(player.y % 1)*tile_width; 
				translateX = 0 ; 
			}
			if (player.direction.equals("left"))  { 
				translateY = (player.y % 1)*tile_width;
				translateX = 0 ; 
			}
			if (player.direction.equals("up"))    { 
				translateY = 0;
				translateX = (player.x % 1)*tile_height; 
			}
			if (player.direction.equals("down"))  { 
				translateY = 0; 
				translateX = -(player.x % 1)*tile_height; 
			}
			
		}
			
		canvas.translate( translateY, translateX );
		
		//canvas.translate(-(player.y % 1)*tile_width, -(player.x % 1)*tile_height);

		int screenX = -1; //reset screenX each loop - this is where we will add an if statement to draw one column only
		
		for (int x = camera.x; x < camera.x + screen_height + 1; x += 1, screenX += 1) {
			
			int screenY = -1; //reset screenY each loop - this is where we will add an if statement to draw one row only
			
			for (int y = camera.y; y < camera.y + screen_width + 1; y += 1, screenY += 1) {

				if (x == world.world_width || y == world.world_height) {
				
					//skip drawing
					
				} else {
				
					//LAYER 1:
					if (world.world_map[x][y] != 0) {
					
						//draw tile
						canvas.drawBitmap(TILE_MAP.get(world.world_map[x][y]), screenY*tile_height , screenX*tile_width, null);
						
					}
					
				}
				
			}
			
		}
		
		//loop again for sprite and LAYER 2
		
		screenX = 0; //reset screenX each loop - this is where we will add an if statement to draw one column only
		
		for (int x = camera.x; x < camera.x + screen_height + 1; x += 1, screenX += 1) {
			
			int screenY = 0; //reset screenY each loop - this is where we will add an if statement to draw one row only
			
			for (int y = camera.y; y < camera.y + screen_width + 1; y += 1, screenY += 1) {

				if (x == world.world_width || y == world.world_height) {
				
					//skip drawing
					
				} else {
				
					if (world.world_map[x][y] != 0) {
				
						//draw sprite
						if ((int)player.x == x && (int)player.y == y) { 

							spriteX = player.movement;
						
							if (player.direction.equals("right")) { spriteY = 2; } 
							else if (player.direction.equals("left")) { spriteY = 1; } 
							else if (player.direction.equals("up")) { spriteY = 3; } 
							else if (player.direction.equals("down")) { spriteY = 0; } 
						
							Rect src = new Rect((SPRITE.getHeight()/4)*spriteX, (SPRITE.getWidth()/3)*	  	spriteY,(SPRITE.	getHeight()/4)*(spriteX+1), (SPRITE.getWidth()/3)*(		 spriteY+1));
						  
							int Yoffset = 0;
							int Xoffset = 0;
							
							if (player.y % 1 != 0) { Yoffset = tile_height/2; }
							if (player.x % 1 != 0) { Xoffset = tile_width/2; }
							
							Rect dest = new Rect(screenY*tile_height + Yoffset, screenX*tile_width + tile_width/4 + Xoffset, 
								(screenY + 1)*tile_height + Yoffset, (screenX + 1)*tile_width + tile_width/3 + Xoffset);

							canvas.drawBitmap(SPRITE, src, dest, null);
							
						}
						
						//draw monster
						/*if (monster.x == x && monster.y == y) {
												
							monsterX = monster.movement;
							
							if (monster.direction.equals("right")) { monsterY = 2; }
							else if (monster.direction.equals("left")) { monsterY = 1;}
							
							Rect src = new Rect((MONSTER.getHeight()/4)*monsterX, (MONSTER.getWidth()/3)*	  	monsterY,(MONSTER.getHeight()/4)*(monsterX+1), (MONSTER.getWidth()/3)*(		 monsterY+1));
						  
							Rect dest = new Rect(screenY*tile_height, screenX*tile_width + tile_width/4, 
								(screenY + 1)*tile_height, (screenX + 1)*tile_width + tile_width/3);
								
							canvas.drawBitmap(MONSTER, src, dest, null);
								
						}*/
					
						//draw shadows
						if (y != world.world_height - 1 && x != world.world_width - 1) {
						if (world.world_map2[x+1][y+1] != 0 && world.world_map2[x][y+1] == 0) {
							canvas.drawBitmap(SHADOW.get("southeast"), screenY*tile_height , screenX*tile_width, null);
						}}
						if (x != world.world_width - 1) {
						if (world.world_map2[x+1][y] != 0) {
							canvas.drawBitmap(SHADOW.get("south"), screenY*tile_height , screenX*tile_width, null);
						}}
						if (x != world.world_width - 1 && y != 0) {
						if (world.world_map2[x+1][y-1] != 0 && world.world_map2[x][y-1] == 0) {
							canvas.drawBitmap(SHADOW.get("southwest"), screenY*tile_height , screenX*tile_width, null);
						}}
						if (y != world.world_height - 1) {
						if (world.world_map2[x][y+1] != 0) {
							canvas.drawBitmap(SHADOW.get("east"), screenY*tile_height , screenX*tile_width, null);
						}}
						if (y != 0) { 
						if (world.world_map2[x][y-1] != 0) {
							canvas.drawBitmap(SHADOW.get("west"), screenY*tile_height , screenX*tile_width, null);
						}}
						if (x != 0 && y != world.world_height - 1) {
						if (world.world_map2[x-1][y+1] != 0 && world.world_map2[x-1][y] == 0 && world.world_map2[x][y+1] == 0) {
							canvas.drawBitmap(SHADOW.get("northeast"), screenY*tile_height , screenX*tile_width, null);
						}}
						if (x != 0) {
						if (world.world_map2[x-1][y] != 0) {
							canvas.drawBitmap(SHADOW.get("north"), screenY*tile_height , screenX*tile_width, null);
						}}
						if (x != 0 && y != 0) {
						if (world.world_map2[x-1][y-1] != 0 && world.world_map2[x][y-1] == 0 && world.world_map2[x-1][y] == 0) {
							canvas.drawBitmap(SHADOW.get("northwest"), screenY*tile_height , screenX*tile_width, null);
						}}
					
					}
				
					//LAYER 2:
					if (world.world_map2[x][y] != 0) {

						//draw tile
						canvas.drawBitmap(TILE_MAP.get(world.world_map2[x][y]), screenY*tile_height,		screenX*tile_width - tile_width/2, null);
					
						//draw shadows
						if (x != world.world_width - 1 && y != 0) {
						if (world.world_map2[x+1][y-1] != 0 && world.world_map2[x+1][y] == 0) {
							canvas.drawBitmap(SHADOW.get("sidewest"), screenY*tile_height , screenX*tile_width, null);
						}}
						if (x != 0) {
						if (world.world_map2[x-1][y] == 0 && world.world_map[x-1][y] == 0) {
							canvas.drawBitmap(SHADOW.get("south"), screenY*tile_height , screenX*tile_width, null);
						}}
					
					}
				
				}
			
			}
			
		}
		
	}
	
	public void setWorld(World world){
		
		this.world = world;
		
	}
	
	public void setPlayer(Player player) {
	
		this.player = player;
		
	}
	
	public void setMonster(Monster monster) {
	
		this.monster = monster;
		
	}
	
	public void setCamera(Camera camera) {
	
		this.camera = camera;
		
	}
	
	public void setScreenSize(int screen_width, int screen_height) {
	
		this.screen_width = screen_width;
		this.screen_height = screen_height;
		
	}

}