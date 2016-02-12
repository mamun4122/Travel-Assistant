package com.dreamkillers.Travel_Assistant;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Zuel on 27-01-2015.
 */
public class Purchaseid extends Activity implements View.OnClickListener {

    private TextView purchase;
    private Button button1, button2;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchaseid);
        button1 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(this);
        //button2 = (Button) findViewById(R.id.button3);
        //button2.setOnClickListener(this);


        purchase = (TextView) findViewById(R.id.purchaseid);
        String msg = "Congratulations!\n You have succesfully booked ticket with purchase id:\n \" "
                + getIntent().getStringExtra("purchaseid") + " \"\n please save this for future reference\n" +
                "Thanks for using this app";
        purchase.setText(msg);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                intent.putExtra("id", getIntent().getStringExtra("id"));
                intent.putExtra("phonenumber", getIntent().getStringExtra("phonenumber"));
                startActivity(intent);
                break;

        }

    }


}
