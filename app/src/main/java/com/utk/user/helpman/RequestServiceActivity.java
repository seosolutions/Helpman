package com.utk.user.helpman;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RequestServiceActivity extends AppCompatActivity implements AsyncResponse{

    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private User userDetails;
    private EditText contactNumber;
    private EditText contactName;
    private EditText contactAddress;
    private Button submitRequestButton;
    private String serviceType;
    private String BASE_URL;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);
        initializeAllComponents();
        displayInformationForVerification(getIntent());
        submitRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order request = new Order();
                request.setUserId(userDetails.getId());
                request.setServiceRequested(serviceType);
                request.setContactNumber(contactNumber.getText().toString());
                request.setContactName(contactName.getText().toString());
                request.setContactAddress(contactAddress.getText().toString());
                request.setRequestTimestamp((int) (System.currentTimeMillis() / 1000L));
                request.setStatus(RequestStatus.RECEIVED.getStatusCode());
                submitOrder(request);
            }
        });
    }

    private void submitOrder(Order request) {
        HttpTask httpTask = new HttpTask(this);
        httpTask.worker = this;
        Gson gson = new GsonBuilder().serializeNulls().create();
        String payload = gson.toJson(request);
        Log.i("Payload", payload);
        httpTask.execute(BASE_URL + "submit-order/", payload);
    }

    private void initializeAllComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        contactNumber = (EditText) findViewById(R.id.contact_number);
        contactName = (EditText) findViewById(R.id.contact_name);
        contactAddress = (EditText) findViewById(R.id.contact_address);
        submitRequestButton = (Button) findViewById(R.id.submit_request);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        try {
            BASE_URL = HelpmanUtil.getProperty("base.url", this);
            Log.i("Base Url", BASE_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayInformationForVerification(Intent intent) {
        userDetails = (User) intent.getSerializableExtra("userDetails");
        serviceType = intent.getStringExtra("serviceType");
        actionBar.setTitle("Requesting " + serviceType);
        toolbar.setTitleTextColor(Color.WHITE);
        contactNumber.setText(userDetails.getId());
        contactName.setText(userDetails.getName());
        contactAddress.setText(userDetails.getAddress());
    }

    @Override
    public void processResponse(Object result) {
        JSONArray response = (JSONArray) result;
        try {
            Log.i("Order Received", response.get(0).toString());
            Gson gson = new GsonBuilder().serializeNulls().create();
            Order orderDetails = gson.fromJson(response.get(0).toString(), Order.class);
            Intent intent = new Intent(this, OrderDetails.class);
            intent.putExtra("orderDetails", orderDetails);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
