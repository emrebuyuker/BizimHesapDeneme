package com.buyukeryazilim.bizimhesapdeneme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MusteriBilgileriDetay extends AppCompatActivity {

    TextView textViewMusteriName;
    TextView textViTelefonNumarasi;
    TextView textVMail;
    TextView textVAdres;
    TextView textVToplamCiro;
    TextView textVBorc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musteri_bilgileri_detay);

        textViewMusteriName = findViewById(R.id.textViewMusteriName);
        textViTelefonNumarasi = findViewById(R.id.textViewTelefonNumarasi);
        textVMail = findViewById(R.id.textViewMail);
        textVAdres = findViewById(R.id.textViewAdres);
        textVToplamCiro = findViewById(R.id.textViewToplamCiro);
        textVBorc = findViewById(R.id.textViewBorc);

        Intent intent = getIntent();
        final String musteriName = intent.getStringExtra("musteriName");
        final String musteriAdres = intent.getStringExtra("musteriAdres");
        final String musteriMail = intent.getStringExtra("musteriMail");
        final String musteriTelefon = intent.getStringExtra("musteriTelefon");

        textViewMusteriName.setText(musteriName);
        textViTelefonNumarasi.setText(musteriTelefon);
        textVMail.setText(musteriMail);
        textVAdres.setText(musteriAdres);
    }
}
