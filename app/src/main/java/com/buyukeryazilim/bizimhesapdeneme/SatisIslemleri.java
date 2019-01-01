package com.buyukeryazilim.bizimhesapdeneme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SatisIslemleri extends AppCompatActivity {

    TextView satısYapılanKisi;
    TextView islemTarihi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satis_islemleri);

        satısYapılanKisi = findViewById(R.id.textViewSatısYapılanKisi);
        islemTarihi = findViewById(R.id.textViewIslemTarihi);

        Intent intent = getIntent();
        final String musteriName = intent.getStringExtra("musteriName");

        satısYapılanKisi.setText(musteriName);
        Date tarih = new Date();
        SimpleDateFormat bugun = new SimpleDateFormat("dd/MM/yyyy");
        islemTarihi.setText(bugun.format(tarih));
    }

    public void urunEkleButtonClick(View view) {

        String mod = "satıs";
        Intent intent = new Intent(getApplicationContext(), Urunler.class);
        intent.putExtra("mod",mod);
        startActivity(intent);

    }


}
