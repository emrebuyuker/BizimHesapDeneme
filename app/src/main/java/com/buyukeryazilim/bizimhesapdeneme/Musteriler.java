package com.buyukeryazilim.bizimhesapdeneme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class Musteriler extends AppCompatActivity {

    FirebaseDatabase database;

    ListView listViewMusteri;

    ArrayList<String> musteriNameFB;

    String musteriName;
    String mod="";


    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musteriler);

        Intent intent = getIntent();
        mod = intent.getStringExtra("mod");


        listViewMusteri = findViewById(R.id.listView);

        database = FirebaseDatabase.getInstance();

        musteriNameFB = new ArrayList<String>();

        getDataFirebase();
        listViewOnClick();
    }

    public void musteriBilgileriButtonClick(View view) {

        Intent intent = new Intent(getApplicationContext(), MusteriBilgileri.class);
        startActivity(intent);

    }

    private void getDataFirebase() {

        DatabaseReference newReference = database.getReference("Müşteriler");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                System.out.println("dataSnapshot = "+dataSnapshot);

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    HashMap<String, Object> hashMap = (HashMap<String, Object>) ds.getValue();
                    musteriNameFB.add((String) hashMap.get("isim"));
                }

                ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>(Musteriler.this, android.R.layout.simple_list_item_1, android.R.id.text1, musteriNameFB);
                listViewMusteri.setAdapter(veriAdaptoru);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listViewOnClick() {

        System.out.println("mod = "+mod);

        if (mod.equals("satış")) {

            System.out.println("mod = girdi");

            listViewMusteri.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    musteriName = musteriNameFB.get(position);

                    String isim = "";
                    String adet = "0";
                    String netTutar = "";
                    String kdv = "";
                    String toplam = "0";
                    Intent intent = new Intent(getApplicationContext(), SatisIslemleri.class);

                    intent.putExtra("musteriName", musteriName);
                    intent.putExtra("isim", isim);
                    intent.putExtra("adet", adet);
                    intent.putExtra("netTutar", netTutar);
                    intent.putExtra("kdv", kdv);
                    intent.putExtra("toplam", toplam);


                    startActivity(intent);

                }
            });
        }else{

            listViewMusteri.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    musteriName = musteriNameFB.get(position);

                    Intent intent = new Intent(getApplicationContext(), MusteriBilgileriDetay.class);

                    intent.putExtra("musteriName", musteriName);

                    startActivity(intent);

                }
            });

        }
    }
}
