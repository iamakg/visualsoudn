package com.philips.alerts;

import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class SmsReceiverActivity extends Activity {

	private PHHueSDK phHueSDK;
    private static final int MAX_HUE=65535;
    public static final String TAG = "QuickStart";
	public static final String MESSAGE = "message";

	  @Override
	  protected void onCreate(Bundle savedInstanceState)
	  {
	    super.onCreate(savedInstanceState);
	    
	    //setContentView(R.layout.activity_received);
	    
	    phHueSDK = PHHueSDK.create();

	    Intent intent = getIntent();

	    int message = Integer.parseInt(intent.getStringExtra(MESSAGE));

    	Toast toast = Toast.makeText(this, String.valueOf(message), 5000);
    	toast.show();
    	randomLights(message);
	  }
	  
	  public void randomLights(int color) {
	        PHBridge bridge = phHueSDK.getSelectedBridge();

	        List<PHLight> allLights = bridge.getResourceCache().getAllLights();
	        Random rand = new Random();
	        
	        for (PHLight light : allLights) {
	            PHLightState lightState = new PHLightState();
	            //lightState.setHue(rand.nextInt(MAX_HUE));
	            
	            lightState.setHue(color);
	            
	            
	            // To validate your lightstate is valid (before sending to the bridge) you can use:  
	            // String validState = lightState.validateState();
	            bridge.updateLightState(light, lightState, listener);
	            //  bridge.updateLightState(light, lightState);   // If no bridge response is required then use this simpler form.
	        }
	    }
		
	    // If you want to handle the response from the bridge, create a PHLightListener object.
	    PHLightListener listener = new PHLightListener() {
	        
	        @Override
	        public void onSuccess() {  
	        }
	        
	        @Override
	        public void onStateUpdate(Hashtable<String, String> arg0, List<PHHueError> arg1) {
	           Log.w(TAG, "Light has updated");
	        }
	        
	        @Override
	        public void onError(int arg0, String arg1) {  
	        }
	    };
}
