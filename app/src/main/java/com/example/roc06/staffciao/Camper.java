package com.example.roc06.staffciao;

import java.util.Date;
import java.util.Random;

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
    public boolean prefersNickname = false;


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
        String[] maleNames = {"John", "Jim", "Tim", "Rob", "Bob", "Cooper"};
        String[] femaleNames = {"Jane", "Audrey", "Becky", "Maddie", "Sarah", "Erin"};

        Random rand = new Random();
        age = rand.nextInt(10);
        age += 7;
        maleOrFemale = (rand.nextInt(1) == 1)? 'M':'F';
        if(maleOrFemale == 'M')
            camperName = maleNames[rand.nextInt(maleNames.length)];
        else
            camperName = femaleNames[rand.nextInt(femaleNames.length)];

        baggedLunch = rand.nextBoolean();
        isOvernight = rand.nextBoolean();
        courseName = courses[rand.nextInt(courses.length)];

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
