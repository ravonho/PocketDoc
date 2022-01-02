package com.example.pocketdoc;

import static com.google.android.material.snackbar.Snackbar.make;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditUserPassword extends AppCompatActivity {

    TextInputLayout newpassword,password;
    TextInputLayout newpassword2;
    Button confirmpassword;
    String username;
    //String password;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_password);


        password = findViewById(R.id.oldpassword);
        newpassword = findViewById(R.id.newpassword);
        newpassword2 = findViewById(R.id.newpassword2);
        confirmpassword = findViewById(R.id.confirmpassword);
         //password = getIntent().getStringExtra("password");
         username = getIntent().getStringExtra("username");
        //Log.i("Username from intent", password);
        Log.i("Password from intent", username);

        confirmpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPassword() && isOldpasswordValid() && isNewpasswordValid()) {
                    updateUserPassword();
                }
            }
        });

    }

    private boolean isNewpasswordValid() {

            boolean newpasswordValid;


            if (!newpassword.getEditText().getText().toString().equals(newpassword2.getEditText().getText().toString()) ){
                newpassword2.setErrorEnabled(true);
                newpassword2.setError("Please enter the right password");
                newpasswordValid = false;
            } else {
                newpassword2.setErrorEnabled(false);
                newpasswordValid = true;
            }

            return  newpasswordValid;
        }


    private boolean isOldpasswordValid() {
        boolean oldpasswordValid = false;

        //Log.i("Current password ", String.valueOf(oldpassword));
        Log.i("Password from intent", String.valueOf(password));
        //Starting Write and Read data with URL
        //Creating array for parameters
        String[] field = new String[2];
        field[0] = "username";
        field[1] = "password";
        //Creating array for data
        String[] data = new String[2];
        data[0] = username;
        data[1] = password.getEditText().getText().toString();


        PutData putData = new PutData("https://pocket-dr.herokuapp.com/verifypassword.php", "POST", field, data);
        if (putData.startPut()) {
            if (putData.onComplete()) {
                String result = putData.getResult();
                Log.i("Result", result);
                Log.i("Username", username);
                Log.i("password", String.valueOf(password));
                if (result.equals("Password is correct")) {

                    oldpasswordValid = true;
                } else {
                    Toast.makeText(getApplicationContext(),"Please enter the right password", Toast.LENGTH_SHORT).show();
                    oldpasswordValid = false;
                }
            }
            
        }
        

        return  oldpasswordValid;
    }

    private void updateUserPassword() {
        String url = "";
        String afterPassword = newpassword.getEditText().getText().toString();
        url = url.concat("&password=").concat(afterPassword);
        Log.d("updateUserInfo", url);
        doUpdateByUser(username, url);
    }

    private void doUpdateByUser(String target, String kv) {
        String url = "https://pocket-dr.herokuapp.com/index.php?action=UpdateUser&target=".concat(target);
        url = url.concat(kv);
        RequestQueue queue = Volley.newRequestQueue(EditUserPassword.this);
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
                                Toast toast = Toast.makeText(getApplicationContext(), "Password update successful ".concat(target), Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 30);
                                toast.show();

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

    private boolean isPassword() {
        boolean passwordValid;

        if (newpassword.getEditText().getText().length() == 0){
            newpassword.setErrorEnabled(true);
            newpassword.setError("Password must not be empty");
            passwordValid = false;
        } else {
            newpassword.setErrorEnabled(false);
            passwordValid = true;
        }

        return  passwordValid;
    }
}