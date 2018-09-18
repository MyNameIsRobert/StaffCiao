package com.example.roc06.staffciao;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
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


    int currentIndex = 0;
    int nextIndex = 0;
    Date transition;
    ArrayList<Camper> group1Campers = new ArrayList<>(), group2Campers = new ArrayList<>(), group3Campers = new ArrayList<>();

    String staffRole = "Instructor";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        TextView greeting = findViewById(R.id.greeting);
        String greetingName = getString(R.string.employee_name);
        greetingName += " (";
        greetingName += staffRole;
        greetingName += ")";
        greeting.setText(greetingName);

        InternalData.fillCampers();
        InternalData.fillEvents();
        for(int i = 0; i < InternalData.campers.size(); i++)
        {
            if(InternalData.campers.get(i).age < 10)
                group1Campers.add(InternalData.campers.get(i));
            else if(InternalData.campers.get(i).age < 13)
                group2Campers.add(InternalData.campers.get(i));
            else
                group3Campers.add(InternalData.campers.get(i));
        }

        SortSchedule();
        RunScheduler();


    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        InternalData.StoreAllData();
    }

    void SortSchedule()
    {        Collections.sort(InternalData.scheduleEvents, new Comparator<ScheduleEvents>() {
            public int compare(ScheduleEvents c1, ScheduleEvents c2) {
                if (c1.eventTime.after(c2.eventTime)) return 1;
                if (c1.eventTime.before(c2.eventTime)) return -1;
                return 0;
            }});

            for(int i = 0; i < InternalData.scheduleEvents.size(); i++)
            {
                System.out.println(InternalData.scheduleEvents.get(i).eventName);
            }
    }

    int FindPlaceInSchedule()
    {
        int tempIndex = -1;
        Date currentTime = Calendar.getInstance().getTime();
        for(int i = 0; i < InternalData.scheduleEvents.size(); i++)
        {
            //If the current time is after the time on the Schedule
            if(currentTime.after(InternalData.scheduleEvents.get(i).eventTime))
            {
                System.out.println("The current time is after " + InternalData.scheduleEvents.get(i).eventName + " at " + InternalData.scheduleEvents.get(i).eventTime);
                //If the event at index: i is the not the last event on the schedule
                if(i != InternalData.scheduleEvents.size()-1)
                {
                    //If the current time is before the next event's time on the Schedule
                   if(currentTime.before(InternalData.scheduleEvents.get(i + 1).eventTime))
                   {
                       tempIndex = i;
                       break;
                   }
                   else
                   {
                       System.out.println(currentTime + " is after " + InternalData.scheduleEvents.get(i + 1).eventName + " at " + InternalData.scheduleEvents.get(i + 1).eventTime);

                   }
                }
                else
                {
                    tempIndex = i;
                }
            }
            else {
                System.out.println(currentTime + " is before " + InternalData.scheduleEvents.get(i).eventName + " at " + InternalData.scheduleEvents.get(i).eventTime);
            }
        }
        return tempIndex;

    }
    void ConvertScheduleToToday()
    {
        //Setting every scheduled Event day to be the current day
        for(int i = 0; i < InternalData.scheduleEvents.size(); i++)
        {
            Calendar cal = Calendar.getInstance();
            cal.setTimeZone(TimeZone.getDefault());
            System.out.println("Hour before conversion: " + InternalData.scheduleEvents.get(i).eventTime.getHours());
            System.out.println("Calendar hour before conversion: " + cal.getTime().getHours());
            cal.set(Calendar.HOUR, InternalData.scheduleEvents.get(i).eventTime.getHours());
            System.out.println("Hour after conversion: " + cal.getTime().getHours());
            cal.set(Calendar.MINUTE, InternalData.scheduleEvents.get(i).eventTime.getMinutes());
            cal.set(Calendar.SECOND, InternalData.scheduleEvents.get(i).eventTime.getSeconds());
            InternalData.scheduleEvents.get(i).eventTime = cal.getTime();
        }
    }
    // TODO: Display a pop up that it's time to start transitioning to the next activity
    void NotifyOfTransition()
    {

    }
    Date GetTimeForNextTransition(int index)
    {
        Date transitionReturn;
        transitionReturn = InternalData.scheduleEvents.get(index).eventTime;
        transitionReturn.setTime(transitionReturn.getTime() - (InternalData.scheduleEvents.get(index).timeForReminder * 60 * 1000 /*Converts the time to milliseconds*/));
        System.out.println("Transition time is " + transitionReturn);
        return transitionReturn;
    }
    void RunScheduler()
    {
        final Handler handler = new Handler();
        final int delay = 1000; //1 Second
        ConvertScheduleToToday();
        currentIndex = FindPlaceInSchedule();
        System.out.println(currentIndex);
        final Calendar temp = Calendar.getInstance();
        System.out.println(InternalData.scheduleEvents.get(0).eventName + " is at " + InternalData.scheduleEvents.get(0).eventTime.toString());
        System.out.println("The current date and time is " + temp.getTime());
        nextIndex = (currentIndex != InternalData.scheduleEvents.size() - 1)? currentIndex + 1 : 0;
        transition = GetTimeForNextTransition(nextIndex);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                //Schedule Code
                Calendar thisSecond = Calendar.getInstance();
                TextView current = findViewById(R.id.currentActivity);
                if(currentIndex == -1)
                    current.setText("No Current Activity");
                else
                    current.setText("Current Activity; " + InternalData.scheduleEvents.get(currentIndex).eventName);
                TextView next = findViewById(R.id.nextActivity);
                SimpleDateFormat transitionFormatted = new SimpleDateFormat("hh:mm a");
                String transitionString = "";
                transitionString = transitionFormatted.format(transition);
                next.setText("Next Activity: " + InternalData.scheduleEvents.get(nextIndex).eventName + "(" + transitionString + ")");
                TextView debugView = findViewById(R.id.debugView);
                debugView.setText("Current time is " + ((transition.getTime() - thisSecond.getTime().getTime())) + " seconds away");
                if(thisSecond.after(transition))
                {
                    NotifyOfTransition();
                    currentIndex = (currentIndex + 1 == InternalData.scheduleEvents.size())? 0:currentIndex +1;
                    nextIndex = (currentIndex + 1 == InternalData.scheduleEvents.size()) ? 0: currentIndex +1;
                    transition = GetTimeForNextTransition(nextIndex);
                }


                //Count Code
                TextView tempView = findViewById(R.id.group1TextView);
                tempView.setText("Group 1s: " + group1Campers.size());
                tempView = findViewById(R.id.group2TextView);
                tempView.setText("Group 2s: " + group2Campers.size());
                tempView = findViewById(R.id.group3TextView);
                tempView.setText("Group 3s: " + group3Campers.size());
                handler.postDelayed(this, delay);
            }
        }, delay);
    }


    public void viewGroup1s(View view)
    {
        Intent intent = new Intent(this, extraCountInformation.class);
        Bundle args = new Bundle();
        args.putSerializable("ARRAY", InternalData.campers);
        intent.putExtra("BUNDLE", args);
        intent.putExtra("RosterType", extraCountInformation.RosterType.Group1);
        startActivity(intent);

    }
    public void viewGroup2s(View view)
    {
        Intent intent = new Intent(this, extraCountInformation.class);
        Bundle args = new Bundle();
        args.putSerializable("ARRAY", InternalData.campers);
        intent.putExtra("BUNDLE", args);
        intent.putExtra("RosterType", extraCountInformation.RosterType.Group2);
        startActivity(intent);
    }


    public void viewSchedule(View view) {
        Intent intent = new Intent(this, ScheduleDisplay.class);
        startActivity(intent);
    }
}
