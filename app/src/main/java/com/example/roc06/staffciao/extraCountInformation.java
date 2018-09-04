package com.example.roc06.staffciao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class extraCountInformation extends AppCompatActivity {

    public enum RosterType{
        Group1,
        Group2,
        Group3,
        GroupA,
        GroupB,
        Medications,
        Course,

    }

    ArrayList<Camper> campersToDisplay = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_count_information);
    }

    public void SetCounts(RosterType type, Camper[] campers)
    {
        FilterCampers(type, campers);
        LinearLayout layout = findViewById(R.id.roster_Layout);
        LinearLayout horizLayout = findViewById(R.id.roster_Layout_Horizontal);

        for(int i = 0; i < campersToDisplay.size(); i++) {
            LinearLayout tempLayout = horizLayout;
            TextView camperName = (TextView) tempLayout.getChildAt(0);
            camperName.setText(campersToDisplay.get(i).getName());
            TextView age = (TextView) tempLayout.getChildAt(1);
            age.setText(campersToDisplay.get(i).age);
            CheckBox medsBox = (CheckBox) tempLayout.getChildAt(2);
            if(campersToDisplay.get(i).medicalInformations != null)
                medsBox.setChecked(true);
            else
                medsBox.setChecked(false);
        }
    }
    public void SetCounts(RosterType type, Camper[] campers,  String additionalInfo)
    {

    }

    void FilterCampers(RosterType type, Camper[] campers)
    {

        switch (type)
        {
            case Group1:
                for(int i = 0; i < campers.length; i++)
                {
                    if(campers[i].isGroup1())
                    {
                        campersToDisplay.add(campers[i]);
                    }
                }
                break;
            case Group2:
                for(int i = 0; i < campers.length; i++)
                {
                    if(campers[i].isGroup2())
                    {
                        campersToDisplay.add(campers[i]);
                    }
                }
                break;
            case Group3:
                for(int i = 0; i < campers.length; i++)
                {
                    if(campers[i].isGroup3())
                    {
                        campersToDisplay.add(campers[i]);
                    }
                }
                break;
            case GroupA:
                break;
            case GroupB:
                break;
            case Medications:
                for(int i = 0; i < campers.length; i++)
                {
                    if(campers[i].medicalInformations != null)
                    {
                        campersToDisplay.add(campers[i]);
                    }
                }
                break;
        }
    }

}
