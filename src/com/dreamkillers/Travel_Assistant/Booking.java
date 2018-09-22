package com.dreamkillers.Travel_Assistant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mamun on 1/22/2015.
 */
public class Booking extends Activity {

    private static final String LOGIN_URL = "http://192.168.111.1:80/mamun/booking.php";
    private static final String time_URL = "http://192.168.111.1:80/mamun/time.php";
    private static final String busid_URL = "http://192.168.111.1:80/mamun/busid.php";
    boolean failure1 = true, failure2 = true;
    private Button button;
    private Spinner bus, date, route, time;
    private ProgressDialog pDialog;
    private String msg;
    //private String phnnmbr, id;
    private int success1 = 0, success2 = 0, success3;
    private JSONObject jsonObject;
    private JSONArray dates = null;
    private JSONArray times = null;
    private ArrayList<String> datelist, timelist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking);

        bus = (Spinner) findViewById(R.id.bus_spinner);
        date = (Spinner) findViewById(R.id.date_spinner);
        route = (Spinner) findViewById(R.id.route_spinner);
        time = (Spinner) findViewById(R.id.time_spinner);
        // bus.on

        bus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new DoPOST().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        route.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new DoPOST().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new DoPST().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button = (Button) findViewById(R.id.booking_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (success1 == 1 && success2 == 1) {
                    new mDoPST().execute();
                } else {
                    Toast.makeText(getApplicationContext(), "Not Available right now", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private class DoPOST extends AsyncTask<String, Void, Boolean> {

        private Context mContext = null;
        private Exception exception = null;
        //private String username,password;


        @Override
        protected Boolean doInBackground(String... arg0) {

            datelist = new ArrayList<String>();

            try {

                String busname = bus.getSelectedItem().toString();
                String routename = route.getSelectedItem().toString();
                //String phonenumbr=phonenmbr.getText().toString();
                //Log.d("pass", busname);


                //Setup the parameters
                ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
                //nameValuePairs.add(new BasicNameValuePair("FirstNameToSearch", strNameToSearch));
                //Add more parameters as necessary
                nameValue.add(new BasicNameValuePair("busname", busname));
                nameValue.add(new BasicNameValuePair("route", routename));
                //nameValue.add(new BasicNameValuePair("phonenumber",phonenumbr));
                Log.d("bus or route request!", "starting");


                //Create the HTTP request
                HttpParams httpParameters = new BasicHttpParams();

                //Setup timeouts
                HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
                HttpConnectionParams.setSoTimeout(httpParameters, 15000);

                HttpClient httpclient = new DefaultHttpClient(httpParameters);
                HttpPost httppost = new HttpPost(LOGIN_URL);
                httppost.setEntity(new UrlEncodedFormEntity(nameValue));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();

                String result = EntityUtils.toString(entity);
                Log.d("bus or route attempt", result);


                // Create a JSON object from the request response
                jsonObject = new JSONObject(result);
                Log.d("bus or route attempt", jsonObject.toString());
                //Retrieve the data from the JSON object
                success1 = jsonObject.getInt("success");
                msg = jsonObject.getString("message");
                if (success1 == 0) {
                    //failure1=false;
                    Log.d("login fail!", jsonObject.toString());

                } else if (success1 == 2) {
                    //failure1=false;
                    Log.d("login Successful!", jsonObject.toString());
                    dates = jsonObject.getJSONArray("posts");
                    for (int i = 0; i < dates.length(); i++) {
                        JSONObject c = dates.getJSONObject(i);
                        String dte = c.getString("date");
                        datelist.add(dte);
                    }

                } else {
                    //failure1=true;
                    Log.d("bus or route Successful!", jsonObject.toString());
                    dates = jsonObject.getJSONArray("posts");
                    for (int i = 0; i < dates.length(); i++) {
                        JSONObject c = dates.getJSONObject(i);
                        String dte = c.getString("date");
                        datelist.add(dte);
                    }
                }

            } catch (Exception e) {
                Log.e("ClientServerDemo", "Error:", e);
                exception = e;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean valid) {
            //Update the UI
            //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,datelist);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_textview, datelist);
            date.setAdapter(adapter);
            //pDialog.dismiss();
            if (exception != null) {
                Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        }


    }


    private class DoPST extends AsyncTask<String, Void, Boolean> {

        private Context mContext = null;
        private Exception exception = null;

        //private String username,password;
/*
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Booking.this);
            pDialog.setMessage("Loading Time...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
*/
        @Override
        protected Boolean doInBackground(String... arg0) {

            timelist = new ArrayList<String>();

            try {

                String busname = bus.getSelectedItem().toString();
                String routename = route.getSelectedItem().toString();
                String datename = date.getSelectedItem().toString();
                //String phonenumbr=phonenmbr.getText().toString();
                Log.d("time attempt", busname);


                //Setup the parameters
                ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
                //nameValuePairs.add(new BasicNameValuePair("FirstNameToSearch", strNameToSearch));
                //Add more parameters as necessary
                nameValue.add(new BasicNameValuePair("busname", busname));
                nameValue.add(new BasicNameValuePair("route", routename));
                nameValue.add(new BasicNameValuePair("datename", datename));
                //nameValue.add(new BasicNameValuePair("phonenumber",phonenumbr));
                Log.d("time request!", "starting");


                //Create the HTTP request
                HttpParams httpParameters = new BasicHttpParams();

                //Setup timeouts
                HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
                HttpConnectionParams.setSoTimeout(httpParameters, 15000);

                HttpClient httpclient = new DefaultHttpClient(httpParameters);
                HttpPost httppost = new HttpPost(time_URL);
                httppost.setEntity(new UrlEncodedFormEntity(nameValue));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();

                String result = EntityUtils.toString(entity);
                Log.d("time attempt", result);


                // Create a JSON object from the request response
                jsonObject = new JSONObject(result);
                Log.d("time attempt", jsonObject.toString());
                //Retrieve the data from the JSON object
                success2 = jsonObject.getInt("success");
                msg = jsonObject.getString("message");
                if (success2 == 0) {
                    failure2 = false;
                    Log.d("login fail!", jsonObject.toString());

                } else if (success2 == 2) {
                    failure2 = false;
                    Log.d("time attempt Successful!", jsonObject.toString());
                    times = jsonObject.getJSONArray("posts");
                    for (int i = 0; i < times.length(); i++) {
                        JSONObject c = times.getJSONObject(i);
                        String tme = c.getString("time");
                        timelist.add(tme);
                    }
                } else {
                    failure2 = false;
                    Log.d("time attempt Successful!", jsonObject.toString());
                    times = jsonObject.getJSONArray("posts");
                    for (int i = 0; i < times.length(); i++) {
                        JSONObject c = times.getJSONObject(i);
                        String tme = c.getString("time");
                        timelist.add(tme);
                    }
                }

            } catch (Exception e) {
                Log.e("ClientServerDemo", "Error:", e);
                exception = e;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean valid) {
            //Update the UI
            //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            //ArrayAdapter<String> adaptr = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,timelist);
            ArrayAdapter<String> adaptr = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_textview, timelist);
            time.setAdapter(adaptr);
            //pDialog.dismiss();
            if (exception != null) {
                Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        }


    }


    private class mDoPST extends AsyncTask<String, Void, Boolean> {

        //private String username,password;
        String busid;
        private Context mContext = null;
        private Exception exception = null;

        @Override
        protected Boolean doInBackground(String... arg0) {

            timelist = new ArrayList<String>();

            try {

                String busname = bus.getSelectedItem().toString();
                String routename = route.getSelectedItem().toString();
                String datename = date.getSelectedItem().toString();
                String timename = time.getSelectedItem().toString();
                Log.d("pass", busname);


                //Setup the parameters
                ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
                //nameValuePairs.add(new BasicNameValuePair("FirstNameToSearch", strNameToSearch));
                //Add more parameters as necessary
                nameValue.add(new BasicNameValuePair("busname", busname));
                nameValue.add(new BasicNameValuePair("route", routename));
                nameValue.add(new BasicNameValuePair("datename", datename));
                nameValue.add(new BasicNameValuePair("timename", timename));
                //nameValue.add(new BasicNameValuePair("phonenumber",phonenumbr));
                Log.d("request!", "starting");


                //Create the HTTP request
                HttpParams httpParameters = new BasicHttpParams();

                //Setup timeouts
                HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
                HttpConnectionParams.setSoTimeout(httpParameters, 15000);

                HttpClient httpclient = new DefaultHttpClient(httpParameters);
                HttpPost httppost = new HttpPost(busid_URL);
                httppost.setEntity(new UrlEncodedFormEntity(nameValue));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();

                String result = EntityUtils.toString(entity);
                Log.d("id attempt", result);


                // Create a JSON object from the request response
                jsonObject = new JSONObject(result);
                Log.d("id attempt", jsonObject.toString());
                //Retrieve the data from the JSON object
                success3 = jsonObject.getInt("success");
                msg = jsonObject.getString("message");
                if (success3 == 0) {
                    Log.d("login fail!", jsonObject.toString());

                } else {
                    busid = jsonObject.getString("busid");
                }

            } catch (Exception e) {
                Log.e("ClientServerDemo", "Error:", e);
                exception = e;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean valid) {
            Intent intent = new Intent(getApplicationContext(), Busseat.class);
            intent.putExtra("busname", bus.getSelectedItem().toString());
            intent.putExtra("route", route.getSelectedItem().toString());
            intent.putExtra("date", date.getSelectedItem().toString());
            intent.putExtra("time", time.getSelectedItem().toString());
            intent.putExtra("busid", busid);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            intent.putExtra("username", getIntent().getStringExtra("username"));
            intent.putExtra("phonenumber", getIntent().getStringExtra("phonenumber"));
            startActivity(intent);
            //pDialog.dismiss();
            if (exception != null) {
                Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        }


    }

}