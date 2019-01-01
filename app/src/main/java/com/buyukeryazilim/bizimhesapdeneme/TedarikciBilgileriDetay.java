package com.buyukeryazilim.bizimhesapdeneme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TedarikciBilgileriDetay extends AppCompatActivity {

    TextView textViewMusteriName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tedarikci_bilgileri_detay);

        textViewMusteriName = findViewById(R.id.textViewMusteriName);

        Intent intent = getIntent();
        final String tedarikciName = intent.getStringExtra("tedarikciName");

        textViewMusteriName.setText(tedarikciName);
    }
}
