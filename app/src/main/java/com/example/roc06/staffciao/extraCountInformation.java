package com.example.roc06.staffciao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

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

    Camper[] allCampers;

    RosterType rosterTyp = RosterType.Group1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_count_information);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        allCampers = (Camper[])args.getSerializable("ARRAY");
        rosterTyp = RosterType.values()[intent.getIntExtra("RosterType", 0)];
        SetCounts(rosterTyp, allCampers);
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
            layout.addView(tempLayout);
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
