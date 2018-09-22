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
 * Created by Mamun on 03-Feb-15.
 */
public class Bkashpay extends Activity {

    private static final String confirmation_URL = "http://192.168.111.1:80/mamun/confirmation.php";
    private static final String confirmpay_URL = "http://192.168.111.1:80/mamun/confirmpay.php";
    String msg;
    int success;
    String purchaseid;
    private Button purchase, back_menu;
    private EditText pur_code, bkash_code;
    private TextView text_msg;
    private ProgressDialog pDialog;
    private JSONObject jsonObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.extra);

        purchase = (Button) findViewById(R.id.late_pay);
        back_menu = (Button) findViewById(R.id.menu_back);
        pur_code = (EditText) findViewById(R.id.pur_id);
        bkash_code = (EditText) findViewById(R.id.bkash_id);
        text_msg = (TextView) findViewById(R.id.bkashmsg);

        // pur_code.setText("mam");
        //pur_code.setEnabled(false);

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new mDoPOST().execute();
                text_msg.setText(msg);
            }
        });

        back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                intent.putExtra("id", getIntent().getStringExtra("id"));
                intent.putExtra("phonenumber", getIntent().getStringExtra("phonenumber"));
                startActivity(intent);
            }
        });

    }

    private class mDoPOST extends AsyncTask<String, Void, Boolean> {

        private Context mContext = null;
        private Exception exception = null;
        // private TextView text_msg= (TextView) findViewById(R.id.bkashmsg);


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
                nameValue.add(new BasicNameValuePair("purchaseid", pur_code.getText().toString()));
                nameValue.add(new BasicNameValuePair("bkash", bkash_code.getText().toString()));
                nameValue.add(new BasicNameValuePair("status", "2"));


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
                    //Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
                    //text_msg.setText(msg);


                } else {
                    //purchaseid=jsonObject.getString("purchaseid");
                    String msg = "Congratulations!\n You have succesfully purchased ticket with purchase id:\n \" "
                            + getIntent().getStringExtra("purchaseid");

                    new DoPOST().execute();
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

    private class DoPOST extends AsyncTask<String, Void, Boolean> {

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
                nameValue.add(new BasicNameValuePair("purchaseid", pur_code.getText().toString()));
                nameValue.add(new BasicNameValuePair("bkash", bkash_code.getText().toString()));
                nameValue.add(new BasicNameValuePair("status", "2"));


                //nameValuePairs.add(new BasicNameValuePair("FirstNameToSearch", strNameToSearch));
                //Add more parameters as necessary
                Log.d("request!", "starting");


                //Create the HTTP request
                HttpParams httpParameters = new BasicHttpParams();

                //Setup timeouts
                HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
                HttpConnectionParams.setSoTimeout(httpParameters, 15000);

                HttpClient httpclient = new DefaultHttpClient(httpParameters);
                HttpPost httppost = new HttpPost(confirmpay_URL);
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
                    //Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
                    //text_msg.setText(msg);


                } else {
                    //purchaseid=jsonObject.getString("purchaseid");

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