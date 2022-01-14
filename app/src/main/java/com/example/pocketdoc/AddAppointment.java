package com.example.pocketdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;


public class AddAppointment extends AppCompatActivity {

    TextInputEditText textInputEditTextAppointmentUser, textInputEditTextAppointmentDate, textInputEditTextAppointmentTime, textInputEditTextAppointmentHospital, textInputEditTextAppointmentDisease;
    Button Button_Add,Button_Back;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        textInputEditTextAppointmentUser = findViewById(R.id.username);
        textInputEditTextAppointmentDate = findViewById(R.id.appointment_date);
        textInputEditTextAppointmentTime = findViewById(R.id.appointment_time);
        textInputEditTextAppointmentHospital = findViewById(R.id.appointment_hospital);
        textInputEditTextAppointmentDisease = findViewById(R.id.appointment_disease);
        Button_Add= findViewById(R.id.Add_button);
        Button_Back= findViewById(R.id.Back_Button);
        progressBar = findViewById(R.id.progressbar);


        Button_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username, appointment_date, appointment_time, appointment_hospital, appointment_disease;
                username = String.valueOf(textInputEditTextAppointmentUser.getText());
                appointment_date = String.valueOf(textInputEditTextAppointmentDate.getText());
                appointment_time = String.valueOf(textInputEditTextAppointmentTime.getText());
                appointment_hospital = String.valueOf(textInputEditTextAppointmentHospital.getText());
                appointment_disease = String.valueOf(textInputEditTextAppointmentDisease.getText());


                if ( !username.equals("") && !appointment_date.equals("") && !appointment_time.equals("") && !appointment_hospital.equals("") && !appointment_disease.equals("") ) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[5];
                            field[0] = "username";
                            field[1] = "appointment_date";
                            field[2] = "appointment_time";
                            field[3] = "appointment_hospital";
                            field[4] = "appointment_disease";
                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = username;
                            data[1] = appointment_date;
                            data[2] = appointment_time;
                            data[3] = appointment_hospital;
                            data[4] = appointment_disease;


                            PutData putData = new PutData("https://pocket-dr.herokuapp.com/addappointment.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Add appointment Success")) {
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Myappointment.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                                //End Write and Read data with URL
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"All fields required", Toast.LENGTH_SHORT).show();
                }
            }

        });

        Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAppointment.this, homepage.class));
            }
        });
    }
}