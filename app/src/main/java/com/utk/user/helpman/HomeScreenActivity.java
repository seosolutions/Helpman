package com.utk.user.helpman;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class HomeScreenActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private User userDetails;
    private TextView welcomeMessage;
    private TextView subtitle;
    private NavigationView mNavView;
    private SharedPrefManager sharedPrefManager;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initializeAllComponents();
        getUserDetailsAfterLogin(getIntent());
        Log.i("User name: ", userDetails.getName());
        welcomeMessage.setText("Welcome " + userDetails.getName() + "!");
        subtitle.setText("Select the service you need");
        assignActionToButtons();
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                Log.i("Item clicked:", item.getTitle().toString() + ":" + item.getItemId() + ":" + R.id.navigation_item_1);
                if(item.getItemId() == R.id.navigation_item_2) {
                    Log.i("Starting activity", "RequestHistory");
                    Intent requestHistoryIntent = new Intent(HomeScreenActivity.this, RequestsHistory.class);
                    requestHistoryIntent.putExtra("userId", userDetails.getId());
                    startActivity(requestHistoryIntent);
                    return true;
                }

                else if(item.getItemId() == R.id.navigation_item_3) {
                    sharedPrefManager.clearSharedPreferences();
                    Intent intent = new Intent(HomeScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    private void initializeAllComponents() {
        welcomeMessage = (TextView) findViewById(R.id.welcome_msg);
        subtitle = (TextView) findViewById(R.id.subtitle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavView = (NavigationView) findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(R.string.app_name);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        sharedPrefManager = new SharedPrefManager(HomeScreenActivity.this);
        Toast.makeText(HomeScreenActivity.this, "Welcome to Helpman!", Toast.LENGTH_LONG).show();


       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void assignActionToButtons() {
        int buttonIds[] = {R.id.carpenter, R.id.plumber, R.id.electrician, R.id.cook};
        for(int id: buttonIds) {
            Button clickedButton = (Button) findViewById(id);
            final String serviceType = clickedButton.getText().toString();
            clickedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Selected Button", "<" + serviceType + ">");
                    Intent requestServiceIntent = new Intent(getApplicationContext(), RequestServiceActivity.class);
                    requestServiceIntent.putExtra("userDetails", userDetails);
                    requestServiceIntent.putExtra("serviceType", serviceType);
                    startActivity(requestServiceIntent);
                }
            });
        }
    }

    private void getUserDetailsAfterLogin(Intent usrInfo) {
        userDetails = (User) usrInfo.getSerializableExtra("userDetails");
    }
}
