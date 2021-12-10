package com.example.pocketdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class registerpage extends AppCompatActivity {

    Button regbtn;
    Spinner spinner;
    String spinner_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);

        spinner = findViewById(R.id.spinner_type);
        String user_type[] = getResources().getStringArray(R.array.spinner);
        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, user_type);
        spinner_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_value = parent.getItemAtPosition(position).toString();
                Log.d("spinner_value", "spinner_value" + spinner_value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        regbtn =findViewById(R.id.reg);
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registerpage.this, MainActivity.class));
            }
        });
    }
}