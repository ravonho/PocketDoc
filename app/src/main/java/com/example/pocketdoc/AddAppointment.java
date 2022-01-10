package com.example.pocketdoc;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;


public class AddAppointment extends AppCompatActivity {
    TextInputEditText textInputEditTextappointmenttime, textInputEditTextappointmentdate, textInputEditTextappointmenthospital, textInputEditTextappointmentdisease;
    Button Add_button, Back_button;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        textInputEditTextappointmentdate = (TextInputEditText)findViewById(R.id.appointmentdate);
        textInputEditTextappointmenttime = (TextInputEditText)findViewById(R.id.appointmenttime);
        textInputEditTextappointmenthospital = (TextInputEditText)findViewById(R.id.appointmenthospital);
        textInputEditTextappointmentdisease = (TextInputEditText)findViewById(R.id.appointmentdisease);
        Add_button = (Button)findViewById(R.id.Add_button);
        Back_button = (Button)findViewById(R.id.Back_Button);
        progressBar = findViewById(R.id.progress);


        Add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appointmenttime, appointmentdate, appointmenthospital, appointmentdisease;
                appointmentdate = String.valueOf(textInputEditTextappointmentdate.getText());
                appointmenttime = String.valueOf(textInputEditTextappointmenttime.getText());
                appointmenthospital = String.valueOf(textInputEditTextappointmenthospital.getText());
                appointmentdisease = String.valueOf(textInputEditTextappointmentdisease.getText());


                if (!appointmenttime.equals("") && !appointmentdate.equals("") && !appointmenthospital.equals("") && !appointmentdisease.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "appointmentdate";
                            field[1] = "appointmenttime";
                            field[2] = "appointmenthospital";
                            field[3] = "appointmentdisease";
                            //Creating array for data
                            String[] data = new String[4];
                            field[0] = appointmentdate;
                            field[1] = appointmenttime;
                            field[2] = appointmenthospital;
                            field[3] = appointmentdisease;

                            PutData putData = new PutData("https://pocket-dr.herokuapp.com/addappointment.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Add Appointment Success")) {
                                        Intent intent = new Intent(getApplicationContext(), AddAppointment.class);

                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                                //End Write and Read data with URL
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                }
            }

        });

        Back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAppointment.this, homepage.class));
            }
        });
    }
}