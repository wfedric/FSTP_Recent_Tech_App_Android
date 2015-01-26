/**************************************************************
 * Created by William Fedric for Mastec Advanced Technologies *
 *                     December 2014                          *
 **************************************************************/
package com.mastec.fstprta;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

public class makeSound extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public static void methodRun(Context context) {
        MediaPlayer mP = MediaPlayer.create(context, R.raw.ding);
        mP.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mP.start();
    }
}
