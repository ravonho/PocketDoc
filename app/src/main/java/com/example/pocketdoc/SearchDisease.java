package com.example.pocketdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
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

public class SearchDisease extends AppCompatActivity {

    EditText searchInput;
    ImageView searchViewDisease;
    Button buttonSDisease;
    RequestQueue requestQueue;
    static Map<String, String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_disease);


        buttonSDisease = findViewById(R.id.buttonSDisease);
        buttonSDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchDisease.this, homepage.class);
                startActivity(intent);
            }


        });



        searchInput = findViewById(R.id.editTextDisease);
        searchViewDisease = findViewById(R.id.searchViewDisease);

        requestQueue = Volley.newRequestQueue(SearchDisease.this);

        params = new HashMap<String, String>();
        searchViewDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Search", searchInput.getText().toString());
                params.put("action", "searchDisease");
                params.put("disease_name", searchInput.getText().toString());
                ProgressDialog loading = new ProgressDialog(SearchDisease.this);
                loading.setTitle("Getting Disease Info");
                loading.setTitle("Please hold on...");
                loading.show();
                StringRequest searchRequest = new StringRequest(Request.Method.POST, "https://pocket-dr.herokuapp.com/index.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Response", response);
                                loading.dismiss();
                                try {
                                    JSONObject diseaseData = new JSONObject(response).getJSONArray("results").getJSONObject(0);
                                    Log.d("Disease Data", diseaseData.toString());
                                    Intent foundDoctor = new Intent(SearchDisease.this, search_disease2.class);
                                    foundDoctor.putExtra("disease_name", diseaseData.getString("disease_name"));
                                    foundDoctor.putExtra("disease_desc", diseaseData.getString("disease_desc"));

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