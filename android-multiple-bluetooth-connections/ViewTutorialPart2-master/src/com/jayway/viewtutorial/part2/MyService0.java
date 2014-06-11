package com.jayway.viewtutorial.part2;

import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService0 extends Service{
	
	public static float floatArray[] = { 1, 1, 1, 1, 1, 1, 
		1, 1, 1, 1, 1, 1,1, 1, 1, 1, 1, 1,1, 1, 1, 1, 1, 1,1, 1, 1, 1, 1, 1,1, 1, 1, 1, 1, 1,};
	
	public static int Direction;
	public static float Speed ;
	private static final String TAG = "MyService";

	 Handler handler = new Handler();
	    Runnable runnable = new Runnable() {
	        public void run() {
	            afficher();
	        }
	    };
	    
	    
	    public void afficher()
	      {
			  
			  Random rand = new Random();
			  
			    // nextInt is normally exclusive of the top value,
			    // so add 1 to make it inclusive
			    float randomNum = rand.nextInt(80);
			   randomNum = randomNum/10;
			    
			  for(int a = 0; a < floatArray.length-1; a++){  
				  floatArray[a]=floatArray[a+1];	  
			 }
			  floatArray[floatArray.length-1]=randomNum;          
	           rand = new Random();   
	           
	           Speed=randomNum;
	           Direction= rand.nextInt((360 - 0) + 1) + 1;
		        handler.postDelayed(runnable, 1000);
	      }
	    
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {

		//Toast.makeText(this, "Airflow Sensor One Is Connected", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onCreate");
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Toast.makeText(this, "Airflow Sensor One ", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onStart");	
		runnable.run();
	}
	
	@Override
	public void onDestroy() {
		Toast.makeText(this, "Airflow Sensor One Is Removed", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onDestroy");
	}
}
