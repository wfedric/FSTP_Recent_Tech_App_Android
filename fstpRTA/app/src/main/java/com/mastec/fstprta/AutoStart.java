/**************************************************************
 * Created by William Fedric for Mastec Advanced Technologies *
 *                     December 2014                          *
 **************************************************************/
package com.mastec.fstprta;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoStart extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        FSTPsearch.SetAlarm(context);
    }
}
