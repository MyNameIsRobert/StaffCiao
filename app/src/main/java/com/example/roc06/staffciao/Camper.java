package com.example.roc06.staffciao;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class Camper implements Serializable
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
    public boolean prefersNickname = false;
    public String[] guardianNames, guardianContactNumbers;
    public String safeWord;

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
        String[] courses = {"Roblox", "Robotics", "Java Programming", "Pi-Tops"};
        String[] maleNames = {"John", "Jim", "Tim", "Rob", "Bob", "Cooper", "Liam", "Noah", "William", "James", "Logan", "Ben", "Mason", "Elijah", "Lucas", "Jackson", "Sam", "David", "Carter", "Robert"};
        String[] femaleNames = {"Jane", "Audrey", "Becky", "Maddie", "Sarah", "Erin", "Emma",  "Olivia", "Sophia", "Charlotte", "Emily", "Ella", "Grace", "Riley", "Layla", "Lillian", "Zoey", "Hannah", "Eleanor"};
        String[] lastNames = {"Smith", "Adams", "Lee", "Clark", "Turner", "Arnsdorff", "Strickland", "Dixon", "Jones", "Brown", "Miller", "Williams", "Taylor", "Muhammad", "Ford", "Thomas", "Wood", "Scott", "Jackson", "John", "Lee", "May", "Marshall", "Day", "Kennedy", "Cook"};
        String[] safeWords = {"Snow", "Prick", "Guess", "North", "Gainful", "Long-Term", "Shade", "Salty", "Gorgeous", "Tie", "Route", "Wet", "Hard", "Preach", "Subdue"};


        Random rand = new Random();
        age = rand.nextInt(10);
        age += 7;
        maleOrFemale = (rand.nextBoolean())? 'M':'F';
        if(maleOrFemale == 'M')
            camperName = maleNames[rand.nextInt(maleNames.length)] + " " + lastNames[rand.nextInt(lastNames.length)];

        else
            camperName = femaleNames[rand.nextInt(femaleNames.length)] + " " + lastNames[rand.nextInt(lastNames.length)];

        baggedLunch = rand.nextBoolean();
        isOvernight = rand.nextBoolean();
        courseName = courses[rand.nextInt(courses.length)];
        guardianNames = new String[rand.nextInt(3) + 1];
        guardianContactNumbers = new String[guardianNames.length];
        for(int i = 0; i < guardianNames.length; i++) {
            guardianNames[i] = (rand.nextBoolean())? maleNames[rand.nextInt(maleNames.length)] + " " + lastNames[rand.nextInt(lastNames.length)]: femaleNames[rand.nextInt(femaleNames.length)] + " " + lastNames[rand.nextInt(lastNames.length)];
            guardianContactNumbers[i] = "803-555-5555";
        }
        safeWord = safeWords[rand.nextInt(safeWords.length)];
    }

    public boolean isGroup1()
    {
        return age < 10;
    }
    public boolean isGroup2()
    {
        return age > 9 && age < 13;
    }
    public boolean isGroup3()
    {
        return age > 12;
    }

    public String getName()
    {
        String temp = prefersNickname? camperNickName:camperName;
        return temp;
    }
}
