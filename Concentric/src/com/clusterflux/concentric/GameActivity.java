package com.clusterflux.concentric;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import java.io.*;
import android.util.Log;
import android.widget.Toast;
import android.graphics.Bitmap;
import java.util.*;
import android.view.View.OnTouchListener;
import java.math.*;

public class GameActivity extends Activity {
	
	public MapView mapView;
	public World world;
	public Player player;
	public Camera camera;
	
	//hardcoded parameters for testing
	private int tile_width = 25;
	private int tile_height = 25;
	public int screen_width = 24;
	public int screen_height = 12;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);	
		Log.d("LOGCAT", "GameActivity Started");
		
		//get the world_name from MenuActivity
		Intent intent = getIntent();
		String world_name = intent.getStringExtra(MenuActivity.EXTRA_MESSAGE);
		
		//load the world
		try {
			world = new World(this, world_name);
		} catch (IOException e) {
			//do nothing
		} catch (ClassNotFoundException e) {
			//do nothing
		}
		
		player = new Player(200, 200, world);
		camera = new Camera(200, 200, world);
		
		//Create a reference to the MapView object, set world & player
		mapView = (MapView) findViewById(R.id.map_view);
		mapView.setWorld(world);
		mapView.setPlayer(player);
		mapView.setCamera(camera);
		
		//implement the OnTouchSwipeListener
		mapView.setOnTouchListener(new OnSwipeTouchListener() {
			
			public void onSwipeRight() {		
				update(0,1);
			}
			
			public void onSwipeLeft() {
				update(0,-1);
			}
			
			public void onSwipeUp() {
				update(-1,0);
			}
			
			public void onSwipeDown() {
				update(1,0);
			}
			
			public void onScrollRight() {
				update(0,1);
			}
	
			public void onScrollLeft() {
				update(0,-1);
			}
	
			public void onScrollUp() {
				update(-1,0);
			}
	
			public void onScrollDown() {
				update(1,0);
			}
		});
		
	}
	
	public void update(int moveX, int moveY) {
		
		int newX = player.x + moveX;
		int newY = player.y + moveY;
		Log.d("LOGCAT", "new X,Y = (" + newX + "," + newY + ")");
		Log.d("LOGCAT", "playerX,Y = (" + player.x + "," + player.y + ")");
		Log.d("LOGCAT", "cameraX,Y = (" + camera.x + "," + camera.y + ")");
		
		//check if player is trying to go out of bounds
		if ( newY > world.world_width - 1 || newX > world.world_height - 1 || newY < 0 || newX < 0) {
		
			Log.d("LOGCAT", "Player attempting to go out of bounds");
			
		} else { //check for collisions
		
			if (world.world_map[newX][newY] == 2) { //2 == stone
        
            Log.d("LOGCAT", "Trying to cross stone. Cancel move");
            
			} else { //update player and camera positions
				
				player.move(moveX, moveY);
				
				//moving up x-axis out of bounds condition
				if ( ( moveX == 1 && player.x <= (world.world_height - screen_height/2 - 1) && (player.x > screen_height/2 - 1)  ) ||
				     ( moveY == 1 && player.y <= (world.world_width - screen_width/2 - 1) && (player.y > screen_width/2 - 1)     ) ||
				     ( moveX == -1 && player.x < (world.world_height - screen_height/2 - 1) && (player.x >= screen_height/2 - 1) ) ||	 
				     ( moveY == -1 && player.y < (world.world_width - screen_width/2 - 1) && (player.y >= screen_width/2 - 1)    ) ) 
				{
					camera.move(moveX, moveY);	
				}
				
			}
				
		}
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}