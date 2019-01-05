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

    ArrayList<String> urunNameFB;
    ArrayList<String> birimFiyatFB;
    ArrayList<String> kdvOraniFB;

    String urunName;
    String mod="";
    String musteriName;
    String birimFiyat;
    String kdvOrani="";


    Context context = this;

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
        birimFiyatFB = new ArrayList<String>();
        kdvOraniFB = new ArrayList<String>();

        getDataFirebase();
        listViewOnClick();
    }

    public void urunBilgileriButtonClick(View view) {

        Intent intent = new Intent(getApplicationContext(), UrunBilgileri.class);
        startActivity(intent);

    }

    private void getDataFirebase() {

        DatabaseReference newReference = database.getReference("Ürünler");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                System.out.println("dataSnapshot = "+dataSnapshot);

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    HashMap<String, Object> hashMap = (HashMap<String, Object>) ds.getValue();
                    urunNameFB.add((String) hashMap.get("isim"));
                    birimFiyatFB.add((String) hashMap.get("satisFiyati"));
                    kdvOraniFB.add((String) hashMap.get("satisKDVOranı"));
                }

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
                    birimFiyat = birimFiyatFB.get(position);
                    kdvOrani = kdvOraniFB.get(position);

                    Intent intent = new Intent(getApplicationContext(), SatisIslemleriDetay.class);

                    intent.putExtra("urunName", urunName);
                    intent.putExtra("musteriName", musteriName);
                    intent.putExtra("birimFiyat", birimFiyat);
                    intent.putExtra("kdvOrani", kdvOrani);

                    startActivity(intent);

                }
            });

        }else{

            listViewUrunler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    urunName = urunNameFB.get(position);

                    Intent intent = new Intent(getApplicationContext(), UrunBilgileriDetay.class);

                    intent.putExtra("urunName", urunName);

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
