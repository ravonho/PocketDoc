package com.example.pocketdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class search_doctor2 extends AppCompatActivity {


    Button buttonbackDoctor;
    TextView doctor_name, doctor_age, doctor_race, doctor_background, doctor_special, doctor_exp;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctor2);


        doctor_name = findViewById(R.id.doctor_name);
        doctor_name.append(getIntent().getStringExtra("doctor_name"));
        doctor_age = findViewById(R.id.doctor_age);
        doctor_age.append(getIntent().getStringExtra("doctor_age"));
        doctor_race = findViewById(R.id.doctor_race);
        doctor_race.append(getIntent().getStringExtra("doctor_race"));
        doctor_background = findViewById(R.id.doctor_background);
        doctor_background.append(getIntent().getStringExtra("doctor_background"));
        doctor_special = findViewById(R.id.doctor_special);
        doctor_special.append(getIntent().getStringExtra("doctor_special"));
        doctor_exp = findViewById(R.id.doctor_exp);
        doctor_exp.append(getIntent().getStringExtra("doctor_exp"));


        buttonbackDoctor = findViewById(R.id.buttonbackDoctor);
        buttonbackDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(search_doctor2.this, SearchDoctor.class);
                startActivity(intent);
            }

        });
    }

}