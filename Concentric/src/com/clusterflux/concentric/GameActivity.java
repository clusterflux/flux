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

public class GameActivity extends Activity {
	
	public MapView mapView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);	
		Log.d("LOGCAT", "GameActivity Started");
		
		//get the world_name from MenuActivity
		Intent intent = getIntent();
		String world_name = intent.getStringExtra(MenuActivity.EXTRA_MESSAGE);
		
		//load the world
		World world = loadWorld(world_name);
		
		//create a tilemap and get the tile translator array from it //need to convert this to a static Map
		TileMap tileMap = new TileMap(this);	
		Map<Integer,Bitmap> tileTranslator = tileMap.getTileTranslator();
		
		//Create a reference to the MapView object and set the translator
		mapView = (MapView) findViewById(R.id.map_view);
		mapView.setArgs(world, tileTranslator);
		
		//implement the OnTouchSwipeListener
		mapView.setOnTouchListener(new OnSwipeTouchListener() {
			
			public void onSwipeRight() {
				movePlayer(0,1);
			}
			public void onSwipeLeft() {
				movePlayer(0,-1);
			}
			public void onSwipeUp() {
				movePlayer(-1,0);
			}
			public void onSwipeDown() {
				movePlayer(1,0);
			}
		});
		
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
	
	public World loadWorld(String world_name) {
	
	//Create a dummy world to load into - why?!
		World dummy_world = new World();
		
		//load the world 
		Log.d("LOGCAT", "Loading the World");
		try {
			World world = dummy_world.loadWorld(this, world_name);
			return world;
		} catch (IOException e) {
		//do nothing!
		} catch (ClassNotFoundException f) {
		//do nothing!
		}
		
		return dummy_world; //if world load fails, send back the default world
							// NOTE: it's not saved!!!
				
	}
	
	public void movePlayer(int x, int y) {
	
		mapView.movePlayer(x,y);
		
	}

}