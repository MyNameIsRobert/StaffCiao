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

    public class ScheduleOptions
    {

    }

    public class MedicalInformation
    {
        public String medicationInfo;
        public Date whenToTake;
        int severityLevel;

        public MedicalInformation(String medInfo, Date when)
        {
            medicationInfo = medInfo;
            whenToTake = when;
        }

        public void setSeverityLevel(int severityLevel)
        {
            this.severityLevel = severityLevel;
        }
    }

    public class Camper
    {
        public String camperName;
        public int age;
        public String courseName;
        public String camperNickName;
        public MedicalInformation[] medicalInformations;
        public String tShirtSize;
        public boolean baggedLunch;
        public boolean isOvernight;
        public char maleOrFemale;

        public Camper()
        {
            camperName = "Johnny Doey";
            age = 10;
            courseName = "Roblox";
            camperNickName = "John";
            medicalInformations = null;
            tShirtSize = "Adult Small";
            baggedLunch = false;
            isOvernight = true;
            maleOrFemale = 'M';
        }

        public Camper(String name, int a, String cName, String nickName, MedicalInformation[] medInfo, String tShirt, boolean bag, boolean over, char mOrF)
        {
            camperNickName = name;
            age = a;
            courseName = cName;
            camperNickName = nickName;
            medicalInformations = medInfo;
            tShirtSize = tShirt;
            baggedLunch = bag;
            isOvernight = over;
            if(mOrF != 'F' && mOrF != 'f' && mOrF != 'M' && mOrF != 'm')
            {
                mOrF = 'M';
            }
            maleOrFemale = mOrF;
        }

        public void RandomizeCamper()
        {
            Random rand = new Random();
            age = rand.nextInt(10);
            age += 7;
            maleOrFemale = (rand.nextInt(1) == 1)? 'M':'F';
            if(maleOrFemale == 'M')
                camperName = "Johnny Doey";
            else
                camperName = "Janey Doooy";

            baggedLunch = rand.nextBoolean();
            isOvernight = rand.nextBoolean();

        }

    }

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
    Camper[] campers;
    ArrayList<Camper> group1Campers = new ArrayList<>(), group2Campers = new ArrayList<>(), group3Campers = new ArrayList<>();

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
            tempCal.set(0, 0, 0, 10, 30, 0);
            ScheduleEvents temp = new ScheduleEvents(tempCal.getTime(), "Lunch", 15, "Cafeteria");
            tempCal.set(0,0,0,11,30,0);
            scheduleEvents.add(temp);
            temp = new ScheduleEvents(tempCal.getTime(), "Instructional Time", 10, "HVC 202");
            scheduleEvents.add((temp));
            SortSchedule();
        }
        RunScheduler();

        Random rand = new Random();
        int numOfCampers = rand.nextInt(30) + 50;
        campers = new Camper[numOfCampers];
        for(int i = 0; i < numOfCampers; i++)
        {
            campers[i] = new Camper();
            campers[i].RandomizeCamper();
            if(campers[i].age < 10)
                group1Campers.add(campers[i]);
            else if(campers[i].age < 13)
                group2Campers.add(campers[i]);
            else
                group3Campers.add(campers[i]);
        }


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
                System.out.println("The current time is after " + scheduleEvents.get(i).eventName + " at " + scheduleEvents.get(i).eventTime);
                //If the event at index: i is the not the last event on the schedule
                if(i != scheduleEvents.size()-1)
                {
                    //If the current time is before the next event's time on the Schedule
                   if(currentTime.before(scheduleEvents.get(i + 1).eventTime))
                   {
                       tempIndex = i;
                       break;
                   }
                   else
                   {
                       System.out.println(currentTime + " is after " + scheduleEvents.get(i + 1).eventName + " at " + scheduleEvents.get(i + 1).eventTime);

                   }
                }
                else
                {
                    tempIndex = i;
                }
            }
            else {
                System.out.println(currentTime + " is before " + scheduleEvents.get(i).eventName + " at " + scheduleEvents.get(i).eventTime);
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
            cal.setTimeZone(TimeZone.getDefault());
            System.out.println("Hour before conversion: " + scheduleEvents.get(i).eventTime.getHours());
            System.out.println("Calendar hour before conversion: " + cal.getTime().getHours());
            cal.set(Calendar.HOUR, scheduleEvents.get(i).eventTime.getHours());
            System.out.println("Hour after conversion: " + cal.getTime().getHours());
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
        transitionReturn.setTime(transitionReturn.getTime() - (scheduleEvents.get(index).timeForReminder * 60 * 1000 /*Converts the time to milliseconds*/));
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
        System.out.println(scheduleEvents.get(0).eventName + " is at " + scheduleEvents.get(0).eventTime.toString());
        System.out.println("The current date and time is " + temp.getTime());
        nextIndex = (currentIndex != scheduleEvents.size() - 1)? currentIndex + 1 : 0;
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
                    current.setText("Current Activity; " + scheduleEvents.get(currentIndex).eventName);
                TextView next = findViewById(R.id.nextActivity);
                SimpleDateFormat transitionFormatted = new SimpleDateFormat("hh:mm a");
                String transitionString = "";
                transitionString = transitionFormatted.format(transition);
                next.setText("Next Activity: " + scheduleEvents.get(nextIndex).eventName + "(" + transitionString + ")");
                TextView debugView = findViewById(R.id.debugView);
                debugView.setText("Current time is " + ((transition.getTime() - thisSecond.getTime().getTime())) + " seconds away");
                if(thisSecond.after(transition))
                {
                    NotifyOfTransition();
                    currentIndex = (currentIndex + 1 == scheduleEvents.size())? 0:currentIndex +1;
                    nextIndex = (currentIndex + 1 == scheduleEvents.size()) ? 0: currentIndex +1;
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


}
