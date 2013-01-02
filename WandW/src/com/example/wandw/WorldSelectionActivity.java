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
import java.io.File;
import android.app.ListActivity;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.util.Log;
import android.widget.Toast;

public class WorldSelectionActivity extends Activity {
	
	public ListView worldListView;
	public ArrayAdapter arrayAdapter;
	public String[] filenames;
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	public String message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.worldselection);
		
		Log.d("LOGCAT", "WorldSelectionActivity started");

		//create view on the fly
		worldListView = (ListView) findViewById(R.id.worldListView);
		
		//get the file data and dump it into an array
		File folder = new File("/data/data/com.example.wandw/files/");
		filenames = folder.list();
		
		//initialize the array adapter
		arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, filenames);
		
		//plug the view into the adapter
		worldListView.setAdapter(arrayAdapter);
		
		//listen for item clicks
		worldListView.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
			{
				//get the world choice and start the game
				message = parent.getItemAtPosition(position).toString();
				//message = "test_world";
				sendSelection();
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
	
	public void sendSelection() {
	Log.d("LOGCAT", "Sending World Selection to MenuActivity");
		Intent intent = new Intent();
		intent.putExtra(EXTRA_MESSAGE, message);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}

}	