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
		
		//get the tile map
		WorldFeatures worldFeatures = new WorldFeatures(this);		
		Map<Integer,Bitmap> TILE_MAP = worldFeatures.TILE_MAP;
		
		//Create a reference to the MapView object, set attributes
		mapView = (MapView) findViewById(R.id.map_view);
		mapView.setWorld(world);
		mapView.setTileMap(TILE_MAP);
		
		//implement the OnTouchSwipeListener
		mapView.setOnTouchListener(new OnSwipeTouchListener() {
			
			public void onSwipeRight() {
				mapView.movePlayer(0,1);
			}
			public void onSwipeLeft() {
				mapView.movePlayer(0,-1);
			}
			public void onSwipeUp() {
				mapView.movePlayer(-1,0);
			}
			public void onSwipeDown() {
				mapView.movePlayer(1,0);
			}
			public void onScrollRight() {
				mapView.movePlayer(0,1);
			}
	
			public void onScrollLeft() {
				mapView.movePlayer(0,-1);
			}
	
			public void onScrollUp() {
				mapView.movePlayer(-1,0);
			}
	
			public void onScrollDown() {
				mapView.movePlayer(1,0);
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

}