package com.clusterflux.concentric;

import android.graphics.BitmapFactory;
import java.math.*;
import android.util.Log;
import java.util.*;
import android.graphics.Bitmap;
import android.content.Context;

public class TileMap {

	public Map<Integer,Bitmap> tileTranslator;

	public TileMap(Context context) { 
	
		Log.d("LOGCAT", "Generating TileMap");
		
		//create hash for translating world array to bitmap tiles
		tileTranslator = new HashMap<Integer, Bitmap>();

		//add integer keys and tiles to hash
		tileTranslator.put(0, BitmapFactory.decodeResource(context.getResources(), R.drawable.dirt));
		tileTranslator.put(1, BitmapFactory.decodeResource(context.getResources(), R.drawable.grass));
		tileTranslator.put(2, BitmapFactory.decodeResource(context.getResources(), R.drawable.stone));

	}
	
	public Map<Integer,Bitmap> getTileTranslator() {
	
		return tileTranslator;
		
	}
	
	public int getSize() {
	
		return tileTranslator.size();
		
	}
	
}