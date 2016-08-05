package com.utk.user.helpman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by utk on 17-03-2016.
 */
public class RequestHistoryListCustomAdapter extends ArrayAdapter<Order> {
    private final Context mContext;
    private final List<Order> requestHistoryList;

    public RequestHistoryListCustomAdapter(Context context, List<Order> orderList) {
        super(context, -1, orderList);
        this.mContext = context;
        this.requestHistoryList = orderList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tableView = inflater.inflate(R.layout.request_history_tabular_list_item, parent, false);
        TextView orderId = (TextView) tableView.findViewById(R.id.order_id);
        orderId.setText(String.valueOf(requestHistoryList.get(position).getId()));

        TextView service = (TextView) tableView.findViewById(R.id.service);
        service.setText(String.valueOf(requestHistoryList.get(position).getServiceRequested()));

        TextView status = (TextView) tableView.findViewById(R.id.status);
        status.setText(String.valueOf(requestHistoryList.get(position).getStatus()));

        TextView requestedTime = (TextView) tableView.findViewById(R.id.date);
        requestedTime.setText(String.valueOf(requestHistoryList.get(position).getRequestTimestamp()));

        return tableView;
    }
}
