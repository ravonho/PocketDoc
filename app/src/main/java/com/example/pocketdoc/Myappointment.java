package com.example.pocketdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
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

        listview = findViewById(R.id.list_view);
        Search = findViewById(R.id.search);
        Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                View_Appointment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    ArrayList<Appointment> model;
    void View_Appointment(String data_search)
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
                            final Adapter2 adapter2 =new Adapter2(Myappointment.this, model);
                            Log.d("Adapter Count", Integer.toString(adapter2.getCount()));
                            listview.setAdapter(adapter2);

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
        View view=inflater.inflate(R.layout.list_item, parent, false);
        appointmenttime=view.findViewById(R.id.appointmenttime);
        appointmentdate=view.findViewById(R.id.appointmentdate);
        appointmenthospital=view.findViewById(R.id.appointmenthospital);
        appointmentdisease=view.findViewById(R.id.appointmentdisease);


        appointmenttime.setText(model.get(position).getappointmenttime());
        appointmentdate.setText(model.get(position).getappointmentdate());
        appointmenthospital.setText(model.get(position).getappointmenthospital());
        appointmentdisease.setText(model.get(position).getappointmentdisease());

        return view;
    }
}