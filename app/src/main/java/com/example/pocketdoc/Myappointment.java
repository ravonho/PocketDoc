package com.example.pocketdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Myappointment extends AppCompatActivity {

    EditText Search;
    ListView listview;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hospital);

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

        listview = findViewById(R.id.list_view);
        Search = findViewById(R.id.search);
        Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search_data(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    ArrayList<Appointment> model;
    void search_data(String data_search)
    {
        String url="https://pocket-dr.herokuapp.com/index.php?action=showAppointment="+data_search;
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response );
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray getresult = jsonObject.getJSONArray("results");
                            model = new ArrayList<>();
                            for (int i=0; i<getresult.length(); i++)
                            {
                                JSONObject getData = getresult.getJSONObject(i);
                                String appointmenttime = getData.getString("appointmenttime");
                                String appointmentdate = getData.getString("appointmentdate");
                                String appointmenthospital = getData.getString("appointmenthospital");
                                String appointmentdisease = getData.getString("appointmentdisease");


                                model.add(new Appointment(appointmenttime, appointmentdate, appointmenthospital, appointmentdisease));
                            }
                            final Adapter2 adapter =new Adapter2(Myappointment.this, model);
                            Log.d("Adapter Count", Integer.toString(adapter.getCount()));
                            listview.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Myappointment.this, "Error Occur", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

 class Adapter2 extends BaseAdapter {

    ArrayList<Appointment> model;
    LayoutInflater inflater;
    Context context;
    Adapter2(Context context, ArrayList<Appointment> model)
    {
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.model=model;
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Appointment getItem(int position) {
        return model.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    TextView appointmenttime, appointmentdate, appointmenthospital, appointmentdisease;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(R.layout.list_hospital, parent, false);
        appointmentdate=view.findViewById(R.id.appointment_date);
        appointmenttime=view.findViewById(R.id.appointment_time);
        appointmenthospital=view.findViewById(R.id.appointment_location);
        appointmentdisease=view.findViewById(R.id.appointment_disease);


        appointmentdate.setText(model.get(position).getappointmentdate());
        appointmenttime.setText(model.get(position).getappointmenttime());
        appointmenthospital.setText(model.get(position).getappointmenthospital());
        appointmentdisease.setText(model.get(position).getappointmentdisease());


        return view;
    }
}