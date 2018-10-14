package com.example.roc06.staffciao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.Clock;

public class camperSearch extends AppCompatActivity {


    EditText yourEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camper_search);

        yourEditText = (EditText) findViewById(R.id.searchBox_Search);

        yourEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                // you can call or do what you want with your EditText here
                if(s.toString().trim().length() > 0) {
                    Camper[] searchResults = InternalData.SearchCampers(s.toString());
                    UpdateSearchView(searchResults);
                }
                else
                {
                    ClearSearchView();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }


    void ClearSearchView()
    {
        final LinearLayout layout = (LinearLayout) findViewById(R.id.searchResultsDisplay_Layout);
        layout.removeAllViews();
    }

    void UpdateSearchView(final Camper[] results)
    {
        final LinearLayout layout = (LinearLayout) findViewById(R.id.searchResultsDisplay_Layout);
        ClearSearchView();
        System.out.println("Reset Views");
        for (Camper c: results)
        {
            final LinearLayout camperLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.camperdisplay_name, null);
            final TextView camperName = (TextView) camperLayout.getChildAt(0);
            final LinearLayout imageFilter_Layout = (LinearLayout)  camperLayout.getChildAt(1);
            char[] resultFilters = c._searchResults_TermHit.toCharArray();

            camperName.setText(c.getName());
            TextView tempView = (TextView) imageFilter_Layout.getChildAt(0);
            tempView.setText(c._searchResults_TermHit);
            layout.addView(camperLayout);
            camperName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int camperIndex = layout.indexOfChild(v);
                    Intent intent = new Intent(camperSearch.this, camperDisplay.class);
                    intent.putExtra("Camper", results[camperIndex]);
                    startActivity(intent);
                }
            });
        }
    }




}
