package com.example.roc06.staffciao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ScheduleDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_display);

        LinearLayout linearLayout = findViewById(R.id.schedule_scroll_linearLayout);

        for (ScheduleEvents event:InternalData.scheduleEvents) {
            LinearLayout temp = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.schedule_view_layout, null);
            linearLayout.addView(temp);
            TextView tempView;
            tempView = (TextView) temp.getChildAt(0);
            tempView.setText(event.eventName);
            temp = (LinearLayout) temp.getChildAt(1);
            tempView = (TextView) temp.getChildAt(0);
            DateFormat df = new SimpleDateFormat("HH:mm a");
            String date = df.format(event.eventTime);
            tempView.setText(date);
            tempView = (TextView) temp.getChildAt(1);
            tempView.setText(event.eventLocation);
            tempView = (TextView) temp.getChildAt(2);
            String timeForReminder = String.valueOf(event.timeForReminder);
            tempView.setText(timeForReminder);
        }
    }
}
