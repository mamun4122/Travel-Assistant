package com.dreamkillers.Travel_Assistant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
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
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Zuel on 26-01-2015.
 */
public class Pay extends Activity {

    private static final String busupdate_URL = "http://192.168.111.1:80/mamun/busupdate.php";
    private static final String purchasedate_URL = "http://192.168.111.1:80/mamun/purchaseupdate.php";
    private static final String confirmation_URL = "http://192.168.111.1:80/mamun/confirmation.php";
    String msg;
    int success;
    String purchaseid;
    //EditText editText;
    Button book, pay;
    private ProgressDialog pDialog;
    private JSONObject jsonObject;
    private TextView username, phonenumber, busname, route, date, time, seat, rate, cost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_);


        username = (TextView) findViewById(R.id.t_n);
        phonenumber = (TextView) findViewById(R.id.t_p);
        busname = (TextView) findViewById(R.id.t_b);
        route = (TextView) findViewById(R.id.t_rout);
        date = (TextView) findViewById(R.id.t_d);
        time = (TextView) findViewById(R.id.t_t);
        seat = (TextView) findViewById(R.id.t_s);
        rate = (TextView) findViewById(R.id.t_rate);
        cost = (TextView) findViewById(R.id.t_c);


        username.setText(getIntent().getStringExtra("username"));
        phonenumber.setText(getIntent().getStringExtra("phonenumber"));
        busname.setText(getIntent().getStringExtra("busname"));
        route.setText(getIntent().getStringExtra("route"));
        date.setText(getIntent().getStringExtra("date"));
        time.setText(getIntent().getStringExtra("time"));
        seat.setText(getIntent().getStringExtra("seats"));
        rate.setText("400");
        int x = getIntent().getIntExtra("seatcount", 0);
        int cst = 400 * x;
        String seatcost = Integer.toString(cst);
        cost.setText(seatcost);

        //editText= (EditText) findViewById(R.id.pur_id);

        //pay = (Button) findViewById(R.id.pay);
        book = (Button) findViewById(R.id.book);


        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DoPST().execute();
                new DoPOST().execute();
                new mDoPOST().execute();
            }
        });


    }


    private class DoPST extends AsyncTask<String, Void, Boolean> {

        private Context mContext = null;
        private Exception exception = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Pay.this);
            pDialog.setMessage("updating to server...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... arg0) {

            try {

                //Setup the parameters
                ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
                nameValue.add(new BasicNameValuePair("seatupdate", getIntent().getStringExtra("query")));
                //nameValuePairs.add(new BasicNameValuePair("FirstNameToSearch", strNameToSearch));
                //Add more parameters as necessary
                Log.d("request!", "starting");


                //Create the HTTP request
                HttpParams httpParameters = new BasicHttpParams();

                //Setup timeouts
                HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
                HttpConnectionParams.setSoTimeout(httpParameters, 15000);

                HttpClient httpclient = new DefaultHttpClient(httpParameters);
                HttpPost httppost = new HttpPost(busupdate_URL);
                httppost.setEntity(new UrlEncodedFormEntity(nameValue));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();

                String result = EntityUtils.toString(entity);
                Log.d("reg attempt", result);


                // Create a JSON object from the request response
                jsonObject = new JSONObject(result);
                Log.d("update attempt", jsonObject.toString());
                //Retrieve the data from the JSON object
                success = jsonObject.getInt("success");
                msg = jsonObject.getString("message");
                if (success == 0) {
                    Log.d("login fail!", jsonObject.toString());

                } else {

                    Log.d("login Successful!", jsonObject.toString());
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
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

            pDialog.dismiss();
            if (exception != null) {
                Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        }


    }


    private class DoPOST extends AsyncTask<String, Void, Boolean> {

        private Context mContext = null;
        private Exception exception = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Pay.this);
            pDialog.setMessage("updating to server...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... arg0) {

            try {

                //Setup the parameters
                ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
                nameValue.add(new BasicNameValuePair("seat", getIntent().getStringExtra("seats")));
                nameValue.add(new BasicNameValuePair("busname", getIntent().getStringExtra("busname")));
                nameValue.add(new BasicNameValuePair("route", getIntent().getStringExtra("route")));
                nameValue.add(new BasicNameValuePair("date", getIntent().getStringExtra("date")));
                nameValue.add(new BasicNameValuePair("time", getIntent().getStringExtra("time")));
                nameValue.add(new BasicNameValuePair("userid", getIntent().getStringExtra("id")));


                //nameValuePairs.add(new BasicNameValuePair("FirstNameToSearch", strNameToSearch));
                //Add more parameters as necessary
                Log.d("request!", "starting");


                //Create the HTTP request
                HttpParams httpParameters = new BasicHttpParams();

                //Setup timeouts
                HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
                HttpConnectionParams.setSoTimeout(httpParameters, 15000);

                HttpClient httpclient = new DefaultHttpClient(httpParameters);
                HttpPost httppost = new HttpPost(purchasedate_URL);
                httppost.setEntity(new UrlEncodedFormEntity(nameValue));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();

                String result = EntityUtils.toString(entity);
                Log.d("reg attempt", result);


                // Create a JSON object from the request response
                jsonObject = new JSONObject(result);
                Log.d("update attempt", jsonObject.toString());
                //Retrieve the data from the JSON object
                success = jsonObject.getInt("success");
                msg = jsonObject.getString("message");
                if (success == 0) {
                    Log.d("login fail!", jsonObject.toString());

                } else {
                    purchaseid = jsonObject.getString("purchaseid");

                    /*Intent intent=new Intent(getApplicationContext(),Purchaseid.class);
                    intent.putExtra("purchaseid",purchaseid);
                    intent.putExtra("username",getIntent().getStringExtra("username"));
                    intent.putExtra("id",getIntent().getStringExtra("id"));
                    intent.putExtra("phonenumber",getIntent().getStringExtra("phonenumber") );
                    startActivity(intent);*/
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
            pDialog.dismiss();
            if (exception != null) {
                Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        }


    }

    private class mDoPOST extends AsyncTask<String, Void, Boolean> {

        private Context mContext = null;
        private Exception exception = null;



        /*@Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Pay.this);
            pDialog.setMessage("updating to server...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }*/

        @Override
        protected Boolean doInBackground(String... arg0) {

            try {

                //Setup the parameters
                ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
                nameValue.add(new BasicNameValuePair("userid", getIntent().getStringExtra("id")));
                nameValue.add(new BasicNameValuePair("purchaseid", purchaseid));
                nameValue.add(new BasicNameValuePair("bkash", "NULL"));
                nameValue.add(new BasicNameValuePair("status", "0"));


                //nameValuePairs.add(new BasicNameValuePair("FirstNameToSearch", strNameToSearch));
                //Add more parameters as necessary
                Log.d("request!", "starting");


                //Create the HTTP request
                HttpParams httpParameters = new BasicHttpParams();

                //Setup timeouts
                HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
                HttpConnectionParams.setSoTimeout(httpParameters, 15000);

                HttpClient httpclient = new DefaultHttpClient(httpParameters);
                HttpPost httppost = new HttpPost(confirmation_URL);
                httppost.setEntity(new UrlEncodedFormEntity(nameValue));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();

                String result = EntityUtils.toString(entity);
                Log.d("confirm attempt", result);


                // Create a JSON object from the request response
                jsonObject = new JSONObject(result);
                Log.d("update attempt", jsonObject.toString());
                //Retrieve the data from the JSON object
                success = jsonObject.getInt("success");
                msg = jsonObject.getString("message");
                if (success == 0) {
                    Log.d("login fail!", jsonObject.toString());

                } else {
                    //purchaseid=jsonObject.getString("purchaseid");
                    Intent intent = new Intent(getApplicationContext(), Purchaseid.class);
                    intent.putExtra("purchaseid", purchaseid);
                    intent.putExtra("username", getIntent().getStringExtra("username"));
                    intent.putExtra("id", getIntent().getStringExtra("id"));
                    intent.putExtra("phonenumber", getIntent().getStringExtra("phonenumber"));
                    startActivity(intent);
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
            // pDialog.dismiss();
            if (exception != null) {
                Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        }


    }
}