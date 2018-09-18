package com.example.roc06.staffciao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TableRow;
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


    RosterType rosterTyp = RosterType.Group1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_count_information);
        Intent intent = getIntent();
        rosterTyp = (RosterType) intent.getSerializableExtra("RosterType");
        SetCounts(rosterTyp, (Camper[]) InternalData.campers.toArray());
    }

    public void SetCounts(RosterType type, Camper[] campers)
    {
        FilterCampers(type, campers);
        LinearLayout layout = findViewById(R.id.roster_Layout);

        for(int i = 0; i < campersToDisplay.size(); i++) {
            LinearLayout tempLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.camperdisplay_layoutview, null) ;
            TextView camperName = (TextView) tempLayout.getChildAt(0);
            TextView camperAge = (TextView) tempLayout.getChildAt(1);
            camperName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LinearLayout layout = (LinearLayout) view.getParent();
                    System.out.println(view.toString());
                    int camperIndex = ((LinearLayout) layout.getParent()).indexOfChild(layout) / 2;
                    Log.d("CamperIndex", "Cameper index is " + camperIndex);
                    Intent intent = new Intent(extraCountInformation.this, camperDisplay.class);
                    intent.putExtra("Camper", campersToDisplay.get(camperIndex));
                    startActivity(intent);
                }
            }
            );
            String age = String.valueOf(campersToDisplay.get(i).age);
            camperAge.setText(age);
            camperName.setText(campersToDisplay.get(i).getName());
            if(tempLayout.getParent() != null)
                ((ViewGroup)tempLayout.getParent()).removeView(tempLayout);
            layout.addView(tempLayout);
            layout.addView(LayoutInflater.from(this).inflate(R.layout.blank_space, null));
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
                TextView view = findViewById(R.id.title_TextView);
                view.setText("Group 1 Roster");
                break;
            case Group2:
                for(int i = 0; i < campers.length; i++)
                {
                    if(campers[i].isGroup2())
                    {
                        campersToDisplay.add(campers[i]);
                    }
                }
                view = findViewById(R.id.title_TextView);
                view.setText("Group 2 Roster");
                break;
            case Group3:
                for(int i = 0; i < campers.length; i++)
                {
                    if(campers[i].isGroup3())
                    {
                        campersToDisplay.add(campers[i]);
                    }
                }
                view = findViewById(R.id.title_TextView);
                view.setText("Group 3 Roster");
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
