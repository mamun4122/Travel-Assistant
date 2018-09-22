package com.dreamkillers.Travel_Assistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Mamun on 1/21/2015.
 */
public class Menu extends Activity implements View.OnClickListener {
    Button book, prev, customer, pay_bkash;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        book = (Button) findViewById(R.id.booking);
        prev = (Button) findViewById(R.id.prev_pur);
        customer = (Button) findViewById(R.id.care);
        pay_bkash = (Button) findViewById(R.id.pay_bkash);

        book.setOnClickListener(this);
        prev.setOnClickListener(this);
        customer.setOnClickListener(this);
        pay_bkash.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.booking:
                Log.d("userid", getIntent().getStringExtra("id").toString());
                //Toast.makeText(getApplicationContext(),getIntent().getStringExtra("id").toString(),Toast.LENGTH_SHORT);
                Intent intent = new Intent(getApplicationContext(), Booking.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                intent.putExtra("username", getIntent().getStringExtra("username"));
                intent.putExtra("phonenumber", getIntent().getStringExtra("phonenumber"));
                startActivity(intent);
                break;
            case R.id.prev_pur:
                Intent intent1 = new Intent(getApplicationContext(), Previous.class);
                intent1.putExtra("id", getIntent().getStringExtra("id"));
                intent1.putExtra("username", getIntent().getStringExtra("username"));
                intent1.putExtra("phonenumber", getIntent().getStringExtra("phonenumber"));
                startActivity(intent1);
                break;
            case R.id.care:
                //Toast.makeText(getApplicationContext(),"on the way",Toast.LENGTH_SHORT).show();
                Intent a = new Intent(getApplicationContext(), Dev_info.class);
                startActivity(a);
                break;
            case R.id.pay_bkash:
                Intent intent2 = new Intent(getApplicationContext(), Bkashpay.class);
                intent2.putExtra("username", getIntent().getStringExtra("username"));
                intent2.putExtra("id", getIntent().getStringExtra("id"));
                intent2.putExtra("phonenumber", getIntent().getStringExtra("phonenumber"));
                //EditText editText= (EditText) findViewById(R.id.pur_id);
                //editText.setEnabled(true);
                startActivity(intent2);
                break;
        }
    }
}
