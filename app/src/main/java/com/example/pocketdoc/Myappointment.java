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

    EditText Search_appointment;
    ListView listView;
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
        setContentView(R.layout.activity_myappointment);

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

        listView = findViewById(R.id.listView);
        Search_appointment = findViewById(R.id.Search_Appointment);
        Search_appointment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence e, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence e, int start, int before, int count) {
                search_data(e.toString());
            }

            @Override
            public void afterTextChanged(Editable e) {

            }
        });
    }
    ArrayList<Appointment> model2;
    void search_data(String data_search)
    {
        String url="https://pocket-dr.herokuapp.com/index.php?action=showAppointment&appointmentdate="+data_search;
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
                            model2 = new ArrayList<>();
                            for (int i=0; i<getresult.length(); i++)
                            {
                                JSONObject getData = getresult.getJSONObject(i);
                                String appointmentdate = getData.getString("Date");
                                String appointmenttime = getData.getString("Time");
                                String appointmentdhospital = getData.getString("Location");
                                String appointmentdisease = getData.getString("Disease");


                                model2.add(new Appointment(appointmentdate, appointmenttime, appointmentdhospital, appointmentdisease));
                            }
                            final Adapter2 adapter2 =new Adapter2(Myappointment.this, model2);
                            Log.d("Adapter Count", Integer.toString(adapter2.getCount()));
                            listView.setAdapter(adapter2);

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

class Adapter2 extends BaseAdapter{

    ArrayList<Appointment> model2;
    LayoutInflater inflater2;
    Context context2;
    Adapter2(Context context2, ArrayList<Appointment> model2)
    {
        inflater2=LayoutInflater.from(context2);
        this.context2=context2;
        this.model2=model2;
    }

    @Override
    public int getCount() {
        return model2.size();
    }

    @Override
    public Appointment getItem(int position) {
        return model2.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    TextView AppointmentDate, AppointmentTime, AppointmentHospital, AppointmentDisease;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=inflater2.inflate(R.layout.list_appointment, parent, false);
        AppointmentDate=view.findViewById(R.id.appointment_date);
        AppointmentTime=view.findViewById(R.id.appointment_time);
        AppointmentHospital=view.findViewById(R.id.appointment_hospital);
        AppointmentDisease=view.findViewById(R.id.appointment_disease);


        AppointmentDate.setText(model2.get(position).getappointmentdate());
        AppointmentTime.setText(model2.get(position).getappointmenttime());
        AppointmentHospital.setText(model2.get(position).getappointmenthospital());
        AppointmentDisease.setText(model2.get(position).getappointmentdisease());


        return view;
    }
}