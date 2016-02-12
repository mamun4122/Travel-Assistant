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
 * Created by MASUD on 1/21/2015.
 */
public class Register extends Activity {

    private static final String LOGIN_URL = "http://192.168.111.1:80/mamun/register.php";
    Button register;
    String msg;
    int success;
    private EditText user, pass, phonenmbr;
    private ProgressDialog pDialog;
    private String id;
    private JSONObject jsonObject;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        user = (EditText) findViewById(R.id.usernameregister);
        pass = (EditText) findViewById(R.id.passwordregister);
        phonenmbr = (EditText) findViewById(R.id.phonenmbr);
        register = (Button) findViewById(R.id.registerbutton);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DoPOST().execute();
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
            pDialog = new ProgressDialog(Register.this);
            pDialog.setMessage("Attempting Register...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... arg0) {

            try {

                String username = user.getText().toString();
                String password = pass.getText().toString();
                String phonenumbr = phonenmbr.getText().toString();
                //Log.d("pass", password);


                //Setup the parameters
                ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
                //nameValuePairs.add(new BasicNameValuePair("FirstNameToSearch", strNameToSearch));
                //Add more parameters as necessary
                nameValue.add(new BasicNameValuePair("username", username));
                nameValue.add(new BasicNameValuePair("password", password));
                nameValue.add(new BasicNameValuePair("phonenumber", phonenumbr));
                Log.d("registration request!", "starting");


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
                Log.d("reg attempt", result);


                // Create a JSON object from the request response
                jsonObject = new JSONObject(result);
                Log.d("Reg attempt", jsonObject.toString());
                //Retrieve the data from the JSON object
                success = jsonObject.getInt("success");
                msg = jsonObject.getString("message");
                if (success == 0) {
                    Log.d("Registration fail!", jsonObject.toString());

                } else {
                    Log.d("Registration Successful!", jsonObject.toString());
                    id = jsonObject.getString("id");
                    Intent intent = new Intent(getApplicationContext(), Menu.class);
                    intent.putExtra("id", id);
                    intent.putExtra("username", username);
                    intent.putExtra("phonenumber", phonenumbr);
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
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            pDialog.dismiss();
            if (exception != null) {
                Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        }


    }


}
