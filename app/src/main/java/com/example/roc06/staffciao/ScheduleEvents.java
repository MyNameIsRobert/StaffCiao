package com.example.roc06.staffciao;

import java.util.Date;

public class ScheduleEvents{
    public Date eventTime;
    public String eventName;
    public int timeForReminder;
    public String eventLocation;
    public String eventDetails;



    ScheduleEvents(Date time, String name, int reminder, String location)
    {
        eventTime = time;
        eventName = name;
        timeForReminder = reminder;
        eventLocation = location;
        eventDetails = "";
    }


}
