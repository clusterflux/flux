package com.clusterflux.concentric;

import android.graphics.BitmapFactory;
import java.math.*;
import android.util.Log;
import java.util.*;
import android.graphics.Bitmap;
import android.content.Context;

public class WorldFeatures {
	
	public final Map<Integer, Bitmap> TILE_MAP;
	public final Bitmap SPRITE;
	
	public WorldFeatures(Context context) {
	
		//get the sprite
		SPRITE = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite);
	
		TILE_MAP = new HashMap<Integer, Bitmap>();
	
		//add integer keys and tiles to hash
		TILE_MAP.put(0, BitmapFactory.decodeResource(context.getResources(), R.drawable.dirt));
		TILE_MAP.put(1, BitmapFactory.decodeResource(context.getResources(), R.drawable.grass));
		TILE_MAP.put(2, BitmapFactory.decodeResource(context.getResources(), R.drawable.stone));
		
	}
	
}
