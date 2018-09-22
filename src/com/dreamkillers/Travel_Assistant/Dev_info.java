package com.dreamkillers.Travel_Assistant;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Mamun on 26-01-2015.
 */
public class Dev_info extends Activity {
    Button m, s, k, p, sc, hc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dev_lay);
        m = (Button) findViewById(R.id.m);
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Intent.ACTION_CALL);
                a.setData(Uri.parse("tel: 01675640900"));
                startActivity(a);
            }
        });
        k = (Button) findViewById(R.id.k);
        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Intent.ACTION_CALL);
                a.setData(Uri.parse("tel: 01711368755"));
                startActivity(a);
            }
        });


        p = (Button) findViewById(R.id.p);
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Intent.ACTION_CALL);
                a.setData(Uri.parse("tel: 01521200856"));
                startActivity(a);
            }
        });
        s = (Button) findViewById(R.id.p);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Intent.ACTION_CALL);
                a.setData(Uri.parse("tel: 01521200856"));
                startActivity(a);
            }
        });
        sc = (Button) findViewById(R.id.s_c);
        hc = (Button) findViewById(R.id.h_c);
        /*

        sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Intent.ACTION_CALL);
                a.setData(Uri.parse("tel:XXXXXXXXXXX"));
                startActivity(a);
            }
        });

        hc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(Intent.ACTION_CALL);
                a.setData(Uri.parse("tel:XXXXXXXXXXX"));
                startActivity(a);
            }
        });
        */
    }
}