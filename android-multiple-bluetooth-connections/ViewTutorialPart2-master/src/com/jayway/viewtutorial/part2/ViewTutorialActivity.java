package com.jayway.viewtutorial.part2;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;








import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

public class ViewTutorialActivity extends Activity {
    /** Called when the activity is first created. */
	public static float floatArray[] = { 1, 1, 1, 1, 1, 1, 
		1, 1, 1, 1, 1, 1,1, 1, 1, 1, 1, 1,1, 1, 1, 1, 1, 1,1, 1, 1, 1, 1, 1,1, 1, 1, 1, 1, 1};

	//private float floatArray2[] = { 13, 14, 15, 16, 17, 18, 19, 0, 2, 14, 12, 1, 13, 15 };
	private LineChartView lineChart ;
	private DirectionView cv;
	private int randomNum2=15;
	
	//public String SensorID;
	
	 Handler handler = new Handler();
	    Runnable runnable = new Runnable() {
	        public void run() {
	            afficher();
	        }
	    };
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         
        lineChart = (LineChartView)findViewById(R.id.linechart);
        cv= (DirectionView)findViewById(R.id.Direction);
        runnable.run();
        
   
        
        
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.layout.view_sub, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	ArrayAdapter mArrayAdapter = null;
        switch (item.getItemId()) {
            case android.R.id.home:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                //NavUtils.navigateUpTo(this, new Intent(this, ViewTutorialActivity.class));
                return true;

            case R.id.action_add_item:
            	//Intent nextScreen = new Intent(getApplicationContext(), LayoutChangesActivity.class);
                //startActivity(nextScreen);   
              //  return true;
            	finish();
        }

        return super.onOptionsItemSelected(item);
    }

	  public void afficher()
      {
		     Intent i = getIntent();
		      String SensorID = i.getStringExtra("SensorID");
		  Random rand = new Random();
		  Log.e("tag" ,SensorID);
		  
	    	if (SensorID.compareTo("Sensor One")==0){
	    		 lineChart.setChartData(MyService0.floatArray);
	    		 Log.e("tag" ,"Here1");
	    		 cv.setBearing(MyService0.Direction,MyService0.Speed);
	    	}
	    	else
	    	{
	    		if (SensorID.compareTo("Sensor Two")==0){
	    			 lineChart.setChartData(MyService1.floatArray);	
	    			 cv.setBearing(MyService1.Direction,MyService1.Speed);
	    			 Log.e("tag" ,"Here2");
	    		}
	    		else {
	    			if (SensorID.compareTo("Sensor Three")==0){
	    				 lineChart.setChartData(MyService2.floatArray);	
	    				 cv.setBearing(MyService2.Direction,MyService2.Speed);
	    				 Log.e("tag" ,"Here3");
	        		}
	    			else {
	    				 lineChart.setChartData(MyService0.floatArray);
	    			}
	    		}
	    	}
	    	
		 
		    // nextInt is normally exclusive of the top value,
		    // so add 1 to make it inclusive
		//    float randomNum = MyService2.Speed;
		    
		//  for(int a = 0; a < floatArray.length-1; a++){  
		//	  floatArray[a]=floatArray[a+1];	  
		// }
		//  floatArray[floatArray.length-1]=randomNum;
    
          
       //   rand = new Random();
		 // lineChart.setChartData(floatArray);

		    
	    //    cv.setBearing(randomNum2++,randomNum);
	        
	        handler.postDelayed(runnable, 1000);
      }


}