package com.example.pocketdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class search_disease2 extends AppCompatActivity {

    Button buttonbackDisease;
    TextView disease_name, disease_description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_disease2);


        disease_name = findViewById(R.id.disease_name);
        disease_name.append(getIntent().getStringExtra("disease_name"));
        disease_description = findViewById(R.id.disease_description);
        disease_description.append(getIntent().getStringExtra("disease_description"));


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