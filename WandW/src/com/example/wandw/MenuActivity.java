package com.example.wandw;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.util.Log;

public class MenuActivity extends Activity
{
	public String message; 
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	public int reqCode;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
	Log.d("LOGCAT", "MenuActivity Started");
        setContentView(R.layout.menu);
    }
	
	/**Called when the user clicks the Start button - loads WorldSelection activity*/
	public void startGame(View view) {
	Log.d("LOGCAT", "Loading WorldSelectionActivity");
		reqCode = 1;
		Intent intent = new Intent(this, WorldSelectionActivity.class);
		startActivityForResult(intent, reqCode);
	}
	
	/**Called when the user finishes the startGame() activity*/
	@Override
	public void onActivityResult(int reqCode, int resCode, Intent data) {
	Log.d("LOGCAT", "Getting WorldSelectionActivityResult");
		//get the world selection result
		if (reqCode == 1) {
			if (resCode == Activity.RESULT_OK) {
				message = data.getStringExtra(EXTRA_MESSAGE);
			}
		}
		
		//load the world
	Log.d("LOGCAT", "Starting GameActivity");		
		//start GameActivity
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}
	
	/**Called when the user clicks the Generate New World button */
	public void newWorld(View view) {
	Log.d("LOGCAT", "Starting WorldGenerationActivity");
		//do something in response to the button
		Intent intent = new Intent(this, WorldActivity.class);
		startActivity(intent);
	}

}