package com.philips.lighting.quickstart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

/**
 * MyApplicationActivity - The starting point for creating your own Hue App.
 * Currently contains a simple view with a button to change your lights to
 * random colours. Remove this and add your own app implementation here! Have
 * fun!
 * 
 * @author SteveyO
 * 
 */
public class MyApplicationActivity extends Activity {
	private PHHueSDK phHueSDK;
	private static final int MAX_HUE = 65535;
	public static final String TAG = "QuickStart";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.app_name);
		setContentView(R.layout.activity_main);
		phHueSDK = PHHueSDK.create();
		Button randomButton;
		randomButton = (Button) findViewById(R.id.buttonRand);
		randomButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				randomLights();
			}
		});

//		Spinner spinnerCall = (Spinner) findViewById(R.id.spinner_Call);
//		
//		PHHueSDK phHueSDK = PHHueSDK.getInstance();
//		PHBridge bridge = phHueSDK.getSelectedBridge();
//
//		List<PHLight> ls = bridge.getResourceCache().getAllLights();
//
//		final ArrayList<String> list = new ArrayList<String>();
//	    for (int i = 0; i < ls.size(); ++i) {
//	      list.add(ls.get(i).getName());
//	    }
//	    
//		
//		final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);
//		spinnerCall.setAdapter(adapter);
//		    
//		    
//		
//
//			 ArrayAdapter<CharSequence> adapterCall = ArrayAdapter.createFromResource(this, R.array.colors_array, android.R.layout.simple_spinner_item);
//			 adapterCall.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//			 spinnerCall.setAdapter(adapterCall);
//		    
		    
//		// Create an ArrayAdapter using the string array and a default spinner layout
//		ArrayAdapter<CharSequence> adapterCall = ArrayAdapter
//				.createFromResource(this, R.array.colors_array,
//						android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
//		adapterCall
//				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		// Apply the adapter to the spinner
//		spinnerCall.setAdapter(adapterCall);

		 Spinner spinnerCall = (Spinner) findViewById(R.id.spinner_Call);
		 // Create an ArrayAdapter using the string array and a default
		 ArrayAdapter<CharSequence> adapterCall =
		 ArrayAdapter.createFromResource(this,
		 R.array.colors_array, android.R.layout.simple_spinner_item);
		 // Specify the layout to use when the list of choices appears
		 adapterCall.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 // Apply the adapter to the spinner
		 spinnerCall.setAdapter(adapterCall);
		
		
		 Spinner spinnerText = (Spinner) findViewById(R.id.spinner_Text);
		 // Create an ArrayAdapter using the string array and a default
		 ArrayAdapter<CharSequence> adapterText =
		 ArrayAdapter.createFromResource(this,
		 R.array.colors_array, android.R.layout.simple_spinner_item);
		 // Specify the layout to use when the list of choices appears
		 adapterText.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 // Apply the adapter to the spinner
		 spinnerText.setAdapter(adapterText);

	}

	public void randomLights() {
		PHBridge bridge = phHueSDK.getSelectedBridge();

		List<PHLight> allLights = bridge.getResourceCache().getAllLights();
		Random rand = new Random();

		for (PHLight light : allLights) {
			PHLightState lightState = new PHLightState();
			lightState.setHue(rand.nextInt(MAX_HUE));
			// To validate your lightstate is valid (before sending to the
			// bridge) you can use:
			// String validState = lightState.validateState();
			bridge.updateLightState(light, lightState, listener);
			// bridge.updateLightState(light, lightState);
			// If no bridge response is required then use this simpler form.
		}
	}

	// If you want to handle the response from the bridge, create a
	// PHLightListener object.
	PHLightListener listener = new PHLightListener() {

		@Override
		public void onSuccess() {
		}

		@Override
		public void onStateUpdate(Hashtable<String, String> arg0,
				List<PHHueError> arg1) {
			Log.w(TAG, "Light has updated");
		}

		@Override
		public void onError(int arg0, String arg1) {
		}
	};

	@Override
	protected void onDestroy() {
		PHBridge bridge = phHueSDK.getSelectedBridge();
		if (bridge != null) {

			if (phHueSDK.isHeartbeatEnabled(bridge)) {
				phHueSDK.disableHeartbeat(bridge);
			}

			phHueSDK.disconnect(bridge);
			super.onDestroy();
		}
	}
	
	
	private class StableArrayAdapter extends ArrayAdapter<String> {

	    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	    public StableArrayAdapter(Context context, int textViewResourceId,
	        List<String> objects) {
	      super(context, textViewResourceId, objects);
	      for (int i = 0; i < objects.size(); ++i) {
	        mIdMap.put(objects.get(i), i);
	      }
	    }

	    @Override
	    public long getItemId(int position) {
	      String item = getItem(position);
	      return mIdMap.get(item);
	    }

	    @Override
	    public boolean hasStableIds() {
	      return true;
	    }

	  }
}
