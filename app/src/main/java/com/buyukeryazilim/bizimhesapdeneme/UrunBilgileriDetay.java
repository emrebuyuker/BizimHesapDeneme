package com.buyukeryazilim.bizimhesapdeneme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class UrunBilgileriDetay extends AppCompatActivity {

    TextView textViewurunName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_bilgileri_detay);

        textViewurunName = findViewById(R.id.textViewUrunName);

        Intent intent = getIntent();
        final String urunName = intent.getStringExtra("urunName");

        textViewurunName.setText(urunName);
    }
}
