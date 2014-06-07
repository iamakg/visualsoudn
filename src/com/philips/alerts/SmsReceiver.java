package com.philips.alerts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver  {

	private static final String TAG = "CustomPhoneStateListener";
	
	@Override
	public void onReceive(Context context, Intent intent) 
	{   
	    //this stops notifications to others
	    this.abortBroadcast();

	    //---get the SMS message passed in---
	    Bundle bundle = intent.getExtras();   
	    String recMsgString = "";
	    String fromAddress = "";
	    
	    SmsMessage recMsg = null;
	    byte[] data = null;

	    
	    if (bundle != null)
	    {
	    	Object[] pdus = (Object[]) bundle.get("pdus");
	        for (int i = 0; i < pdus.length; i++)
	        {
	          recMsg = SmsMessage.createFromPdu((byte[]) pdus[i]);

	          try
	          {
	            data = recMsg.getUserData();
	          }
	          catch (Exception e)
	          {

	          }
	          if (data != null)
	          {
	            for (int index = 0; index < data.length; ++index)
	            {
	              recMsgString += Character.toString((char) data[index]);
	            }
	          }

	          fromAddress = recMsg.getOriginatingAddress();
	        }
	        
	        Log.d("CustomPhoneStateListener", "TEXT RXD.");
	  }
	    
	    Intent result = new Intent(context, SmsReceiverActivity.class);
	    result.putExtra(SmsReceiverActivity.MESSAGE, "25500");
	    result.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    context.startActivity(result);
	}
}