package com.example.roc06.staffciao;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class InternalData {

    public static ArrayList<ScheduleEvents> scheduleEvents;

    public static ArrayList<Camper> campers;

    final String campersFileName = "CamperList";
    final String eventsFileName = "ScheduleEvents";

    public static void fillEvents()
    {
        if(scheduleEvents == null)
        {
            scheduleEvents = new ArrayList<>();
        }
        if(scheduleEvents.isEmpty())
        {
            Calendar tempCal = Calendar.getInstance();
            tempCal.set(0, 0, 0, 10, 30, 0);
            ScheduleEvents temp = new ScheduleEvents(tempCal.getTime(), "Lunch", 15, "Cafeteria");
            tempCal.set(0,0,0,11,30,0);
            scheduleEvents.add(temp);
            temp = new ScheduleEvents(tempCal.getTime(), "Instructional Time", 10, "HVC 202");
            scheduleEvents.add((temp));
        }
    }


    public static void fillCampers()
    {
        Random rand = new Random();
        int numOfCampers = rand.nextInt(30) + 50;
        campers = new ArrayList<>(numOfCampers);
        for(int i = 0; i < numOfCampers; i++)
        {
            campers.add(new Camper());
            campers.get(i).RandomizeCamper();
        }
    }

    public static void StoreAllData()
    {
        ObjectOutput out;
        try{
            File outFile = new File(Environment.getExternalStorageDirectory(), "appSaveState.data");
            out = new ObjectOutputStream(new FileOutputStream(outFile));
            out.writeObject(campers);
            out.writeObject(scheduleEvents);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void ReadAllData()
    {
        ObjectInput in;
        try
        {
            in = new ObjectInputStream(new FileInputStream("appSaveState.data"));
            campers = (ArrayList<Camper>) in.readObject();
            scheduleEvents = (ArrayList<ScheduleEvents>) in.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
