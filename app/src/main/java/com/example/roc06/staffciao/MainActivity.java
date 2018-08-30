package com.example.roc06.staffciao;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Time;
import java.time.Instant;
import java.util.*;
import java.util.Collections;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    class ScheduleEvents{
        public Date eventTime;
        public String eventName;
        public int timeForReminder;
        public String eventLocation;

        ScheduleEvents(Date time, String name, int reminder, String location)
        {
            eventTime = time;
            eventName = name;
            timeForReminder = reminder;
            eventLocation = location;
        }
    }
    int currentIndex = 0;
    ArrayList<ScheduleEvents> scheduleEvents = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView greeting = findViewById(R.id.greeting);
        String greetingName = getString(R.string.employee_name);
        greetingName += " (";
        greetingName += getString(R.string.employee_title);
        greetingName += ")";
        greeting.setText(greetingName);

        if(scheduleEvents.isEmpty())
        {
            Calendar tempCal = Calendar.getInstance();
            tempCal.set(0, 0, 0, 13, 30, 0);
            ScheduleEvents temp = new ScheduleEvents(tempCal.getTime(), "Lunch", 15, "Cafeteria");
            tempCal.set(0,0,0,12,30,0);
            scheduleEvents.add(temp);
            temp = new ScheduleEvents(tempCal.getTime(), "Instructional Time", 10, "HVC 202");
            scheduleEvents.add((temp));
            SortSchedule();
        }
        RunScheduler();
    }

    void SortSchedule()
    {        Collections.sort(scheduleEvents, new Comparator<ScheduleEvents>() {
            public int compare(ScheduleEvents c1, ScheduleEvents c2) {
                if (c1.eventTime.after(c2.eventTime)) return 1;
                if (c1.eventTime.before(c2.eventTime)) return -1;
                return 0;
            }});

            for(int i = 0; i < scheduleEvents.size(); i++)
            {
                System.out.println(scheduleEvents.get(i).eventName);
            }
    }

    void RunScheduler()
    {
        final Handler handler = new Handler();
        final int delay = 1000;

        //Setting every scheduled Event day to be the current day
        for(int i = 0; i < scheduleEvents.size(); i++)
        {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR, scheduleEvents.get(i).eventTime.getHours());
            cal.set(Calendar.MINUTE, scheduleEvents.get(i).eventTime.getMinutes());
            cal.set(Calendar.SECOND, scheduleEvents.get(i).eventTime.getSeconds());
            scheduleEvents.get(i).eventTime = cal.getTime();
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();

                //Schedule Code
                //Creating a time that is the set number of minutes before the next activity, called transitionTime
                int nextIndex;
                Date transitionTime = new Date();
                if(scheduleEvents.size() > 0 && currentIndex == scheduleEvents.size() - 1)
                {
                    transitionTime = scheduleEvents.get(0).eventTime;
                    transitionTime.setHours(transitionTime.getHours() - scheduleEvents.get(0).timeForReminder);
                    nextIndex = 0;
                }
                else
                {
                    transitionTime = scheduleEvents.get(currentIndex + 1).eventTime;
                    transitionTime.setHours(transitionTime.getHours() - scheduleEvents.get(currentIndex + 1).timeForReminder);
                    nextIndex = currentIndex + 1;
                }
                System.out.println();
                //If the current time is after the next events time
                if(calendar.getTime().after(scheduleEvents.get(currentIndex).eventTime))
                {
                    //If the current time is still before the transition period
                    if(calendar.getTime().before((transitionTime)))
                    {
                        TextView tempText = findViewById(R.id.currentActivity);
                        tempText.setText("Current activity: " + scheduleEvents.get(currentIndex).eventName);
                    }
                    //If we are in the transition period
                    else if(calendar.getTime().before(scheduleEvents.get(nextIndex).eventTime))
                    {
                        TextView tempText = findViewById(R.id.currentActivity);
                        tempText.setText("Transitioning to:  " + scheduleEvents.get(nextIndex).eventName);
                    }
                    //We move on to the next event
                    else
                    {
                        currentIndex = nextIndex;
                    }
                }
                TextView tempText = findViewById(R.id.nextActivity);
                tempText.setText("Next activity: " + scheduleEvents.get(nextIndex).eventName);

                handler.postDelayed(this, delay);
            }
        }, delay);
    }


}
