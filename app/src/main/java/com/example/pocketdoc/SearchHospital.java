package com.example.pocketdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageButton;
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

public class SearchHospital extends AppCompatActivity {

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
                        startActivity(new Intent(SearchHospital.this, homepage.class));
                        break;

                    case R.id.nav_profile:
                        Log.i("Menu_Drawer_Tag", "Profile item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(SearchHospital.this, profile.class));
                        break;

                    case R.id.nav_contact:
                        Log.i("Menu_Drawer_Tag", "Contact item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(SearchHospital.this, contactus.class));
                        break;

                    case R.id.nav_logout:
                        Log.i("Menu_Drawer_Tag", "Logout item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(SearchHospital.this, Login.class));
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
    ArrayList<Object> model;
    void search_data(String data_search)
    {
        String url="https://pocket-dr.herokuapp.com/index.php?action=showHospital&Name="+data_search;
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
                                String Name = getData.getString("Name");
                                String Contact = getData.getString("Contact");
                                String Location = getData.getString("Location");
                                String Doctor = getData.getString("Doctor");
                                String Start = getData.getString("Start");
                                String End = getData.getString("End");

                                model.add(new Object(Name, Contact, Location, Doctor, Start, End));
                            }
                            final Adapter adapter =new Adapter(SearchHospital.this, model);
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
                        Toast.makeText(SearchHospital.this, "Error Occur", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

class Adapter extends BaseAdapter {

    ArrayList<Object> model;
    LayoutInflater inflater;
    Context context;
    Adapter(Context context, ArrayList<Object> model)
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
    public Object getItem(int position) {
        return model.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    TextView Name, Contact, Location, Doctor, Start, End;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(R.layout.list_item, parent, false);
        Name=view.findViewById(R.id.hospital_name);
        Contact=view.findViewById(R.id.hospital_contact);
        Location=view.findViewById(R.id.hospital_location);
        Doctor=view.findViewById(R.id.hospital_doctor);
        Start=view.findViewById(R.id.hospital_start_time);
        End=view.findViewById(R.id.hospital_end_time);

        Name.setText(model.get(position).getName());
        Contact.setText(model.get(position).getContact());
        Location.setText(model.get(position).getLocation());
        Doctor.setText(model.get(position).getDoctor());
        Start.setText(model.get(position).getStart());
        End.setText(model.get(position).getEnd());

        return view;
    }
}