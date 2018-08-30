package com.example.roc06.staffciao;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
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
    int nextIndex = 0;
    Date transition;
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
    int FindPlaceInSchedule()
    {
        int tempIndex = -1;
        Date currentTime = Calendar.getInstance().getTime();
        for(int i = 0; i < scheduleEvents.size(); i++)
        {
            //If the current time is after the time on the Schedule
            if(currentTime.after(scheduleEvents.get(i).eventTime))
            {
                //If the event at index: i is the not the last event on the schedule
                if(i != scheduleEvents.size()-1)
                {
                    //If the current time is before the next event's time on the Schedule
                   if(currentTime.before(scheduleEvents.get(i + 1).eventTime))
                   {
                       tempIndex = i;
                       break;
                   }
                }
            }

        }
        return tempIndex;

    }
    void ConvertScheduleToToday()
    {
        //Setting every scheduled Event day to be the current day
        for(int i = 0; i < scheduleEvents.size(); i++)
        {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR, scheduleEvents.get(i).eventTime.getHours());
            cal.set(Calendar.MINUTE, scheduleEvents.get(i).eventTime.getMinutes());
            cal.set(Calendar.SECOND, scheduleEvents.get(i).eventTime.getSeconds());
            scheduleEvents.get(i).eventTime = cal.getTime();
        }
    }
    // TODO: Display a pop up that it's time to start transitioning to the next activity
    void NotifyOfTransition()
    {

    }
    Date GetTimeForNextTransition(int index)
    {
        Date transitionReturn;
        transitionReturn = scheduleEvents.get(index).eventTime;
        transitionReturn.setTime(transitionReturn.getTime() - (scheduleEvents.get(index).timeForReminder  * 1000 /*Converts the time to milliseconds*/));
        return transitionReturn;
    }
    void RunScheduler()
    {
        final Handler handler = new Handler();
        final int delay = 1000; //1 Second
        currentIndex = FindPlaceInSchedule();
        ConvertScheduleToToday();
        nextIndex = (currentIndex != scheduleEvents.size() - 1)? currentIndex + 1 : 0;
        transition = GetTimeForNextTransition(nextIndex);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Calendar thisSecond = Calendar.getInstance();

                //Schedule Code
                TextView current = findViewById(R.id.currentActivity);
                current.setText("Current Activity; " + scheduleEvents.get(currentIndex).eventName);
                TextView next = findViewById(R.id.nextActivity);
                SimpleDateFormat transitionFormatted = new SimpleDateFormat("hh:mm a");
                String transitionString = "";
                transitionString = transitionFormatted.format(transition);
                next.setText("Next Activity: " + scheduleEvents.get(nextIndex).eventName + "(" + transitionString + ")");

                if(thisSecond.after(transition))
                {
                    NotifyOfTransition();
                    currentIndex = (currentIndex + 1 == scheduleEvents.size())? 0:currentIndex +1;
                    nextIndex = (currentIndex + 1 == scheduleEvents.size()) ? 0: currentIndex +1;
                    transition = GetTimeForNextTransition(nextIndex);
                }
                handler.postDelayed(this, delay);
            }
        }, delay);
    }


}
