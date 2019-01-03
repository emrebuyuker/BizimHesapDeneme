package com.buyukeryazilim.bizimhesapdeneme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

    ArrayList<String> borçFB;
    ArrayList<String> toplamciroFB;

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

        borçFB = new ArrayList<String>();
        toplamciroFB = new ArrayList<String>();

        getDataFirebase();
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
        myRef.child("Satış").child(uuidString).child("adet").setValue(adet);
        myRef.child("Satış").child(uuidString).child("musteriName").setValue(musteriName);
        myRef.child("Satış").child(uuidString).child("isim").setValue(isim);
        myRef.child("Satış").child(uuidString).child("netTutar").setValue(netTutar);
        myRef.child("Satış").child(uuidString).child("kdv").setValue(kdv);
        myRef.child("Satış").child(uuidString).child("toplam").setValue(toplam);

        int borçInt = Integer.parseInt(borçFB.get(0));
        int toplamciroInt = Integer.parseInt(toplamciroFB.get(0));
        int toplamInt = Integer.parseInt(toplam);

        myRef.child("Kasa").child(musteriName).child("borç").setValue(Integer.toString(borçInt+toplamInt));
        myRef.child("Kasa").child(musteriName).child("toplamciro").setValue(Integer.toString(toplamciroInt+toplamInt));

        Toast.makeText(getApplicationContext(),"Satış İşlemi Başarılı",Toast.LENGTH_LONG).show();

        String isim = "";
        String adet = "0";
        String netTutar = "";
        String kdv = "";
        String toplam = "0";
        String musteriName=" ";
        Intent intent = new Intent(getApplicationContext(), Satislar.class);

        intent.putExtra("musteriName", musteriName);
        intent.putExtra("isim", isim);
        intent.putExtra("adet", adet);
        intent.putExtra("netTutar", netTutar);
        intent.putExtra("kdv", kdv);
        intent.putExtra("toplam", toplam);

        startActivity(intent);

    }

    private void getDataFirebase() {

        System.out.println("musteriName "+musteriName);

        DatabaseReference newReference = database.getReference("Kasa");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    if (ds.getKey().equals(musteriName)) {

                        HashMap<String, Object> hashMap = (HashMap<String, Object>) ds.getValue();
                        borçFB.add((String) hashMap.get("borç"));
                        toplamciroFB.add((String) hashMap.get("toplamciro"));
                    }

                }

                System.out.println("aaaborçFB = " +borçFB);
                System.out.println("aaatoplamciroFB = " +toplamciroFB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
