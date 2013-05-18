package com.clusterflux.concentric;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.content.Context;


public class MapThread extends Thread {

	boolean mapRun;
	Canvas mapCanvas;
	SurfaceHolder surfaceHolder;
	Context context;
	MapView mapView;
	private final int MAX_FPS = 25;
	private final int FRAME_PERIOD = 1000/ MAX_FPS;
	private final int MAX_FRAME_SKIPS = 5;
	
	public MapThread(SurfaceHolder surfaceHolder, Context context, MapView mapView) {
	
		this.surfaceHolder = surfaceHolder;
		this.context = context;
		mapRun = false;
		this.mapView = mapView;
		
	}
	
	public void setRunning(boolean mapRun) {
	
		this.mapRun = mapRun;
		
	}
	
	@Override
	public void run() {
		
		super.run();
		
		long beginTime;    // the time when the cycle began
		long timeDiff;     // the time it took for the cycle to execute;
		int sleepTime;     // ms to sleep ( < 0 if we're behind )
		int framesSkipped; // the number of frames being skipped
	
		sleepTime = 0;
		
		while (mapRun) {
		
			mapCanvas = null;
			
			try {
			
				mapCanvas = surfaceHolder.lockCanvas();
				
				synchronized(surfaceHolder) {
					beginTime = System.currentTimeMillis();
					framesSkipped = 0; //reset the frames skipped
					
					//update and render
					//mapView.update()
					mapView.doDraw(mapCanvas);
					
					timeDiff = System.currentTimeMillis() - beginTime; //calculate how long cycle took
					sleepTime = (int)(FRAME_PERIOD - timeDiff); //calculate sleep time
					
					if (sleepTime > 0) { //good, we're ahead
					
						try{
							Thread.sleep(sleepTime);
						} catch (InterruptedException e) {}
						
					}
					
					while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) { // we're behind, need to catch up
					
						//update without rendering
						//mapView.update(); 
					
						sleepTime += FRAME_PERIOD; //add frame period to check if in next frame
						framesSkipped++;
						
					}
					
				}
				
			} finally { //in case of exception, the surface is not left in an inconsistent state
			
				if (mapCanvas != null) {

					surfaceHolder.unlockCanvasAndPost(mapCanvas);
					
				}
				
			}
			
		}
		
	}
	
}