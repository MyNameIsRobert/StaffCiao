package com.example.roc06.staffciao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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
        temp.setText(age);
        temp = findViewById(R.id.camperCourse_TextView);
        temp.setText(camper.courseName);
        temp = findViewById(R.id.camperLunchType_TextView);
        String tempText = camper.baggedLunch? "Bagged Lunch":"Cafeteria Lunch";
        temp.setText(tempText);
        temp = findViewById(R.id.camperShirtSize_TextView);
        temp.setText(camper.tShirtSize);

    }
}
