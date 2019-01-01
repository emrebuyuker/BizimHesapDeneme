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

public class Tedarikciler extends AppCompatActivity {

    FirebaseDatabase database;

    ListView listViewTedarikci;

    ArrayList<String> tedarikciNameFB;

    String tedarikciName;


    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tedarikciler);

        listViewTedarikci = findViewById(R.id.listView);

        database = FirebaseDatabase.getInstance();

        tedarikciNameFB = new ArrayList<String>();

        getDataFirebase();
        listViewOnClick();
    }

    public void tedarikciBilgileriButtonClick(View view) {

        Intent intent = new Intent(getApplicationContext(), TedarikciBilgileri.class);
        startActivity(intent);

    }

    private void getDataFirebase() {

        DatabaseReference newReference = database.getReference("Tedarikçiler");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                System.out.println("dataSnapshot = "+dataSnapshot);

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    HashMap<String, Object> hashMap = (HashMap<String, Object>) ds.getValue();
                    tedarikciNameFB.add((String) hashMap.get("isim"));
                }

                ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>(Tedarikciler.this, android.R.layout.simple_list_item_1, android.R.id.text1, tedarikciNameFB);
                listViewTedarikci.setAdapter(veriAdaptoru);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listViewOnClick() {

        listViewTedarikci.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                tedarikciName = tedarikciNameFB.get(position);

                Intent intent = new Intent(getApplicationContext(),TedarikciBilgileriDetay.class);

                intent.putExtra("tedarikciName", tedarikciName);

                startActivity(intent);

            }
        });
    }
}
