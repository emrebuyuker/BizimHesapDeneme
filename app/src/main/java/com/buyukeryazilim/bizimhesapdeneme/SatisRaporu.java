package com.buyukeryazilim.bizimhesapdeneme;

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

public class SatisRaporu extends AppCompatActivity {

    FirebaseDatabase database;

    ListView listView;

    String başlangıçTarihi;
    String bitişTarihi;

    ArrayList<String> islemTarihiFB;
    ArrayList<String> isimFB;
    ArrayList<String> kdvFB;
    ArrayList<String> musteriNameFB;
    ArrayList<String> netTutarFB;
    ArrayList<String> toplamFB;
    ArrayList<String> adetFB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satis_raporu);

        database = FirebaseDatabase.getInstance();

        islemTarihiFB = new ArrayList<String>();
        isimFB = new ArrayList<String>();
        kdvFB = new ArrayList<String>();
        musteriNameFB = new ArrayList<String>();
        netTutarFB = new ArrayList<String>();
        toplamFB = new ArrayList<String>();
        adetFB = new ArrayList<String>();

        Intent intent = getIntent();
        başlangıçTarihi = intent.getStringExtra("başlangıçTarihi");
        bitişTarihi = intent.getStringExtra("bitişTarihi");

        listView = findViewById(R.id.listview);
        listView.clearAnimation();

        getDataFirebase();
        listViewOnClick();
    }

    private void getDataFirebase() {

        DatabaseReference newReference = database.getReference("Satış");
        newReference.orderByChild("islemTarihi2").startAt(başlangıçTarihi).endAt(bitişTarihi).addValueEventListener(new ValueEventListener() {
        //newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                System.out.println("dataSnapshot = "+dataSnapshot);

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    HashMap<String, Object> hashMap = (HashMap<String, Object>) ds.getValue();
                    islemTarihiFB.add((String) hashMap.get("islemTarihi"));
                    isimFB.add((String) hashMap.get("isim"));
                    kdvFB.add((String) hashMap.get("kdv"));
                    musteriNameFB.add((String) hashMap.get("musteriName"));
                    netTutarFB.add((String) hashMap.get("netTutar"));
                    toplamFB.add((String) hashMap.get("toplam"));
                    adetFB.add((String) hashMap.get("adet"));
                    /*musteriAdresFB.add((String) hashMap.get("adres"));
                    musteriMailFB.add((String) hashMap.get("mail"));
                    musteriTelefonFB.add((String) hashMap.get("telefon"));*/
                }

                ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>(SatisRaporu.this, android.R.layout.simple_list_item_1, android.R.id.text1, isimFB);
                listView.setAdapter(veriAdaptoru);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listViewOnClick() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), SatisBilgisi.class);

                intent.putExtra("islemTarihi", islemTarihiFB.get(position));
                intent.putExtra("isim", isimFB.get(position));
                intent.putExtra("kdv", kdvFB.get(position));
                intent.putExtra("musteriName", musteriNameFB.get(position));
                intent.putExtra("netTutar", netTutarFB.get(position));
                intent.putExtra("toplam", toplamFB.get(position));
                intent.putExtra("adet", adetFB.get(position));

                startActivity(intent);

            }
        });
    }


}
