package com.philips.alerts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras != null) {
			String state = extras.getString(TelephonyManager.EXTRA_STATE);
			Log.w("MY_DEBUG_TAG", state);
			if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
				String phoneNumber = extras
						.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

				Intent result = new Intent(context, SmsReceiverActivity.class);
				result.putExtra(SmsReceiverActivity.MESSAGE,
						"0");
				result.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(result);

				Log.w("MY_DEBUG_TAG", phoneNumber);
			}
		}
	}
}