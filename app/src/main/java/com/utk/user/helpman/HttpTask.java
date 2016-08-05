package com.utk.user.helpman;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by utk on 02-03-2016.
 */
public class HttpTask extends AsyncTask<Object, Void, Object> {

    public AsyncResponse worker = null;
    private ProgressDialog progressDialog = null;
    private Context mContext;

    public HttpTask(Context context) {
        this.mContext = context;
    }

    private static String getResponseText(InputStream inStream) {
        // very nice trick from
        // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
        return new Scanner(inStream).useDelimiter("\\A").next();
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Object result) {
        progressDialog.dismiss();
        worker.processResponse(result);
    }

    @Override
    protected Object doInBackground(Object... params) {
        String url = params[0].toString();
        String requestMethod = "GET";
        String payload = null;
        if(params.length > 1) {
            payload = params[1].toString();
            requestMethod = "POST";
        }
        try {
            URL requestUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setConnectTimeout(20*1000);
            if(requestMethod == "POST") {
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                OutputStreamWriter requestStream = new OutputStreamWriter(conn.getOutputStream());
                requestStream.write(payload);
                requestStream.flush();
                requestStream.close();
            }

            int responseCode = conn.getResponseCode();
            Log.i("Response Code", responseCode + "");
            if(responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inpStream = new BufferedInputStream(conn.getInputStream());
                JSONArray responseJSON = new JSONArray(getResponseText(inpStream));
                //Log.i("response body:", responseJSON.toString());
                conn.disconnect();
                return responseJSON;
            }
            else {
                InputStream inpStream = new BufferedInputStream(conn.getInputStream());
                JSONArray responseJSON = new JSONArray(getResponseText(inpStream));
                Log.i("response body:", responseJSON.toString());
                conn.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
