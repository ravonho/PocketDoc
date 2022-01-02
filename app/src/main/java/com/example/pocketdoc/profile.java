package com.example.pocketdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.TextureView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;


import static com.google.android.material.snackbar.Snackbar.make;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class profile extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    TextInputLayout name, password, email, contact;
    Button update, changepassword;
    Spinner dropdown;
    //private static final String[] items = {"Bernard", "JON", "Wong", "Yao Lun"};
    String target = "";
    String userpassword = "";
    String beforeName = "default";
    String beforeEmail = "default@hotmail.com";
    String beforeContact = "9274010";
    String beforePassword = "123456";
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
        setContentView(R.layout.activity_profile);
        target = getIntent().getStringExtra("username");
        userpassword = getIntent().getStringExtra("password");
        Log.i("Username from intent", target);
        Log.i("Password from intent", userpassword);
        name = findViewById(R.id.username);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        update = findViewById(R.id.update);
        changepassword = findViewById(R.id.changepassword);

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
                        startActivity(new Intent(profile.this, homepage.class));
                        break;

                    case R.id.nav_profile:
                        Log.i("Menu_Drawer_Tag", "Profile item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(profile.this, profile.class));
                        break;

                    case R.id.nav_contact:
                        Log.i("Menu_Drawer_Tag", "Contact item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(profile.this, contactus.class));
                        break;

                    case R.id.nav_logout:
                        Log.i("Menu_Drawer_Tag", "Logout item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(profile.this, Login.class));
                        break;
                }
                return true;
            }
        });



        //Snackbar
        drawerLayout = findViewById(R.id.drawerlayout);




        //dropdown
//        dropdown = findViewById(R.id.spinner1);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        dropdown.setAdapter(adapter);
//        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                target = adapterView.getItemAtPosition(i).toString();
//                beforeName = target;
//                Toast toast = Toast.makeText(getApplicationContext(), "THIS IS ".concat(target), Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.CENTER, 0, 30);
//                toast.show();
//                Snackbar snackbar1 = make(drawerLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
//                snackbar1.show();
//                doGetByUser(target);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                Toast toast = Toast.makeText(getApplicationContext(), "NOTHING IS SELECTED ".concat(target), Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.CENTER, 0, 30);
//                toast.show();
//            }
//        });
//

        doGetByUser(target);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    updateUserInfo();
                }
                //Intent
                //startIntent
            };
        });

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent Intent = new Intent(profile.this, EditUserPassword.class);
                Intent.putExtra("username",target);
                Intent.putExtra("password",userpassword);
                startActivity(Intent);



            }
        });
    }


    private boolean isValid() {
        boolean nameValid, emailValid, contactValid;
        if (name.getEditText().getText().length() == 0){
            name.setErrorEnabled(true);
            name.setError("Username must not be empty");
            nameValid = false;
        } else {
            name.setErrorEnabled(false);
            nameValid = true;
        }

        if (email.getEditText().getText().length() == 0){
            email.setErrorEnabled(true);
            email.setError("Email must not be empty");
            emailValid = false;
        } else {
            email.setErrorEnabled(false);
            emailValid = true;
        }
        if (contact.getEditText().getText().length() == 0){
            contact.setErrorEnabled(true);
            contact.setError("Phone number must not be empty");
            contactValid = false;
        } else {
            contact.setErrorEnabled(false);
            contactValid = true;
        }
        return nameValid && contactValid && emailValid ;
    }
    private void updateUserInfo() {
        String url = "";
        String afterContact = contact.getEditText().getText().toString();
        String afterName = name.getEditText().getText().toString();
        String afterEmail = email.getEditText().getText().toString();
        if (!afterContact.equalsIgnoreCase(beforeContact)) {
            url = url.concat("&contact=").concat(afterContact);
        }
        if (!afterName.equalsIgnoreCase(beforeName)) {
            url = url.concat("&username=").concat(afterName);
        }
        if (!afterEmail.equalsIgnoreCase(beforeEmail)) {
            url = url.concat("&email=").concat(afterEmail);
        }

        Log.d("updateUserInfo", url);
        doUpdateByUser(beforeName, url);
    }

    private void doUpdateByUser (String target, String kv) {
        String url = "https://pocket-dr.herokuapp.com/index.php?action=UpdateUser&target=".concat(target);
        url = url.concat(kv);
        RequestQueue queue = Volley.newRequestQueue(profile.this);
        Log.d("Post URL", url);
        final Map<String, String> params = new HashMap<String, String>();
        Log.d("anything", "onClick: getParams ".concat(kv));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", "onResponse: ".concat(response));
                        try {
                            JSONObject object = new JSONObject(response);
                            int row = object.getInt("rowCount");
                            if (row == 1) {
                                Toast toast = Toast.makeText(getApplicationContext(), "User update successful ".concat(target), Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 30);
                                toast.show();
                                Snackbar snackbar1 = make(drawerLayout, "Update succeed", Snackbar.LENGTH_SHORT);
                                snackbar1.show();
                            }
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
        queue.add(stringRequest);
    }


    private void doGetByUser (String username) {
        RequestQueue queue = Volley.newRequestQueue(profile.this);
        String url ="https://pocket-dr.herokuapp.com/index.php?action=showUser&username=".concat(username);

        // Request a string response from the provided URL.
        final Map<String, String> params = new HashMap<String, String>();
        params.put("action", "showUser");
        params.put("username", target);
        Log.d("anything", "onClick: getParams ".concat(params.toString()));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String tempname = "a";
                        String temppass = "a";
                        String tempemail = "a";
                        String tempcontact = "a";
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
                                temppass = tmpobject.getString("password");
                                tempemail = tmpobject.getString("email");
                                tempcontact = tmpobject.getString("contact");
                            }
                            name.getEditText().setText(tempname);
                            email.getEditText().setText(tempemail);
                            contact.getEditText().setText(tempcontact);
                            beforeName = tempname;
                            beforeEmail = tempemail;
                            beforeContact = tempcontact;
                            userpassword = temppass;
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