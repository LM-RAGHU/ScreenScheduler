package com.example.onoffscheduler;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.PowerManager;

public class SleepReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // Set the phone to silent mode and turn off the screen
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "SleepLock");
        wakeLock.acquire();

        Intent sleepIntent = new Intent(context, SleepActivity.class);
        sleepIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(sleepIntent);

    }
}
