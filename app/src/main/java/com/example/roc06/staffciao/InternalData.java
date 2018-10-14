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
            tempCal.set(0,0,0,13, 15, 0);
            temp = new ScheduleEvents(tempCal.getTime(), "Activity Time", 15, "Courtyard");
            scheduleEvents.add(temp);
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

    public static Camper[] SearchCampers(String keyWord)
    {
        ArrayList<Camper> tempList = new ArrayList<>();
        keyWord = keyWord.toLowerCase();
        //Loops through all campers
        for(int i = 0; i < campers.size(); i++)
        {
            Camper temp = campers.get(i);
            temp._searchResults_TermHit = "";
            //Name - Calls the Camper function getName() to include nicknames in search
            if(temp.getName().toLowerCase().contains(keyWord))
            {
                temp._searchResults_TermHit += "n";
                tempList.add(temp);
            }
            // Course Name
            if(temp.courseName.toLowerCase().contains(keyWord))
            {
                temp._searchResults_TermHit += "c";
                tempList.add(temp);
            }
            // Age- checks to see if the keyword contains numbers, if it does, it extracts only the numbers in the string,
            // then checks to see if they are the same as the campers age
            if(keyWord.matches(".*\\d+.*") && temp.age == Integer.parseInt(keyWord.replaceAll("[^0-9]", "")))
            {
                temp._searchResults_TermHit += "a";
                tempList.add(temp);
            }
            // T-Shirt size -
            if(temp.tShirtSize.toLowerCase().contains(keyWord))
            {
                temp._searchResults_TermHit += "t";
                tempList.add(temp);
            }
        }

        Camper[] returnList = tempList.toArray(new Camper[tempList.size()]);

        returnList = Camper.sort(returnList);

        return  returnList;
    }
    /// <summary>
    /// Searches for campers that fit the keyword within the given parameters in advanced tools.
    /// Usable parameters are: n(N) for name, i(I) for instructor, c(C) for class/course, a(A) for
    /// age, t(T) for t-shirt size, g(G) or p(P) for guardian names. Note that this function takes
    /// a string for the second parameter, so you can combine letters, and they will be searched in
    /// order that they were given. I.e. "inC" would first return all the campers that have an instructor
    /// that matches the keyword, then all campers that have that name, then all campers with a class
    /// that matches the keyword, making sure not to duplicate.
    /// </summary>
    public static Camper[] SearchCampers(String keyWord, String advancedTools)
    {

        return null;
    }



}
