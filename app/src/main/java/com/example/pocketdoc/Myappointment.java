package com.example.pocketdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Myappointment extends AppCompatActivity {

    EditText searchAppointment;
    ImageView SearchViewAppointment;
    Button buttonSAppointment;

    RequestQueue requestQueue1;
    static Map<String, String> params1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myappointment);


        buttonSAppointment = findViewById(R.id.buttonSAppointment);
        buttonSAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Myappointment.this, homepage.class);
                startActivity(intent1);
            }


        });

        searchAppointment = findViewById(R.id.editTextTextPersonName2);
        SearchViewAppointment = findViewById(R.id.SearchViewAppointment);

        requestQueue1 = Volley.newRequestQueue(Myappointment.this);

        params1 = new HashMap<String, String>();
        SearchViewAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Search Appointment", searchAppointment.getText().toString());
                params1.put("action", "showAppointment");
                params1.put("appointment_date", searchAppointment.getText().toString());
                ProgressDialog loading = new ProgressDialog(Myappointment.this);
                loading.setTitle("Getting Appointment Info");
                loading.setTitle("Please hold on...");
                loading.show();
                StringRequest searchRequest1 = new StringRequest(Request.Method.POST, "https://pocket-dr.herokuapp.com/index.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Response", response);
                                loading.dismiss();
                                try {
                                    JSONObject AppointmentData = new JSONObject(response).getJSONArray("results").getJSONObject(0);
                                    Log.d("Appointment Data", AppointmentData.toString());
                                    Intent foundAppointment = new Intent(Myappointment.this, list_appointment.class);
                                    foundAppointment.putExtra("appointment_date", AppointmentData.getString("appointment_date"));
                                    foundAppointment.putExtra("appointment_time", AppointmentData.getString("appointment_time"));
                                    foundAppointment.putExtra("appointment_hospital", AppointmentData.getString("appointment_hospital"));
                                    foundAppointment.putExtra("appointment_disease", AppointmentData.getString("appointment_disease"));
                                    startActivity(foundAppointment);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams(){
                        return params1;
                    }
                };
                requestQueue1.add(searchRequest1);
            }
        });
    }

}