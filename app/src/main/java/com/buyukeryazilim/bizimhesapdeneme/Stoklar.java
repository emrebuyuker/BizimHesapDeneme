package com.buyukeryazilim.bizimhesapdeneme;

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

public class Stoklar extends AppCompatActivity {

    ListView listView;

    ArrayList<String> urunIsmiFB;
    ArrayList<String> urunAdetiFB;

    String urunIsmi;
    String urunAdeti;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stoklar);

        listView = findViewById(R.id.listView);

        urunIsmiFB = new ArrayList<String>();
        urunAdetiFB = new ArrayList<String>();

        database = FirebaseDatabase.getInstance();

        getDataFirebase();
        listViewOnClick();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getDataFirebase() {

        DatabaseReference newReference = database.getReference("Ürünler");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                System.out.println("dataSnapshot = "+dataSnapshot);

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    HashMap<String, Object> hashMap = (HashMap<String, Object>) ds.getValue();
                    urunIsmiFB.add((String) hashMap.get("isim"));
                    urunAdetiFB.add((String) hashMap.get("adet"));
                }

                ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>(Stoklar.this, android.R.layout.simple_list_item_1, android.R.id.text1, urunIsmiFB);
                listView.setAdapter(veriAdaptoru);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listViewOnClick() {


        System.out.println("mod = girdi");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                urunIsmi = urunIsmiFB.get(position);
                urunAdeti = urunAdetiFB.get(position);

                Intent intent = new Intent(getApplicationContext(), StokDetayi.class);

                intent.putExtra("urunIsmi", urunIsmi);
                intent.putExtra("urunAdeti", urunAdeti);

                startActivity(intent);

            }
        });
    }
}
