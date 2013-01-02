package com.example.wandw;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.EditText;
import java.util.*;
import android.widget.TextView;
import java.lang.Integer;
import java.io.FileOutputStream;
import java.lang.String;
import android.content.Context;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import android.util.Log;

public class WorldGenerationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	Log.d("LOGCAT", "WorldGenerationActivity started");
		setContentView(R.layout.worldgeneration);
		
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
	
	public void generateWorld (View view) { //create the view on the fly, 
											//we don't need it very long
	Log.d("LOGCAT", "Getting World Information");
		//get the context so it can be passed to the World class
		Context context = getApplicationContext();
		
		//get the user input and convert it to string/int
		EditText editText = (EditText) findViewById(R.id.world_name);
		String world_name = editText.getText().toString();
		
		editText = (EditText) findViewById(R.id.world_width);
		int world_width = Integer.parseInt(editText.getText().toString());
		
		editText = (EditText) findViewById(R.id.world_height);
		int world_height = Integer.parseInt(editText.getText().toString());
	Log.d("LOGCAT", "Generating the World");	
		//generate and save the World
		World world = new World(world_name, world_width, world_height);
	Log.d("LOGCAT", "Saving the World");	
		try {
		world.saveWorld(context, world_name, world);
		} catch (IOException e) {
		//do nothing
		}
	Log.d("LOGCAT", "Display World Generation Success");	
		//create a text view to display the results
		TextView textView = new TextView(this);
		textView.setTextSize(40);
		textView.setText("World Generated! Press back and load your world!");
		setContentView(textView);

	}
		
}	