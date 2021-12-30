package com.example.pocketdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class search_disease2 extends AppCompatActivity {

    Button buttonbackDisease;
    TextView disease_name, disease_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_disease2);

        disease_name = findViewById(R.id.disease_name);
        disease_name.append(getIntent().getStringExtra("disease_name"));
        disease_desc = findViewById(R.id.disease_desc);
        disease_desc.append(getIntent().getStringExtra("disease_desc"));


        buttonbackDisease = findViewById(R.id.buttonbackDisease);
        buttonbackDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(search_disease2.this, SearchDisease.class);
                startActivity(intent);
            }

        });
    }
}