package com.utk.user.helpman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class RequestsHistory extends AppCompatActivity implements AsyncResponse {
    private List<Order> userRequestHistory;
    String userId;
    private String BASE_URL;

    public void getAllRequests(String userId) throws JSONException {
        HttpTask httpTask = new HttpTask(this);
        httpTask.worker = this;
        String REQUEST_URL = BASE_URL + "orders/user/" + userId;
        httpTask.execute(REQUEST_URL);
    }

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
        setContentView(R.layout.activity_requests_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("History");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent requestHistoryIntent = getIntent();
        userId = requestHistoryIntent.getStringExtra("userId");
        try {
            BASE_URL = HelpmanUtil.getProperty("base.url", this);
            Log.i("Base Url", BASE_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            getAllRequests(userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processResponse(Object response) {
        JSONArray result = (JSONArray) response;
        if(result == null || result.length() == 0) {
            //handle error
            return;
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Order>>(){}.getType();
        userRequestHistory = gson.fromJson(result.toString(), listType);
        Log.i("Result received: main", result.toString() + ":" + userRequestHistory.size());
        populateList();
    }

    private void populateList() {
        RequestHistoryListCustomAdapter adapter = new RequestHistoryListCustomAdapter(this, userRequestHistory);
        ListViewCompat requestListView = (ListViewCompat) findViewById(R.id.request_history);
        requestListView.setAdapter(adapter);

        requestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RequestsHistory.this, OrderDetails.class);
                intent.putExtra("orderDetails", userRequestHistory.get(position));
                startActivity(intent);
            }
        });
    }
}
