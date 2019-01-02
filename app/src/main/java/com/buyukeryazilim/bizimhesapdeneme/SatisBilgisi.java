package com.buyukeryazilim.bizimhesapdeneme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SatisBilgisi extends AppCompatActivity {

    String musteriName;
    String isim;
    String adet;
    String netTutar;
    String kdv;
    String toplam;
    String islemTarihi;

    TextView textVSatisYapilanKisi;
    TextView textVIslemTarihi;
    TextView textVSatilanUrun;
    TextView textVUrunAdeti;
    TextView textVNetTutar;
    TextView textVKDV;
    TextView textVToplam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satis_bilgisi);

        Intent intent = getIntent();
        musteriName = intent.getStringExtra("musteriName");
        isim = intent.getStringExtra("isim");
        adet = intent.getStringExtra("adet");
        netTutar = intent.getStringExtra("netTutar");
        kdv = intent.getStringExtra("kdv");
        toplam = intent.getStringExtra("toplam");
        islemTarihi = intent.getStringExtra("islemTarihi");

        textVSatisYapilanKisi = findViewById(R.id.textViewSatisYapilanKisi);
        textVIslemTarihi = findViewById(R.id.textViewIslemTarihi);
        textVSatilanUrun = findViewById(R.id.textViewSatilanUrun);
        textVUrunAdeti = findViewById(R.id.textViewUrunAdeti);
        textVNetTutar = findViewById(R.id.textViewNetTutar);
        textVKDV = findViewById(R.id.textViewKDV);
        textVToplam = findViewById(R.id.textViewToplam);

        textVSatisYapilanKisi.setText(musteriName);
        textVIslemTarihi.setText(islemTarihi);
        textVSatilanUrun.setText(isim);
        textVUrunAdeti.setText(adet);
        textVNetTutar.setText(netTutar);
        textVKDV.setText(kdv);
        textVToplam.setText(toplam);
    }
}
