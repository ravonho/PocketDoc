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

    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        textInputEditTextappointmenttime = findViewById(R.id.appointmenttime);
        textInputEditTextappointmentdate = findViewById(R.id.appointmentdate);
        textInputEditTextappointmenthospital = findViewById(R.id.appointmenthospital);
        textInputEditTextappointmentdisease = findViewById(R.id.appointmentdisease);
        Add_button = findViewById(R.id.Add_button);
        Back_button = findViewById(R.id.Back_Button);



        Add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appointmenttime, appointmentdate, appointmenthospital, appointmentdisease;
                appointmenttime = String.valueOf(textInputEditTextappointmenttime .getText());
                appointmentdate = String.valueOf(textInputEditTextappointmentdate.getText());
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
                            field[0] = "appointmenttime";
                            field[1] = "appointmentdate";
                            field[2] = "appointmenthospital";
                            field[3] = "appointmentdisease";
                            //Creating array for data
                            String[] data = new String[4];
                            field[0] = appointmenttime;
                            field[1] = appointmentdate;
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