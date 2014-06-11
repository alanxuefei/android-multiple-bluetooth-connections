package com.jayway.viewtutorial.part2;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import java.util.Timer;  
import java.util.TimerTask;  
import java.util.UUID;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import android.os.Build;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class MyService2 extends Service{
	
	Timer t;  
	TimerTask task;  
	
	  /*** Bluetooth 1  ***/
	 
	  private BluetoothAdapter btAdapter = null;
	  private BluetoothSocket btSocket = null;
	  private StringBuilder sb = new StringBuilder();
	  
	  private ConnectedThread mConnectedThread;
	  	   
	  // SPP UUID service
	  private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	 
	  // MAC-address of Bluetooth module (you must edit this line)
	  private static String address = "00:06:66:4E:3E:BD";
	  //private static String address = "98:D3:31:B2:00:14";
	  
	

		/*** Bluetooth   ***/
	
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
			  for(int a = 0; a < floatArray.length-1; a++){  
				  floatArray[a]=floatArray[a+1];	  
			 }
			  floatArray[floatArray.length-1]=Speed; 
			  handler.postDelayed(runnable, 1000);
	      }
	    
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {

	//	Toast.makeText(this, "Airflow Sensor Three Is Connected", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onCreate");
		
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Toast.makeText(this, "Airflow Sensor Three Is Connected", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onStart");	
		startMeasureResult();
		runnable.run();
	}
	
	@Override
	public void onDestroy() {
		Toast.makeText(this, "Airflow Sensor Three Is Removed", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onDestroy");
	}
	
	  
	 public void startMeasureResult() {
		 /*bluetooth2*/
		    
		    btAdapter = BluetoothAdapter.getDefaultAdapter();		// get Bluetooth adapter

		    
		    
		 // Set up a pointer to the remote node using it's address.
		    BluetoothDevice device = btAdapter.getRemoteDevice(address);
		   
		    // Two things are needed to make a connection:
		    //   A MAC address, which we got above.
		    //   A Service ID or UUID.  In this case we are using the
		    //     UUID for SPP.
		    
			try {
				btSocket = createBluetoothSocket(device);
			} catch (IOException e) {
				errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
			}
		    

		   
		    // Discovery is resource intensive.  Make sure it isn't going on
		    // when you attempt to connect and pass your message.
		    btAdapter.cancelDiscovery();
		   
		    // Establish the connection.  This will block until it connects.
		    Log.d("bluetooth", "...Connecting...");
		   // Toast.makeText(MicroAnemometerActivity.this, "Bluetooth ...Connecting...", Toast.LENGTH_SHORT).show();
		    try {
		      btSocket.connect();
		      Log.d("bluetooth", "....Connection ok...");
		      //Toast.makeText(MicroAnemometerActivity.this, "Bluetooth ....Connection ok...", Toast.LENGTH_SHORT).show();
		    } catch (IOException e) {
		      try {
		        btSocket.close();
		      } catch (IOException e2) {
		        errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
		      }
		    }
		     
		    // Create a data stream so we can talk to server.
		    Log.d("bluetooth", "...Create Socket...");
		   
		    mConnectedThread = new ConnectedThread(btSocket);
		    mConnectedThread.start();       
	  }
	
	
	
	
	  private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
	      if(Build.VERSION.SDK_INT >= 10){
	          try {
	              final Method  m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
	              return (BluetoothSocket) m.invoke(device, MY_UUID);
	          } catch (Exception e) {
	             // Log.e(TAG, "Could not create Insecure RFComm Connection",e);
	          }
	      }
	      return  device.createRfcommSocketToServiceRecord(MY_UUID);
	  }
	 
  public void setMeasureResult(String str) {
      int index1=-1;
      int index2=-1;
      int index3=-1;
      String str3,str2="0";
   
      //Speed++; 
              //index1 = str.lastIndexOf("sensor = ");
              index2 = str.lastIndexOf("end");
              if (index2!=-1){
              	str3=str.substring(0, index2);
              	index1 = str3.lastIndexOf("Direction");
                  if ((index2>index1+1)&&(index2!=-1)&&(index1!=-1)){
                  	str2=str.substring(index1+9, index2);
                  	Direction=Integer.parseInt(str2);
                  	
                  }
                  index3 = str3.lastIndexOf("Speed");
                  if ((index1>index3+1)&&(index1!=-1)&&(index3!=-1)){
                  	str2=str.substring(index3+5, index1);
                  	 
                  	Speed=Float.parseFloat(str2);
                  }
              }        
  }


	 
	  private void checkBTState() {
		    // Check for Bluetooth support and then check to make sure it is turned on
		    // Emulator doesn't support Bluetooth and will return null
		  
		    if(btAdapter==null) { 
		    
		   //   errorExit("Fatal Error", "Bluetooth 2");
		    } else {
		      if (btAdapter.isEnabled()) {   
		       
		      } else {
	
		        //Prompt user to turn on Bluetooth
		       // Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		        //startActivityForResult(enableBtIntent, 1);
		      }
		    }
		  }
	  
	  private void errorExit(String title, String message){
		    Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
		    
		  }
	
		  private class ConnectedThread extends Thread {
			    private final InputStream mmInStream;
			    private final OutputStream mmOutStream;
			 
			    public ConnectedThread(BluetoothSocket socket) {
			        InputStream tmpIn = null;
			        OutputStream tmpOut = null;
			 
			        // Get the input and output streams, using temp objects because
			        // member streams are final
			        try {
			            tmpIn = socket.getInputStream();
			            tmpOut = socket.getOutputStream();
			        } catch (IOException e) { }
			 
			        mmInStream = tmpIn;
			        mmOutStream = tmpOut;
			    }
			 
			    public void run() {
			        byte[] buffer = new byte[256];  // buffer store for the stream
			        int bytes; // bytes returned from read()

			        // Keep listening to the InputStream until an exception occurs
			        while (true) {
			        	try {
			                // Read from the InputStream
			                bytes = mmInStream.read(buffer);		// Get number of bytes and messan "buffer"
			                String readMessage = new String(buffer, 0, bytes);
			                sb.append(readMessage);
			                int endOfLineIndex = sb.indexOf("\r\n");	
			                if (endOfLineIndex > 0) { 											// if end-of-line,
			            		String sbprint = sb.substring(0, endOfLineIndex);				// extract string
			            		
			            		setMeasureResult(sbprint);// update TextView
			            		sb.delete(0, sb.length());										// and clear
			                   
								
			                   
			                    Log.d("xuefei", sbprint);
			                }

			                
			            } catch (IOException e) {
			                break;
			            }
			        }
			    }
			 

			}
}
