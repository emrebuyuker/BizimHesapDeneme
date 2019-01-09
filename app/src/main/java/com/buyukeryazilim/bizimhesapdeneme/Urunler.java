package com.buyukeryazilim.bizimhesapdeneme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Urunler extends AppCompatActivity {

    FirebaseDatabase database;

    ListView listViewUrunler;

    ArrayList<String> alisFiyatiFB;
    ArrayList<String> alisKDVOranıFB;
    ArrayList<String> urunNameFB;
    ArrayList<String> satisFiyatFB;
    ArrayList<String> satisKDVOraniFB;
    ArrayList<String> urunTipiFB;
    ArrayList<String> urunAdetiFB;
    ArrayList<String> urunKeyFB;

    String urunName;
    String mod="";
    String musteriName;
    String birimFiyat;
    String kdvOrani="";


    Context context = this;

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urunler);

        Intent intent = getIntent();
        mod = intent.getStringExtra("mod");
        musteriName = intent.getStringExtra("musteriName");

        listViewUrunler = findViewById(R.id.listView);
        listViewUrunler.clearAnimation();

        database = FirebaseDatabase.getInstance();

        urunNameFB = new ArrayList<String>();
        satisFiyatFB = new ArrayList<String>();
        satisKDVOraniFB = new ArrayList<String>();
        alisFiyatiFB = new ArrayList<String>();
        alisKDVOranıFB = new ArrayList<String>();
        urunTipiFB = new ArrayList<String>();
        urunAdetiFB = new ArrayList<String>();
        urunKeyFB = new ArrayList<String>();

        getDataFirebase();
        listViewOnClick();
    }

    public void urunBilgileriButtonClick(View view) {

        Intent intent = new Intent(getApplicationContext(), UrunBilgileri.class);
        startActivity(intent);

    }

    private void getDataFirebase() {

        DatabaseReference newReference = database.getReference(firebaseAuth.getCurrentUser().getUid()).child("Ürünler");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                System.out.println("dataSnapshot2 = "+dataSnapshot.getChildren());

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    HashMap<String, Object> hashMap = (HashMap<String, Object>) ds.getValue();

                    urunNameFB.add((String) hashMap.get("isim"));
                    satisFiyatFB.add((String) hashMap.get("satisFiyati"));
                    satisKDVOraniFB.add((String) hashMap.get("satisKDVOranı"));
                    alisFiyatiFB.add((String) hashMap.get("alisFiyati"));
                    alisKDVOranıFB.add((String) hashMap.get("alisKDVOranı"));
                    urunTipiFB.add((String) hashMap.get("urunTipi"));
                    urunAdetiFB.add((String) hashMap.get("adet"));
                    urunKeyFB.add(ds.getKey());
                }

                System.out.println("dataSnapshot3 = "+urunKeyFB);


                ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>(Urunler.this, android.R.layout.simple_list_item_1, android.R.id.text1, urunNameFB);
                listViewUrunler.setAdapter(veriAdaptoru);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listViewOnClick() {

        System.out.println("modd = "+mod);

        if (mod.equals("satıs")) {

            listViewUrunler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    urunName = urunNameFB.get(position);
                    birimFiyat = satisFiyatFB.get(position);

                    Intent intent = new Intent(getApplicationContext(), SatisIslemleriDetay.class);

                    intent.putExtra("urunName", urunName);
                    intent.putExtra("musteriName", musteriName);
                    intent.putExtra("birimFiyat", birimFiyat);
                    intent.putExtra("kdvOrani", satisKDVOraniFB.get(position));
                    intent.putExtra("urunKey", urunKeyFB.get(position));

                    startActivity(intent);

                }
            });

        }else if(mod.equals("alış")){

            listViewUrunler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getApplicationContext(), AlisIslemleriDetay.class);

                    intent.putExtra("urunName", urunNameFB.get(position));
                    intent.putExtra("musteriName", musteriName);
                    intent.putExtra("birimFiyat", alisFiyatiFB.get(position));
                    intent.putExtra("kdvOrani", alisKDVOranıFB.get(position));
                    intent.putExtra("urunKey", urunKeyFB.get(position));

                    startActivity(intent);

                }
            });

        }else{

            listViewUrunler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    urunName = urunNameFB.get(position);

                    Intent intent = new Intent(getApplicationContext(), UrunBilgileriDetay.class);

                    intent.putExtra("urunName", urunNameFB.get(position));
                    intent.putExtra("satisFiyat", satisFiyatFB.get(position));
                    intent.putExtra("satisKDVOranı", satisKDVOraniFB.get(position));
                    intent.putExtra("alisFiyati", alisFiyatiFB.get(position));
                    intent.putExtra("alisKDVOranı", alisKDVOranıFB.get(position));
                    intent.putExtra("urunTipi", urunTipiFB.get(position));
                    intent.putExtra("urunAdeti", urunAdetiFB.get(position));

                    startActivity(intent);

                }
            });


        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
