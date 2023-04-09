package com.example.onoffscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class SetAwakeTimer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_awake_timer);

           TimePicker awakeTimePicker = findViewById(R.id.awakeTimePicker);
           Button awakeTimeBtn = findViewById(R.id.setAwakeTime);

           awakeTimeBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Calendar awakeTime = Calendar.getInstance();

                   if(Build.VERSION.SDK_INT >= 23) {
                       awakeTime.set(
                               awakeTime.get(Calendar.YEAR),
                               awakeTime.get(Calendar.MONTH),
                               awakeTime.get(Calendar.DAY_OF_MONTH),
                               awakeTimePicker.getHour(),
                               awakeTimePicker.getMinute(),
                               0
                       );
                   }else {
                       awakeTime.set(
                               awakeTime.get(Calendar.YEAR),
                               awakeTime.get(Calendar.MONTH),
                               awakeTime.get(Calendar.DAY_OF_MONTH),
                               awakeTimePicker.getCurrentHour(),
                               awakeTimePicker.getCurrentMinute(),
                               0
                       );
                   }

                   setAwakeAlarm(awakeTime.getTimeInMillis());
               }

           });
    }

    private void setAwakeAlarm(long timeInMillis) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this, RebootReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
        }
        Toast.makeText(this,"Sleep time is set",Toast.LENGTH_SHORT).show();

    }
}