package com.example.onoffscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class SetSleepTimer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_sleep_timer);

        // Get the selected sleep and awake times from the time pickers
        TimePicker sleepTimePicker = findViewById(R.id.sleepTimePicker);
        Button sleepTimeBtn = findViewById(R.id.setSleepTime);

        sleepTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar sleepTime = Calendar.getInstance();

                if(Build.VERSION.SDK_INT >= 23) {
                    sleepTime.set(
                            sleepTime.get(Calendar.YEAR),
                            sleepTime.get(Calendar.MONTH),
                            sleepTime.get(Calendar.DAY_OF_MONTH),
                            sleepTimePicker.getHour(),
                            sleepTimePicker.getMinute(),
                            0
                    );
                }else {
                    sleepTime.set(
                            sleepTime.get(Calendar.YEAR),
                            sleepTime.get(Calendar.MONTH),
                            sleepTime.get(Calendar.DAY_OF_MONTH),
                            sleepTimePicker.getCurrentHour(),
                            sleepTimePicker.getCurrentMinute(),
                            0
                    );
                }

                setSleepAlarm(sleepTime.getTimeInMillis());
            }
        });
    }

    private void setSleepAlarm(long sleepTimeInMillis) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent sleepIntent = new Intent(this, SleepReceiver.class);
        PendingIntent sleepPendingIntent = PendingIntent.getBroadcast(this, 0, sleepIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, sleepTimeInMillis, sleepPendingIntent);
        }

        Toast.makeText(this,"Sleep time is set",Toast.LENGTH_SHORT).show();

    }
}