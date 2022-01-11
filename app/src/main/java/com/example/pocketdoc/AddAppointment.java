package com.example.pocketdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;


public class AddAppointment extends AppCompatActivity {

    TextInputEditText textInputEditTextAppointmentDate, textInputEditTextAppointmentTime, textInputEditTextAppointmentHospital, textInputEditTextAppointmentDisease;
    Button Button_Add,Button_Back;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        textInputEditTextAppointmentDate = findViewById(R.id.appointmentdate);
        textInputEditTextAppointmentTime = findViewById(R.id.appointmenttime);
        textInputEditTextAppointmentHospital = findViewById(R.id.appointmenthospital);
        textInputEditTextAppointmentDisease = findViewById(R.id.appointmentdisease);
        Button_Add= findViewById(R.id.Add_button);
        Button_Back= findViewById(R.id.Back_Button);
        progressBar = findViewById(R.id.progressbar);


        Button_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appointmentdate, appointmenttime, appointmenthospital, appointmentdisease;
                appointmentdate = String.valueOf(textInputEditTextAppointmentDate.getText());
                appointmenttime = String.valueOf(textInputEditTextAppointmentTime.getText());
                appointmenthospital = String.valueOf(textInputEditTextAppointmentHospital.getText());
                appointmentdisease = String.valueOf(textInputEditTextAppointmentDisease.getText());


                if (!appointmentdate.equals("") && !appointmenttime.equals("") && !appointmenthospital.equals("") && !appointmentdisease.equals("") ) {
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
                            data[0] = appointmentdate;
                            data[1] = appointmenttime;
                            data[2] = appointmenthospital;
                            data[3] = appointmentdisease;


                            PutData putData = new PutData("https://pocket-dr.herokuapp.com/addappointment.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Add appointment Success")) {
                                        Intent intent = new Intent(getApplicationContext(), AddAppointment.class);

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