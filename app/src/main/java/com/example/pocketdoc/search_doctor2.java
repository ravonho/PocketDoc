package com.example.pocketdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class search_doctor2 extends AppCompatActivity {

    TextView doctor_name, doctor_age, doctor_race, doctor_background, doctor_special, doctor_exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctor2);
        doctor_name = findViewById(R.id.doctor_name);
        doctor_name.append(getIntent().getStringExtra("doctor_name"));
    }
}