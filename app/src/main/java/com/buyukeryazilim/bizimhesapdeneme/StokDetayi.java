package com.buyukeryazilim.bizimhesapdeneme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class StokDetayi extends AppCompatActivity {

    TextView textVUrunIsmi;
    TextView textVUrunAdeti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok_detayi);

        textVUrunIsmi = findViewById(R.id.textViewUrunIsmi);
        textVUrunAdeti = findViewById(R.id.textViewUrunAdeti);

        Intent intent = getIntent();
        final String urunIsmi = intent.getStringExtra("urunIsmi");
        final String urunAdeti = intent.getStringExtra("urunAdeti");

        textVUrunIsmi.setText(urunIsmi);
        textVUrunAdeti.setText(urunAdeti);
    }
}
