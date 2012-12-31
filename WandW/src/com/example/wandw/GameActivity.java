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
import java.io.*;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.util.Log;

public class GameActivity extends Activity {

	public ListView worldListView;
	public ArrayAdapter arrayAdapter;
	public String[] filenames;
	public static String world_name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	Log.d("LOGCAT", "GameActivityStarted");
		Intent intent = getIntent();
		world_name = intent.getStringExtra(MenuActivity.EXTRA_MESSAGE);
		setContentView(R.layout.game);
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