package com.example.pocketdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class homepage extends AppCompatActivity implements View.OnClickListener{

    private CardView D1, D2, D3, D4, D5, D6;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
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
        setContentView(R.layout.activity_homepage);

        D1 = (CardView) findViewById(R.id.addappointment);
        D2 = (CardView) findViewById(R.id.searchDr);
        D3 = (CardView) findViewById(R.id.searchD);
        D4 = (CardView) findViewById(R.id.myappointment);
        D5 = (CardView) findViewById(R.id.searchHospital);
        D6 = (CardView) findViewById(R.id.checkin);

        D1.setOnClickListener((View.OnClickListener) this);
        D2.setOnClickListener((View.OnClickListener) this);
        D3.setOnClickListener((View.OnClickListener) this);
        D4.setOnClickListener((View.OnClickListener) this);
        D5.setOnClickListener((View.OnClickListener) this);
        D6.setOnClickListener((View.OnClickListener) this);

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
                        break;

                    case R.id.nav_profile:
                        Log.i("Menu_Drawer_Tag", "Profile item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(homepage.this, profile.class));
                        break;

                    case R.id.nav_contact:
                        Log.i("Menu_Drawer_Tag", "Contact item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(homepage.this, contactus.class));
                        break;

                    case R.id.nav_logout:
                        Log.i("Menu_Drawer_Tag", "Logout item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(homepage.this, Login.class));
                        break;
                }
                return true;
            }
        });
    }
    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.addappointment : i = new Intent(this,AddAppointment.class); startActivity(i);
            break;

            case R.id.searchDr : i = new Intent(this,SearchDoctor.class); startActivity(i);
                break;

            case R.id.searchD : i = new Intent(this,SearchDisease.class); startActivity(i);
                break;

            case R.id.myappointment : i = new Intent(this,Myappointment.class); startActivity(i);
                break;

            case R.id.searchHospital:  i = new Intent(this,SearchHospital.class); startActivity(i);
                break;

            case R.id.checkin:  i = new Intent(this,Check_In.class); startActivity(i);
                break;
        }
    }
}
