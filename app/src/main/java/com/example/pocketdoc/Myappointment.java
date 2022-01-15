package com.example.pocketdoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class Myappointment extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextInputLayout name, appointment_date, appointment_time,appointment_disease,appointment_hospital;
    RequestQueue queue;
    Button nextRecord, previousRecord;
    String target = "";
    int appointment_id, idIndex;
    ArrayList <Integer> idArray;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setCurrent(int curr){
        appointment_id = curr;
    }

    public int getCurrent() {
        return appointment_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myappointment);
        target = getIntent().getStringExtra("username");
        Log.i("Username from intent", target);
        queue = Volley.newRequestQueue(Myappointment.this);
        idArray = new ArrayList<Integer>();
        idIndex = 0;
        name = findViewById(R.id.username);
        appointment_date = findViewById(R.id.appointment_date);
        appointment_time = findViewById(R.id.appointment_time);
        appointment_hospital = findViewById(R.id.appointment_hospital);
        appointment_disease = findViewById(R.id.appointment_disease);

        previousRecord = findViewById(R.id.previousRecord);
        nextRecord = findViewById(R.id.nextRecord);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_Open, R.string.menu_CLose);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Log.i("MENU_DRAWER_TAG", "Home item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(Myappointment.this, homepage.class));
                        break;

                    case R.id.nav_profile:
                        Log.i("Menu_Drawer_Tag", "Profile item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(Myappointment.this, profile.class));
                        break;

                    case R.id.nav_contact:
                        Log.i("Menu_Drawer_Tag", "Contact item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(Myappointment.this, contactus.class));
                        break;

                    case R.id.nav_logout:
                        Log.i("Menu_Drawer_Tag", "Logout item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(Myappointment.this, Login.class));
                        break;
                }
                return true;
            }
        });

        //Snackbar
        drawerLayout = findViewById(R.id.drawerlayout);

        previousRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Previous Button Before", "onClick: %s".format(String.valueOf(previousRecord.isEnabled())));
                Log.d("Previous Button Before", "current Index: ".concat(String.valueOf(idIndex)));
                Log.d("Previous Button Before", "idArray size: ".concat(String.valueOf(idArray.size())));
                if (!(idIndex - 1 < 0)) {
                    previousRecord.setEnabled(true);
                    nextRecord.setEnabled(true);
                    idIndex = idIndex - 1;
                    setCurrent(idArray.get(idIndex));
                    getAppointmentByID(getCurrent());
                }
                else {
                    Toast.makeText(getApplicationContext(),"No past appointments found!", Toast.LENGTH_LONG).show();
                    previousRecord.setEnabled(false);
                }
                Log.d("Previous Button After", "onClick: %s".format(String.valueOf(previousRecord.isEnabled())));
                Log.d("Previous Button After", "current Index: ".concat(String.valueOf(idIndex)));
            }
        });

        nextRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Next Button Before", "onClick: %s".format(String.valueOf(nextRecord.isEnabled())));
                Log.d("Next Button Before", "current Index: ".concat(String.valueOf(idIndex)));
                Log.d("Next Button Before", "idArray size: ".concat(String.valueOf(idArray.size())));
                if (!(idIndex + 1 > idArray.size() - 1)) {
                    nextRecord.setEnabled(true);
                    previousRecord.setEnabled(true);
                    idIndex = idIndex + 1;
                    setCurrent(idArray.get(idIndex));
                    getAppointmentByID(getCurrent());
                }
                else {
                    Toast.makeText(getApplicationContext(),"This is the latest appointment!", Toast.LENGTH_LONG).show();
                    nextRecord.setEnabled(false);
                }
                Log.d("Next Button After", "onClick: %s".format(String.valueOf(nextRecord.isEnabled())));
                Log.d("Next Button After", "current Index: ".concat(String.valueOf(idIndex)));
            }
        });

        doGetAppointment();
    }

    private void doGetAppointment() {
        doGetAppointmentIDByName(target);
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                Log.d("current value", String.valueOf(getCurrent()));
                getAppointmentByID(getCurrent());
            }
        }, 1000);
    }

    private void doGetAppointmentIDByName(String username) {
        String url ="https://pocket-dr.herokuapp.com/index.php?action=getAppointmentIDByName&username=".concat(username);

        // Request a string response from the provided URL.
        final Map<String, String> params = new HashMap<String, String>();
        params.put("action", "getAppointmentIDByName");
        params.put("username", target);
        Log.d("getAppointmentIDByName", "onClick: getParams ".concat(params.toString()));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", "onResponse: ".concat(response));
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("results");
                            int row = object.getInt("RowCount");
                            Log.d("GAID array", array.toString());
                            Log.d("GAID rowcount", String.valueOf(row));
                            for (int i = 0; i<array.length();i++){
                                JSONObject tmpobject = array.getJSONObject(i);
                                idArray.add(tmpobject.getInt("idAppointment"));
                                Log.d("get id for loop", String.valueOf(idArray.get(i)));
                            }
                            setCurrent(idArray.get(0));
                            Log.d("after getID curr value", String.valueOf(getCurrent()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Display the first 500 characters of the response string.
                        Log.d("Volley Reponse", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
            @Override
            protected Map<String, String> getParams() { return params; }
        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void getAppointmentByID(int id) {
        Log.d("passed in id value", String.valueOf(id));
        String url ="https://pocket-dr.herokuapp.com/index.php?action=getAppointmentByID&idAppointment=".concat(String.valueOf(id));

        // Request a string response from the provided URL.
        final Map<String, String> params = new HashMap<String, String>();
        params.put("action", "getAppointmentByID");
        params.put("idAppointment", String.valueOf(id));
        Log.d("getAppointmentByID", "onClick: getParams ".concat(params.toString()));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String tempname = "a";
                        String tempdate = "a";
                        String temptime = "a";
                        String temphospital = "a";
                        String tempdisease = "a";
                        Log.d("response", "onResponse: ".concat(response));
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("results");
                            int row = object.getInt("RowCount");
                            Log.d("array", array.toString());
                            Log.d("rowcount", String.valueOf(row));
                            for (int i = 0; i<array.length();i++){
                                JSONObject tmpobject = array.getJSONObject(i);
                                tempname = tmpobject.getString("username");
                                tempdate = tmpobject.getString("appointment_date");
                                temptime = tmpobject.getString("appointment_time");
                                temphospital = tmpobject.getString("appointment_hospital");
                                tempdisease = tmpobject.getString("appointment_disease");
                            }
                            name.getEditText().setText(tempname);
                            appointment_date.getEditText().setText(tempdate);
                            appointment_time.getEditText().setText(temptime);
                            appointment_hospital.getEditText().setText(temphospital);
                            appointment_disease.getEditText().setText(tempdisease);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Display the first 500 characters of the response string.
                        Log.d("Volley Reponse", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
            @Override
            protected Map<String, String> getParams() { return params; }
        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}