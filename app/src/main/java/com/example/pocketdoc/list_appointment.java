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

public class list_appointment extends AppCompatActivity {


    Button Back;
    TextView appointment_date, appointment_time, appointment_hospital, appointment_disease;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_appointment);


        appointment_date = findViewById(R.id.appointment_date);
        appointment_date.append(getIntent().getStringExtra("appointment_date"));
        appointment_time = findViewById(R.id.appointment_time);
        appointment_time.append(getIntent().getStringExtra("appointment_time"));
        appointment_hospital = findViewById(R.id.appointment_hospital);
        appointment_hospital.append(getIntent().getStringExtra("appointment_hospital"));
        appointment_disease = findViewById(R.id.appointment_disease);
        appointment_disease.append(getIntent().getStringExtra("appointment_disease"));
        Back = findViewById(R.id.Back);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(list_appointment.this, Myappointment.class);
                startActivity(intent);
            }

        });
    }

}