/**************************************************************
 * Created by William Fedric for Mastec Advanced Technologies *
 *                     December 2014                          *
 **************************************************************/
package com.mastec.fstprta;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ApplicationRun extends Service {

    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {
        FSTPsearch.SetAlarm(ApplicationRun.this);
        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}



