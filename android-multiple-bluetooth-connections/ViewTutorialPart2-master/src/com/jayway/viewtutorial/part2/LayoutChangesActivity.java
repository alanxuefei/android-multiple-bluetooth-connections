/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jayway.viewtutorial.part2;

import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
//import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This sample demonstrates how to use system-provided, automatic layout transitions. Layout
 * transitions are animations that occur when views are added to, removed from, or changed within
 * a {@link ViewGroup}.
 *
 * <p>In this sample, the user can add rows to and remove rows from a vertical
 * {@link android.widget.LinearLayout}.</p>
 */
@SuppressLint("ValidFragment")
public class LayoutChangesActivity extends Activity {
    private static final int REQUEST_ENABLE_BT = 0;
    
    final Context context = this;
	//private Button button;

	/**
     * The container view which has layout change animations turned on. In this sample, this view
     * is a {@link android.widget.LinearLayout}.
     */
    private ViewGroup mContainerView;
    
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        public void run() {
            afficher();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_changes);

        mContainerView = (ViewGroup) findViewById(R.id.container);        
        runnable.run();

        
    }
    
    //@Override
   // public Dialog onCreateDialog(Bundle savedInstanceState) {
       
   //     return builder.create();
   // }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.layout.activity_layout_changes_sub, menu);
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
                // Hide the "empty" view since there is now at least one item in the list.
                
                findViewById(android.R.id.empty).setVisibility(View.GONE);
        		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
        				context);
         
        			// set title
        			
         
        			// set dialog message
        			alertDialogBuilder.setTitle("List of sensors")
        	           .setItems(COUNTRIES, new DialogInterface.OnClickListener() {
        	               public void onClick(DialogInterface dialog, int which) {
        	               // The 'which' argument contains the index position
        	               // of the selected item
        	               addItem(which);
        	           }
        	    });
         
        				// create alert dialog
        				AlertDialog alertDialog = alertDialogBuilder.create();
         
        				// show it
        				alertDialog.show();
                
                
                
               
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addItem(int which) {
        // Instantiate a new "row" view.
        final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(
                R.layout.list_item_example, mContainerView, false);

        // Set the text in the new row to a random country.
        ((TextView) newView.findViewById(R.id.SensorID)).setText(
                COUNTRIES[which]);
        
        // Set the text in the new row to a random country.
        ((TextView) newView.findViewById(R.id.AirSpeed)).setText(
        		Airspeed[(int) (Math.random() * Airspeed.length)]);
        
        // Set the text in the new row to a random country.
        ((TextView) newView.findViewById(R.id.AirDirection)).setText(
        		AirDirection[(int) (Math.random() * AirDirection.length)]);
      
        //start the service from here //MyService is your service class name
        
        //int index0 = mContainerView.indexOfChild(newView);
        mystartservice(which);
      		

        	
      		// Set a click listener for the "X" button in the row that will remove the row.
        newView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	 	
                // Remove the row from its parent (the container view).
                // Because mContainerView has android:animateLayoutChanges set to true,
                // this removal is automatically animated.
               // ViewGroup parent = (ViewGroup) newView.getParent();
            	String index = ((TextView) newView.findViewById(R.id.SensorID)).getText().toString();
         
                mContainerView.removeView(newView);
 
                // If there are no rows remaining, show the empty view.
                if (mContainerView.getChildCount() == 0) {
                    findViewById(android.R.id.empty).setVisibility(View.VISIBLE);  
                }
                

                mystopservice(index);
            }
        });
        
  
        
        ((TextView) newView.findViewById(R.id.AirDirection)).setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
            	String sensorID = ((TextView) newView.findViewById(R.id.SensorID)).getText().toString();
            	swichtoDataDispalyPage(sensorID);
             
            }  
        });
        
        ((TextView) newView.findViewById(R.id.AirSpeed)).setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
            	String sensorID = ((TextView) newView.findViewById(R.id.SensorID)).getText().toString();
            	swichtoDataDispalyPage(sensorID);
             
            }  
        });
        
        ((TextView) newView.findViewById(R.id.SensorID)).setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
            	String sensorID = ((TextView) newView.findViewById(R.id.SensorID)).getText().toString();
            	swichtoDataDispalyPage(sensorID);
            }  
        });
        // Because mContainerView has android:animateLayoutChanges set to true,
        // adding this view is automatically animated.
        mContainerView.addView(newView, 0);
    }

    /**
     * A static list of country names.
     */
    private static final String[] COUNTRIES = new String[]{
            "Sensor One", "Sensor Two", "Sensor Three", 
    };
    /**
     * A static list of country names.
     */
    private static final String[] Airspeed = new String[]{
            "1 m/s", "2 m/s", "3 m/s", 
    };
    
    /**
     * A static list of country names.
     */
    private static final String[] AirDirection = new String[]{
            "10бу", "180бу", "320бу", 
    };
    
    public void afficher()
    {
     
    	
        handler.postDelayed(runnable, 1000);
        for(int i=0; i<((ViewGroup)mContainerView).getChildCount(); ++i) {
            View nextChild = ((ViewGroup)mContainerView).getChildAt(i);
         // Set the text in the new row to a random country.
            String SensorID = ((TextView) nextChild.findViewById(R.id.SensorID)).getText().toString();
        	
        	if (SensorID.compareTo("Sensor One")==0){
        		((TextView) nextChild.findViewById(R.id.AirSpeed)).setText(
        				Float.toString(MyService0.Speed)+"m/s");
                
                // Set the text in the new row to a random country.
                ((TextView) nextChild.findViewById(R.id.AirDirection)).setText(
                		Float.toString(MyService0.Direction)+"бу");
       	}
       	else
       	{
        	if (SensorID.compareTo("Sensor Two")==0){
        		
        		((TextView) nextChild.findViewById(R.id.AirSpeed)).setText(
        				Float.toString(MyService1.Speed)+"m/s");
                
                // Set the text in the new row to a random country.
                ((TextView) nextChild.findViewById(R.id.AirDirection)).setText(
                		Float.toString(MyService1.Direction)+"бу");
                
      			
      		}
      		else {
      			if (SensorID.compareTo("Sensor Three")==0){
      	     		((TextView) nextChild.findViewById(R.id.AirSpeed)).setText(
            				Float.toString(MyService2.Speed)+"m/s");
                    
                    // Set the text in the new row to a random country.
                    ((TextView) nextChild.findViewById(R.id.AirDirection)).setText(
                    		Float.toString(MyService2.Direction)+"бу");
          		}
      			else {
      				//lineChart.setChartData(MyService0.floatArray);
      			}
      		}
      	}
            
            
            
            
        }
    };
    
    public void mystartservice(int index)
    {
    	//Stop the running service from here//MyService is your service class name
    			//Service will only stop if it is already running.
    	
    	switch(index) {
        case 0:
        	startService(new Intent(this, MyService0.class)); 
            break;
        case 1:
        	startService(new Intent(this, MyService1.class));
            break;      
        case 2:
        	startService(new Intent(this, MyService2.class));
            break;   
        }
    		
    };
    
    public void mystopservice(String index)
    {
    	
       // Log.e("tag" ,index);
    	//Stop the running service from here//MyService is your service class name
    			//Service will only stop if it is already running.
    	if (index == "Sensor One"){
    		stopService(new Intent(this, MyService0.class));   		
    	}
    	else
    	{
    		if (index == "Sensor Two"){
    			stopService(new Intent(this, MyService1.class)); 			
    		}
    		else {
    			if (index == "Sensor Three"){
        			stopService(new Intent(this, MyService2.class)); 			
        		}
    		}
    	}

    		
    };
    
    public void swichtoDataDispalyPage(String ID){
  	  Intent nextScreen = new Intent(getApplicationContext(), ViewTutorialActivity.class);
        //Sending data to another Activity
          nextScreen.putExtra("SensorID", ID);
         // nextScreen.putExtra("email", "2");
          startActivity(nextScreen);
  	
  };
    
    public class FireMissilesDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Select an airflow sensor")
            .setItems(COUNTRIES, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                // The 'which' argument contains the index position
                // of the selected item
            }
     });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

}
