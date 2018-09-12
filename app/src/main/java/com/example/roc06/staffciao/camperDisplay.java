package com.example.roc06.staffciao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class camperDisplay extends AppCompatActivity {

    Camper camper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camper_display);

        Intent intent = getIntent();
        camper = (Camper) intent.getSerializableExtra("Camper");
        TextView temp = findViewById(R.id.camperName_TextView);
        temp.setText(camper.getName());
        temp = findViewById(R.id.camperAge_TextView);
        String age = String.valueOf(camper.age);
        temp.setText(age + " Years Old");
        temp = findViewById(R.id.camperCourse_TextView);
        temp.setText(camper.courseName);
        temp = findViewById(R.id.camperLunchType_TextView);
        String tempText = camper.baggedLunch? "Bagged Lunch":"Cafeteria Lunch";
        temp.setText(tempText);
        temp = findViewById(R.id.camperShirtSize_TextView);
        temp.setText(camper.tShirtSize);

        LinearLayout guardianLayout = findViewById(R.id.guardianLinearLayout);
        for(int i = 0; i < camper.guardianNames.length; i++) {
            LinearLayout tempLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.guardian_contact_info_layout_view, null) ;
            guardianLayout.addView(tempLayout);
            TextView tempView = (TextView) tempLayout.getChildAt(0);
            tempView.setText(camper.guardianNames[i] + ":");
            Log.d(camper.guardianNames[i], "");
            tempView = (TextView) tempLayout.getChildAt(2);
            tempView.setText(camper.guardianContactNumbers[i]);
        }

    }
}
