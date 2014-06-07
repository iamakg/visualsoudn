package com.philips.alerts;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CustomPhoneStateListener extends PhoneStateListener {

	private static final String TAG = "CustomPhoneStateListener";
	
	private boolean isPhoneCalling = false;
	
    private Context context = null;
    
    public CustomPhoneStateListener() {
    }
    
    public CustomPhoneStateListener(Context context) {
    	this.context = context;
    }
    
	@Override
	public void onCallStateChanged(int state, String incomingNumber){

		if (TelephonyManager.CALL_STATE_RINGING == state) {
            // phone ringing
            Log.i(TAG, "RINGING, number: " + incomingNumber);
            
            Intent result = new Intent(context, SmsReceiverActivity.class);
    	    result.putExtra(SmsReceiverActivity.MESSAGE, "Received Call");
    	    result.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	    context.startActivity(result);
        }
		
		if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
            // active
            Log.i(TAG, "OFFHOOK");

            isPhoneCalling = true;
        }
		
		if (TelephonyManager.CALL_STATE_IDLE == state) {
            // run when class initial and phone call ended, need detect flag
            // from CALL_STATE_OFFHOOK
            Log.i(TAG, "IDLE number");

            if (isPhoneCalling) {

                isPhoneCalling = false;
            }
        }
	}
}