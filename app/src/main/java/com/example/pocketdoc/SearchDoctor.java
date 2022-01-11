package com.example.pocketdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SearchDoctor extends AppCompatActivity {


    EditText searchInput;
    ImageView searchViewDoctor;
    Button buttonSDoctor;

    RequestQueue requestQueue;
    static Map<String, String> params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctor);


        buttonSDoctor = findViewById(R.id.buttonSDoctor);
        buttonSDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchDoctor.this, homepage.class);
                startActivity(intent);
            }


        });

        searchInput = findViewById(R.id.editTextTextPersonName4);
        searchViewDoctor = findViewById(R.id.searchViewDoctor);

        requestQueue = Volley.newRequestQueue(SearchDoctor.this);

        params = new HashMap<String, String>();
        searchViewDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Search", searchInput.getText().toString());
                params.put("action", "searchDoctor");
                params.put("doctor_name", searchInput.getText().toString());
                ProgressDialog loading = new ProgressDialog(SearchDoctor.this);
                loading.setTitle("Getting Doctor Info");
                loading.setTitle("Please hold on...");
                loading.show();
                StringRequest searchRequest = new StringRequest(Request.Method.POST, "https://pocket-dr.herokuapp.com/index.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Response", response);
                                loading.dismiss();
                                try {
                                    JSONObject doctorData = new JSONObject(response).getJSONArray("results").getJSONObject(0);
                                    Log.d("Doctor Data", doctorData.toString());
                                    Intent foundDoctor = new Intent(SearchDoctor.this, search_doctor2.class);
                                    foundDoctor.putExtra("doctor_name", doctorData.getString("doctor_name"));
                                    foundDoctor.putExtra("doctor_age", doctorData.getString("doctor_age"));
                                    foundDoctor.putExtra("doctor_race", doctorData.getString("doctor_race"));
                                    foundDoctor.putExtra("doctor_background", doctorData.getString("doctor_background"));
                                    foundDoctor.putExtra("doctor_special", doctorData.getString("doctor_special"));
                                    foundDoctor.putExtra("doctor_exp", doctorData.getString("doctor_exp"));
                                    startActivity(foundDoctor);
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
                        return params;
                    }
                };
                requestQueue.add(searchRequest);
            }
        });
    }
}