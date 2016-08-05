package com.utk.user.helpman;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private EditText registeredNumber;
    private EditText password;
    private Button loginButton;
    private Button registrationButton;
    private TextView errorWindow;
    private Toolbar toolbar;
    private User usr;
    private String BASE_URL;
    private SharedPrefManager sharedPrefManager;

    public void postLogin(User usr) {
        Log.i("UserInfo", usr.getName());
        sharedPrefManager.setValue("USER_ID", usr.getId());
        sharedPrefManager.setValue("PASSWORD", usr.getPassword());
        Intent homeScreenIntent = new Intent(getApplicationContext(), HomeScreenActivity.class);
        homeScreenIntent.putExtra("userDetails",usr );
        startActivity(homeScreenIntent);
    }

    @Override
    public void processResponse(Object response) {
        JSONArray result = (JSONArray) response;
        if(result == null || result.length() == 0) {
            Toast.makeText(MainActivity.this, "Login Failed! Please try again.", Toast.LENGTH_LONG).show();
            //errorWindow.setText("Login Failed! Please try again.");
            return;
        }
        JSONObject userDetails = null;
        try {
            userDetails = (JSONObject) result.get(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("Result received: main", userDetails.toString());
        Gson gson = new Gson();
        postLogin(gson.fromJson(userDetails.toString(), User.class));
    }

    public void verifyCredentials(User usr) throws JSONException {
        HttpTask httpTask = new HttpTask(this);
        httpTask.worker = this;
        String LOGIN_URL = BASE_URL + "login/";
        JSONObject loginPayload = new JSONObject();
        loginPayload.put("id", usr.getId());
        loginPayload.put("password", usr.getPassword());
        Log.i("Payload", loginPayload.toString());
        httpTask.execute(LOGIN_URL, loginPayload.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
        if(sharedPrefManager.getValue("USER_ID") != null) {
            usr = new User(sharedPrefManager.getValue("USER_ID"), sharedPrefManager.getValue("PASSWORD"), null, null, null);
            Log.d("User details", usr.getId());
            try {
                verifyCredentials(usr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr = new User(registeredNumber.getText().toString(), password.getText().toString(), null, null, null);
                Log.d("User details", usr.getId());
                try {
                    verifyCredentials(usr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initializeComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        registeredNumber = (EditText) findViewById(R.id.registered_number);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login_button);
        //registrationButton = (Button) findViewById(R.id.register_button);
        errorWindow = (TextView) findViewById(R.id.error_window);
        sharedPrefManager = new SharedPrefManager(MainActivity.this);

        try {
            BASE_URL = HelpmanUtil.getProperty("base.url", this);
            Log.i("Base Url", BASE_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

}
