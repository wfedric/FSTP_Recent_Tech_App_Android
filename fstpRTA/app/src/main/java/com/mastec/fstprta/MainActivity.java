/**************************************************************
 * Created by William Fedric for Mastec Advanced Technologies *
 *                                                            *
 *                     December 2014                          *
 **************************************************************/
package com.mastec.fstprta;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        // Toast.makeText(this, R.string.firstRun, Toast.LENGTH.LONG).show();
    try {
        FSTPsearch.SetAlarm(this);
    }catch(Exception a){
    a.printStackTrace();
    //Toast.makeText(this, "Unable to start logger.", Toast.LENGTH_SHORT).show();
    }
        // makeSound.methodRun(this);
        super.finish();
    }
}
