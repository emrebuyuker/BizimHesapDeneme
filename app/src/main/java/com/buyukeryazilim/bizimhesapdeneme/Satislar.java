package com.buyukeryazilim.bizimhesapdeneme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Satislar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satislar);
    }

    public void satisIcinMusteriSecmeButtonClick(View view) {

        String mod = "satış";
        Intent intent = new Intent(getApplicationContext(), Musteriler.class);
        intent.putExtra("mod",mod);
        startActivity(intent);

    }
}


