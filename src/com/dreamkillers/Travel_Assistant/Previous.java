package com.dreamkillers.Travel_Assistant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by Mamun on 27-01-2015.
 */
public class Previous extends Activity {
    private static final String purchase_URL = "http://192.168.111.1:80/mamun/purchase.php";
    String msg;
    int success;
    EditText purchasecode;
    Button get_data;
    private ProgressDialog pDialog;
    private JSONObject jsonObject;
    private TextView username, phonenumber, status;
    private TextView busname, route, date, time, seat;
    private String busnme, rte, dte, tme, sat;
    private String id;
    private int flag;
    String status_msg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previous);

        id = getIntent().getStringExtra("id");
        purchasecode = (EditText) findViewById(R.id.editText2);
        get_data = (Button) findViewById(R.id.detail);

        username = (TextView) findViewById(R.id.prev_name);
        phonenumber = (TextView) findViewById(R.id.prev_phone);

        username.setText(getIntent().getStringExtra("username"));
        phonenumber.setText(getIntent().getStringExtra("phonenumber"));
        busname = (TextView) findViewById(R.id.prev_bus);
        route = (TextView) findViewById(R.id.prev_route);
        date = (TextView) findViewById(R.id.prev_date);
        time = (TextView) findViewById(R.id.prev_time);
        seat = (TextView) findViewById(R.id.prev_seat);
        status = (TextView) findViewById(R.id.p_status);

        get_data.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DoPOST().execute();
                busname.setText(busnme);
                route.setText(rte);
                date.setText(dte);
                time.setText(tme);
                seat.setText(sat);
                if (flag == 1) status_msg = "Payment Successful";
                if (flag == 2) status_msg = "Payment in Process";
                if (flag == 0) status_msg = "Only Booked";
                if (flag == 3) status_msg = "No purchase found";
                status.setText(status_msg);
            }
        });


    }

    private class DoPOST extends AsyncTask<String, Void, Boolean> {

        private Context mContext = null;
        private Exception exception = null;
        //private String username,password;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Previous.this);
            pDialog.setMessage("Fetching Data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... arg0) {

            try {


                //Setup the parameters
                ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
                //nameValuePairs.add(new BasicNameValuePair("FirstNameToSearch", strNameToSearch));
                //Add more parameters as necessary
                nameValue.add(new BasicNameValuePair("userid", id));
                nameValue.add(new BasicNameValuePair("purchaseid", purchasecode.getText().toString()));
                //nameValue.add(new BasicNameValuePair("phonenumber",phonenumbr));
                Log.d("login request!", "starting");
                Log.d("vua", String.valueOf(nameValue.get(0)));
                Log.d("vua2", String.valueOf(nameValue.get(1)));

                //Create the HTTP request
                HttpParams httpParameters = new BasicHttpParams();

                //Setup timeouts
                HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
                HttpConnectionParams.setSoTimeout(httpParameters, 15000);

                HttpClient httpclient = new DefaultHttpClient(httpParameters);
                HttpPost httppost = new HttpPost(purchase_URL);
                httppost.setEntity(new UrlEncodedFormEntity(nameValue));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();

                String result = EntityUtils.toString(entity);
                Log.d("login attempt", result);


                // Create a JSON object from the request response
                jsonObject = new JSONObject(result);
                Log.d("Login attempt", jsonObject.toString());
                //Retrieve the data from the JSON object
                success = jsonObject.getInt("success");
                msg = jsonObject.getString("message");
                Log.d("login Successful!", jsonObject.toString());
                busnme = jsonObject.getString("busname");
                rte = jsonObject.getString("route");
                dte = jsonObject.getString("date");
                tme = jsonObject.getString("time");
                sat = jsonObject.getString("seat");
                flag = jsonObject.getInt("status");


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


}