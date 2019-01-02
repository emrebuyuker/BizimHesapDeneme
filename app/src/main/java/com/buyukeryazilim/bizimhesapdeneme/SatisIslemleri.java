package com.buyukeryazilim.bizimhesapdeneme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class SatisIslemleri extends AppCompatActivity {

    TextView satısYapılanKisi;
    TextView islemTarihi;
    TextView textVToplam;

    String musteriName;
    String isim;
    String adet;
    String netTutar;
    String kdv;
    String toplam;

    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satis_islemleri);

        satısYapılanKisi = findViewById(R.id.textViewSatısYapılanKisi);
        islemTarihi = findViewById(R.id.textViewIslemTarihi);
        textVToplam = findViewById(R.id.textViewToplam);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        Intent intent = getIntent();
        musteriName = intent.getStringExtra("musteriName");
        isim = intent.getStringExtra("isim");
        adet = intent.getStringExtra("adet");
        netTutar = intent.getStringExtra("netTutar");
        kdv = intent.getStringExtra("kdv");
        toplam = intent.getStringExtra("toplam");


        textVToplam.setText(toplam);
        satısYapılanKisi.setText(musteriName);
        Date tarih = new Date();
        SimpleDateFormat bugun = new SimpleDateFormat("dd/MM/yyyy");
        islemTarihi.setText(bugun.format(tarih));
    }

    public void urunEkleButtonClick(View view) {

        String mod = "satıs";
        Intent intent = new Intent(getApplicationContext(), Urunler.class);
        intent.putExtra("mod",mod);
        intent.putExtra("musteriName",musteriName);
        startActivity(intent);

    }

    public void satisYapButtonClick(View view) {



        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        String tarih = islemTarihi.getText().toString();

        myRef.child("Satış").child(uuidString).child("islemTarihi").setValue(tarih);
        myRef.child("Satış").child(uuidString).child("musteriName").setValue(musteriName);
        myRef.child("Satış").child(uuidString).child("isim").setValue(isim);
        myRef.child("Satış").child(uuidString).child("netTutar").setValue(netTutar);
        myRef.child("Satış").child(uuidString).child("kdv").setValue(kdv);
        myRef.child("Satış").child(uuidString).child("toplam").setValue(toplam);

        Toast.makeText(getApplicationContext(),"Satış İşlemi Başarılı",Toast.LENGTH_LONG).show();

        String isim = "";
        String adet = "0";
        String netTutar = "";
        String kdv = "";
        String toplam = "0";
        String musteriName=" ";
        Intent intent = new Intent(getApplicationContext(), SatisIslemleri.class);

        intent.putExtra("musteriName", musteriName);
        intent.putExtra("isim", isim);
        intent.putExtra("adet", adet);
        intent.putExtra("netTutar", netTutar);
        intent.putExtra("kdv", kdv);
        intent.putExtra("toplam", toplam);

        startActivity(intent);

    }


}
