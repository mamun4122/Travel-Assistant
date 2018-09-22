package com.dreamkillers.Travel_Assistant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
 * Created by Mamun on 1/22/2015.
 */
public class Busseat extends Activity implements View.OnClickListener {


    private static final String busseat_URL = "http://192.168.111.1:80/mamun/busseat.php";
    String msg;
    int success;
    Button buttons[] = new Button[22];
    boolean mark[] = new boolean[22];
    boolean seat_select[] = new boolean[22];
    String seats[] = {"",
            "A1", "A2", "A3", "A4",
            "B1", "B2", "B3", "B4",
            "C1", "C2", "C3", "C4",
            "D1", "D2", "D3", "D4",
            "E1", "E2", "E3", "E4"};
    String query, seats_selected;
    int cnt;
    private ProgressDialog pDialog;
    private JSONObject jsonObject;
    private String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        cnt = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat);

        for (int i = 0; i < 22; i++) mark[i] = false;
        for (int i = 0; i < 22; i++) seat_select[i] = false;

        buttons[1] = (Button) findViewById(R.id.A1);
        buttons[2] = (Button) findViewById(R.id.A2);
        buttons[3] = (Button) findViewById(R.id.A3);
        buttons[4] = (Button) findViewById(R.id.A4);
        buttons[5] = (Button) findViewById(R.id.B1);
        buttons[6] = (Button) findViewById(R.id.B2);
        buttons[7] = (Button) findViewById(R.id.B3);
        buttons[8] = (Button) findViewById(R.id.B4);
        buttons[9] = (Button) findViewById(R.id.C1);
        buttons[10] = (Button) findViewById(R.id.C2);
        buttons[11] = (Button) findViewById(R.id.C3);
        buttons[12] = (Button) findViewById(R.id.C4);
        buttons[13] = (Button) findViewById(R.id.D1);
        buttons[14] = (Button) findViewById(R.id.D2);
        buttons[15] = (Button) findViewById(R.id.D3);
        buttons[16] = (Button) findViewById(R.id.D4);
        buttons[17] = (Button) findViewById(R.id.E1);
        buttons[18] = (Button) findViewById(R.id.E2);
        buttons[19] = (Button) findViewById(R.id.E3);
        buttons[20] = (Button) findViewById(R.id.E4);


        Button next_butn = (Button) findViewById(R.id.seatbutton);
        next_butn.setOnClickListener(this);

        id = getIntent().getStringExtra("busid");
        Log.d("busid", id);
        //Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();

        new DoPOST().execute();


        for (int i = 1; i <= 20; i++)
            buttons[i].setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.seatbutton:
                //Log.d("clicked","dsajda");
                query = " UPDATE `busseat` SET ";
                seats_selected = "";
                boolean ok = false;
                for (int i = 1; i <= 20; i++) {
                    if (seat_select[i] && ok) {
                        query += ",`" + seats[i] + "` = 1";
                        seats_selected += " , " + seats[i];
                        cnt++;
                    } else if (seat_select[i]) {
                        ok = true;
                        query += "`" + seats[i] + "` = 1";
                        cnt++;
                        seats_selected += seats[i];
                    }
                }
                query += " WHERE `id` LIKE " + id;
                if (ok) {
                    Intent intent = new Intent(getApplicationContext(), Pay.class);
                    intent.putExtra("query", query);
                    intent.putExtra("seats", seats_selected);
                    intent.putExtra("seatcount", cnt);
                    intent.putExtra("busname", getIntent().getStringExtra("busname"));
                    intent.putExtra("route", getIntent().getStringExtra("route"));
                    intent.putExtra("date", getIntent().getStringExtra("date"));
                    intent.putExtra("time", getIntent().getStringExtra("time"));
                    intent.putExtra("busid", getIntent().getStringExtra("busid"));
                    intent.putExtra("id", getIntent().getStringExtra("id"));
                    intent.putExtra("username", getIntent().getStringExtra("username"));
                    intent.putExtra("phonenumber", getIntent().getStringExtra("phonenumber"));

                    startActivity(intent);


                } else {
                    Toast.makeText(getApplicationContext(), "Select a seat", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Log.d("checking", "succesful 1");
                Button btn = (Button) findViewById(v.getId());
                int indx = 1;
                for (int i = 1; i <= 20; i++) {
                    if (btn == buttons[i]) {
                        Log.d("checking", "succesful 2");
                        indx = i;
                        break;
                    }
                }
                if (seat_select[indx] == true) {
                    Log.d("checking", "succesful 3");
                    buttons[indx].setBackgroundColor(Color.DKGRAY);
                    seat_select[indx] = false;
                } else {
                    Log.d("checking", "succesful 4");
                    buttons[indx].setBackgroundColor(Color.GREEN);
                    seat_select[indx] = true;
                }
                break;
        }
    }


    private class DoPOST extends AsyncTask<String, Void, Boolean> {

        private Context mContext = null;
        private Exception exception = null;
        //private String username,password;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Busseat.this);
            pDialog.setMessage("Wait for a momment...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... arg0) {

            try {

                //Setup the parameters
                ArrayList<NameValuePair> nameValue = new ArrayList<NameValuePair>();
                nameValue.add(new BasicNameValuePair("busid", id));
                //nameValuePairs.add(new BasicNameValuePair("FirstNameToSearch", strNameToSearch));
                //Add more parameters as necessary
                Log.d("request!", "starting");


                //Create the HTTP request
                HttpParams httpParameters = new BasicHttpParams();

                //Setup timeouts
                HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
                HttpConnectionParams.setSoTimeout(httpParameters, 15000);

                HttpClient httpclient = new DefaultHttpClient(httpParameters);
                HttpPost httppost = new HttpPost(busseat_URL);
                httppost.setEntity(new UrlEncodedFormEntity(nameValue));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();

                String result = EntityUtils.toString(entity);
                Log.d("reg attempt", result);


                // Create a JSON object from the request response
                jsonObject = new JSONObject(result);
                Log.d("Login attempt", jsonObject.toString());
                //Retrieve the data from the JSON object
                success = jsonObject.getInt("success");
                msg = jsonObject.getString("message");
                if (success == 0) {
                    Log.d("login fail!", jsonObject.toString());

                } else {
                    for (int i = 1; i <= 20; i++) {
                        //String k=Integer.toString(i);
                        int x = jsonObject.getInt(Integer.toString(i));
                        if (x == 1)
                            mark[i] = true;
                        Log.d("seat value", String.valueOf(x));
                    }
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
            for (int i = 1; i <= 20; i++) {
                if (mark[i]) {
                    buttons[i].setBackgroundColor(Color.RED);
                    // buttons[i].setBackgroundColor(getResources().getColor(R.color.Red));
                    buttons[i].setEnabled(false);
                } else {
                    buttons[i].setBackgroundColor(Color.DKGRAY);
                }
            }
            pDialog.dismiss();
            if (exception != null) {
                Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        }


    }


}