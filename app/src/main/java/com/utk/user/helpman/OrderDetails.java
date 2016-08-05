package com.utk.user.helpman;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class OrderDetails extends AppCompatActivity {

    private TextView msg;
    private TextView service;
    private TextView requestTime;
    private TextView status;
    private TextView orderId;
    private TextView billedAmt;
    private TextView contactName;
    private TextView contactNumber;
    private TextView contactAddress;
    private Order orderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeAllComponents();
        populateAllComponents();
    }

    private void populateAllComponents() {
        getSupportActionBar().setTitle("Request Details");
        Intent intent = getIntent();
        orderDetails = (Order) intent.getSerializableExtra("orderDetails");
        msg.setText(getMessage(orderDetails.getStatus()));
        orderId.setText(String.valueOf(orderDetails.getId()));
        service.setText(orderDetails.getServiceRequested());
        requestTime.setText(String.valueOf(orderDetails.getRequestTimestamp()));
        billedAmt.setText("Rs. " + String.valueOf(orderDetails.getBilledAmt()));
        contactNumber.setText(String.valueOf(orderDetails.getContactNumber()));
        contactName.setText(orderDetails.getContactName());
        contactAddress.setText(orderDetails.getContactAddress());
    }

    private CharSequence getMessage(String status) {
        Map<String, String> statusMsgMap = new HashMap<>();
        statusMsgMap.put(RequestStatus.RECEIVED.getStatusCode(), getResources().getString(R.string.msg_order_details_received_status));
        statusMsgMap.put(RequestStatus.PROCESSING.getStatusCode(), getResources().getString(R.string.msg_order_details_processing_status));
        statusMsgMap.put(RequestStatus.CLOSED.getStatusCode(), getResources().getString(R.string.msg_order_details_closed_status));
        statusMsgMap.put(RequestStatus.CANCELLED.getStatusCode(), getResources().getString(R.string.msg_order_details_cancelled_status));
        return statusMsgMap.get(status);
    }

    private void initializeAllComponents() {
        service = (TextView) findViewById(R.id.order_details_service);
        requestTime = (TextView) findViewById(R.id.order_details_date);
        status = (TextView) findViewById(R.id.order_details_status);
        billedAmt = (TextView) findViewById(R.id.order_details_billed_amount);
        contactName = (TextView) findViewById(R.id.order_details_contact_name);
        contactNumber = (TextView) findViewById(R.id.order_details_contact_number);
        contactAddress = (TextView) findViewById(R.id.order_details_contact_address);
        orderId = (TextView) findViewById(R.id.order_details_order_id);
        msg = (TextView) findViewById(R.id.msg);

        orderDetails = new Order();
    }

}
