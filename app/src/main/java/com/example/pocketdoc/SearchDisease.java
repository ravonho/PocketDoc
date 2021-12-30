package com.example.pocketdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SearchDisease extends AppCompatActivity {


    Button buttonSDisease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_disease);


        buttonSDisease = findViewById(R.id.buttonSDisease);
        buttonSDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchDisease.this, homepage.class);
                startActivity(intent);
            }


        });




    }
}