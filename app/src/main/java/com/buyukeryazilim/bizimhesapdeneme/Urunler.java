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

public class Urunler extends AppCompatActivity {

    FirebaseDatabase database;

    ListView listViewUrunler;

    ArrayList<String> urunNameFB;

    String urunName;
    String mod="";


    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urunler);

        Intent intent = getIntent();
        mod = intent.getStringExtra("mod");

        listViewUrunler = findViewById(R.id.listView);

        database = FirebaseDatabase.getInstance();

        urunNameFB = new ArrayList<String>();

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

                    Intent intent = new Intent(getApplicationContext(), SatisIslemleriDetay.class);

                    intent.putExtra("urunName", urunName);

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
}
