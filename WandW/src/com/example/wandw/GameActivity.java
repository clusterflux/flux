package com.example.wandw;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import java.io.*;
import android.util.Log;
import android.widget.Toast;

public class GameActivity extends Activity {
	
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
		
		//Create a mapview object and set the current world
		MapView mapView = (MapView) findViewById(R.id.map_view);
		mapView.setWorld(world);
		
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

}