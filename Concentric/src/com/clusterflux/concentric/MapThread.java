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
		
		while (mapRun) {
		
			mapCanvas = surfaceHolder.lockCanvas();
			
			if (mapCanvas != null) {

				mapView.doDraw(mapCanvas);
				surfaceHolder.unlockCanvasAndPost(mapCanvas);
				
			}
			
		}
		
	}
	
}