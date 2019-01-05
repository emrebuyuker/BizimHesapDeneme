package com.buyukeryazilim.bizimhesapdeneme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class UrunBilgileriDetay extends AppCompatActivity {

    TextView textVUrunName;
    TextView textVSatisFiyat;
    TextView textVSatisKDVOrani;
    TextView textVAlisFiyati;
    TextView textVAlisKDVOrani;
    TextView textVUrunTipi;
    TextView textVUrunAdeti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_bilgileri_detay);

        textVUrunName = findViewById(R.id.textViewUrunName);
        textVSatisFiyat = findViewById(R.id.textViewSatisFiyati);
        textVSatisKDVOrani = findViewById(R.id.textViewSatisKDVOrani);
        textVAlisFiyati = findViewById(R.id.textViewAlısFiyati);
        textVAlisKDVOrani = findViewById(R.id.textViewAlısKDVOrani);
        textVUrunTipi = findViewById(R.id.textViewUrunTipi);
        textVUrunAdeti = findViewById(R.id.textViewToplamStok);

        Intent intent = getIntent();

        final String urunName = intent.getStringExtra("urunName");
        final String satisFiyat = intent.getStringExtra("satisFiyat");
        final String satisKDVOrani = intent.getStringExtra("satisKDVOranı");
        final String alisFiyati = intent.getStringExtra("alisFiyati");
        final String alisKDVOrani = intent.getStringExtra("alisKDVOranı");
        final String urunTipi = intent.getStringExtra("urunTipi");
        final String urunAdeti = intent.getStringExtra("urunAdeti");

        textVUrunName.setText(urunName);
        textVSatisFiyat.setText(satisFiyat);
        textVSatisKDVOrani.setText(satisKDVOrani);
        textVAlisFiyati.setText(alisFiyati);
        textVAlisKDVOrani.setText(alisKDVOrani);
        textVUrunTipi.setText(urunTipi);
        textVUrunAdeti.setText(urunAdeti);
    }
}
