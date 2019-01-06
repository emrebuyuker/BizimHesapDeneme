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

public class AlisIslemleri extends AppCompatActivity {

    TextView islemTarihi;
    TextView textVSatısYapılanKisi;
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
        setContentView(R.layout.activity_alis_islemleri);

        islemTarihi = findViewById(R.id.textViewIslemTarihi);
        textVSatısYapılanKisi = findViewById(R.id.textViewSatısYapılanKisi);
        textVToplam = findViewById(R.id.textViewToplam);

        Date tarih = new Date();
        SimpleDateFormat bugun = new SimpleDateFormat("dd/MM/yyyy");
        islemTarihi.setText(bugun.format(tarih));

        Intent intent = getIntent();
        musteriName = intent.getStringExtra("tedarikciName");
        isim = intent.getStringExtra("isim");
        adet = intent.getStringExtra("adet");
        netTutar = intent.getStringExtra("netTutar");
        kdv = intent.getStringExtra("kdv");
        toplam = intent.getStringExtra("toplam");


        textVSatısYapılanKisi.setText(musteriName);
        textVToplam.setText(toplam);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

    public void urunEkleButtonClickk(View view) {

        String mod = "alış";
        Intent intent = new Intent(getApplicationContext(), Urunler.class);
        intent.putExtra("mod",mod);
        intent.putExtra("musteriName",musteriName);
        startActivity(intent);

    }

    public void alisYapButtonClick(View view) {



        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        String tarih = islemTarihi.getText().toString();

        myRef.child("Alış").child(uuidString).child("islemTarihi").setValue(tarih);
        myRef.child("Alış").child(uuidString).child("adet").setValue(adet);
        myRef.child("Alış").child(uuidString).child("musteriName").setValue(musteriName);
        myRef.child("Alış").child(uuidString).child("isim").setValue(isim);
        myRef.child("Alış").child(uuidString).child("netTutar").setValue(netTutar);
        myRef.child("Alış").child(uuidString).child("kdv").setValue(kdv);
        myRef.child("Alış").child(uuidString).child("toplam").setValue(toplam);

        /*int borçInt = Integer.parseInt(borçFB.get(0));
        int toplamciroInt = Integer.parseInt(toplamciroFB.get(0));
        int toplamInt = Integer.parseInt(toplam);

        myRef.child("Kasa").child(musteriName).child("borç").setValue(Integer.toString(borçInt+toplamInt));
        myRef.child("Kasa").child(musteriName).child("toplamciro").setValue(Integer.toString(toplamciroInt+toplamInt));*/

        Toast.makeText(getApplicationContext(),"Alış İşlemi Başarılı",Toast.LENGTH_LONG).show();

        /*String isim = "";
        String adet = "0";
        String netTutar = "";
        String kdv = "";
        String toplam = "0";
        String musteriName=" ";*/
        Intent intent = new Intent(getApplicationContext(), Alislar.class);

        /*intent.putExtra("musteriName", musteriName);
        intent.putExtra("isim", isim);
        intent.putExtra("adet", adet);
        intent.putExtra("netTutar", netTutar);
        intent.putExtra("kdv", kdv);
        intent.putExtra("toplam", toplam);*/

        startActivity(intent);

    }
}
