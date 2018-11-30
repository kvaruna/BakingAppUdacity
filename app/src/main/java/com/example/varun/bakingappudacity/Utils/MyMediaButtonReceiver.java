package com.example.varun.bakingappudacity.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.media.session.MediaButtonReceiver;
import android.util.Log;

public class MyMediaButtonReceiver extends MediaButtonReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            super.onReceive(context, intent);
        } catch (IllegalStateException e) {
            Log.d(this.getClass().getName(), e.getMessage());
        }
    }
}