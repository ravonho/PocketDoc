package com.example.pocketdoc;

import androidx.appcompat.app.AlertDialog;
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
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class AddAppointment extends AppCompatActivity {

    TextInputEditText textInputEditTextChooseTime, textInputEditTextChooseDate, textInputEditTextChooseHospital, textInputEditTextChooseDisease;
    Button Add_button;
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

        textInputEditTextChooseTime = findViewById(R.id.ChooseTime);
        textInputEditTextChooseDate = findViewById(R.id.ChooseDate);
        textInputEditTextChooseHospital = findViewById(R.id.ChooseHospital);
        textInputEditTextChooseDisease = findViewById(R.id.ChooseDisease);
        Add_button = findViewById(R.id.Add_button);



        Add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ChooseTime, ChooseDate, ChooseHospital, ChooseDisease;
                ChooseTime = String.valueOf(textInputEditTextChooseTime.getText());
                ChooseDate = String.valueOf(textInputEditTextChooseTime.getText());
                ChooseHospital = String.valueOf(textInputEditTextChooseTime.getText());
                ChooseDisease = String.valueOf(textInputEditTextChooseTime.getText());


                if (!ChooseTime.equals("") && !ChooseDate.equals("") && !ChooseHospital.equals("") && !ChooseDisease.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "ChooseTime";
                            field[1] = "ChooseDate";
                            field[2] = "ChooseLocation";
                            field[3] = "ChooseDisease";
                            //Creating array for data
                            String[] data = new String[4];
                            field[0] = "ChooseTime";
                            field[1] = "ChooseDate";
                            field[2] = "ChooseLocation";
                            field[3] = "ChooseDisease";
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
    }
}