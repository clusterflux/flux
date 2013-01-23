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
	public final int tile_height;
	public final int tile_width;
	public final Map<String, Bitmap> SHADOW;
	
	public WorldFeatures(Context context) {
	
		//get the sprite
		SPRITE = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite);
	
		TILE_MAP = new HashMap<Integer, Bitmap>();
	
		//add integer keys and tiles to hash
		TILE_MAP.put(1, BitmapFactory.decodeResource(context.getResources(), R.drawable.dirt));
		TILE_MAP.put(2, BitmapFactory.decodeResource(context.getResources(), R.drawable.grass));
		TILE_MAP.put(3, BitmapFactory.decodeResource(context.getResources(), R.drawable.stone));
		TILE_MAP.put(4, BitmapFactory.decodeResource(context.getResources(), R.drawable.water));
		
		tile_width = TILE_MAP.get(1).getHeight()/2 - 2; //account for overlap
		tile_height = TILE_MAP.get(1).getWidth() - 1; 
		
		SHADOW = new HashMap<String, Bitmap>();
	
		//add string keys and shadow tiles to hash
		SHADOW.put("north", BitmapFactory.decodeResource(context.getResources(), R.drawable.shadow_north));
		SHADOW.put("south", BitmapFactory.decodeResource(context.getResources(), R.drawable.shadow_south));
		SHADOW.put("east", BitmapFactory.decodeResource(context.getResources(), R.drawable.shadow_east));
		SHADOW.put("west", BitmapFactory.decodeResource(context.getResources(), R.drawable.shadow_west));
		SHADOW.put("northeast", BitmapFactory.decodeResource(context.getResources(), R.drawable.shadow_northwest));
		SHADOW.put("northwest", BitmapFactory.decodeResource(context.getResources(), R.drawable.shadow_northeast));
		SHADOW.put("southeast", BitmapFactory.decodeResource(context.getResources(), R.drawable.shadow_southwest));
		SHADOW.put("southwest", BitmapFactory.decodeResource(context.getResources(), R.drawable.shadow_southeast));
		SHADOW.put("sidewest", BitmapFactory.decodeResource(context.getResources(), R.drawable.shadow_sidewest));
		
	}
	
}
