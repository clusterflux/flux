package com.clusterflux.concentric;

import java.math.*;
import java.util.*;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import android.content.Context;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class World implements Serializable {

	public String world_name;
	public int world_width;
	public int world_height;
	public int[][] world_map;
	
	public World(String world_name, int world_width, int world_height) {

		//set the world attributes
		this.world_name = world_name;
		this.world_width = world_width;
		this.world_height = world_height;
		
		//generate the map
		world_map = createWorldMap(world_width, world_height);
	
	}
	
	public World(Context context, String world_name) throws IOException, ClassNotFoundException {
		
		//load world from file
		FileInputStream fis = context.openFileInput(world_name);
		ObjectInputStream ois = new ObjectInputStream(fis);
		World world = (World)ois.readObject();
		
		//set the world attributes
		this.world_name = world.world_name;
		this.world_width = world.world_width;
		this.world_height = world.world_height;
		this.world_map = world.world_map;
		
	}
	
	private int[][] createWorldMap(int world_width, int world_height) {
	
		//create a local tile map 
		int[][] world_map = new int[world_width][world_height];
		
		//get a randomizer to fill the array with - {temporary solution}
		Random rand = new Random();

		//fill the tile map array with random numbers between 0 and 2
		for(int row = 0; row < world_map.length; row++) {
			for (int col = 0; col < world_map[row].length; col++) {
				world_map[row][col] = rand.nextInt(3);  //static number, needs variable!
														//3 is the number of tile types
			}
		}
		
		return world_map;
		
	}	
	
	public void saveWorld(Context context, String world_name, World world) throws IOException {
		
		//save world object to file named world_name
		FileOutputStream fos = context.openFileOutput(world_name, Context.MODE_PRIVATE);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(world);
		oos.close();
		
	}
	
}