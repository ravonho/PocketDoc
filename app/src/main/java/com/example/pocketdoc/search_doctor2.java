package com.example.pocketdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

public class search_doctor2 extends AppCompatActivity {




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

    }
}