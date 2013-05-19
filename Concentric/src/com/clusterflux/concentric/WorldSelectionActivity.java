package com.clusterflux.concentric;

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
		
		//get the file data and dump it into an array
		File folder = new File("/data/data/com.clusterflux.concentric/files/");
		filenames = folder.list();
		
		//no worlds condition
		if ( filenames == null ) {
		Log.d("LOGCAT", "null files");
			Intent intent = new Intent();
			intent.putExtra(EXTRA_MESSAGE, "null");
			setResult(Activity.RESULT_CANCELED, intent);
			finish();

		} else {

			//create view on the fly
			worldListView = (ListView) findViewById(R.id.worldListView);
		
			//initialize the array adapter
			arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, filenames);
		
			//plug the view into the adapter
			worldListView.setAdapter(arrayAdapter);
		
			//listen for item clicks
			worldListView.setOnItemClickListener(new OnItemClickListener() 
			{
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
				{
					Toast.makeText(WorldSelectionActivity.this, "LOADING WORLD", Toast.LENGTH_LONG).show();
				
					//get the world_name and send it back to MenuActivity
					message = parent.getItemAtPosition(position).toString();
					sendSelection();
				}
			});
			
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
	
	public void sendSelection() { //send world_name back to MenuActivity
	
		Log.d("LOGCAT", "Sending world_name to MenuActivity");
		Intent intent = new Intent();
		intent.putExtra(EXTRA_MESSAGE, message);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
	
}