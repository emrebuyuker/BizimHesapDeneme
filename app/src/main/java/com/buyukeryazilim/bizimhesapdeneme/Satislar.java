package com.buyukeryazilim.bizimhesapdeneme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Satislar extends AppCompatActivity {

    FirebaseDatabase database;

    ListView listViewMusteri;

    ArrayList<String> musteriNameFB;

    String musteriName;
    String mod="";


    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satislar);

        listViewMusteri = findViewById(R.id.listView);

        database = FirebaseDatabase.getInstance();

        musteriNameFB = new ArrayList<String>();

        getDataFirebase();
    }

    public void satisIcinMusteriSecmeButtonClick(View view) {

        String mod = "satış";
        Intent intent = new Intent(getApplicationContext(), Musteriler.class);
        intent.putExtra("mod",mod);
        startActivity(intent);

    }

    private void getDataFirebase() {

        DatabaseReference newReference = database.getReference("Satış");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                System.out.println("dataSnapshot = "+dataSnapshot);

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    HashMap<String, Object> hashMap = (HashMap<String, Object>) ds.getValue();
                    musteriNameFB.add((String) hashMap.get("islemTarihi"));
                }

                ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>(Satislar.this, android.R.layout.simple_list_item_1, android.R.id.text1, musteriNameFB);
                listViewMusteri.setAdapter(veriAdaptoru);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


