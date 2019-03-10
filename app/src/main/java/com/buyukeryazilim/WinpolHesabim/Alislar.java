package com.buyukeryazilim.WinpolHesabim;

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
import java.util.Collections;
import java.util.HashMap;

public class Alislar extends AppCompatActivity {

    FirebaseDatabase database;

    ListView listViewMusteri;

    ArrayList<String> islemTarihiFB;
    ArrayList<String> isimFB;
    ArrayList<String> kdvFB;
    ArrayList<String> musteriNameFB;
    ArrayList<String> netTutarFB;
    ArrayList<String> toplamFB;
    ArrayList<String> adetFB;

    String islemTarihi;
    String isim;
    String kdv;
    String musteriName;
    String netTutar;
    String toplam;
    String adet;

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alislar);

        listViewMusteri = findViewById(R.id.listView);
        listViewMusteri.clearAnimation();

        database = FirebaseDatabase.getInstance();

        islemTarihiFB = new ArrayList<String>();
        isimFB = new ArrayList<String>();
        kdvFB = new ArrayList<String>();
        musteriNameFB = new ArrayList<String>();
        netTutarFB = new ArrayList<String>();
        toplamFB = new ArrayList<String>();
        adetFB = new ArrayList<String>();


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

    public void alisIcinTedarikciSecmeButtonClick(View view) {

        String mod = "alış";
        Intent intent = new Intent(getApplicationContext(), Tedarikciler.class);
        intent.putExtra("mod",mod);
        startActivity(intent);

    }

    private void getDataFirebase() {

        DatabaseReference newReference = database.getReference(firebaseAuth.getCurrentUser().getUid()).child("Alış");
        newReference.addValueEventListener(new ValueEventListener() {
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
                }
                /*System.out.println("aaaaislemTarihiFB= "+islemTarihiFB);
                System.out.println("aaaaisimFB= "+isimFB);
                System.out.println("aaaakdvFB= "+kdvFB);
                System.out.println("aaaamusteriNameFB= "+musteriNameFB);
                System.out.println("aaaanetTutarFB= "+netTutarFB);
                System.out.println("aaaatoplamFB= "+toplamFB);*/


                Collections.sort(islemTarihiFB);
                ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>(Alislar.this, android.R.layout.simple_list_item_1, android.R.id.text1, islemTarihiFB);
                listViewMusteri.setAdapter(veriAdaptoru);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listViewOnClick() {

        listViewMusteri.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                islemTarihi = islemTarihiFB.get(position);
                isim = isimFB.get(position);
                kdv = kdvFB.get(position);
                musteriName = musteriNameFB.get(position);
                netTutar = netTutarFB.get(position);
                toplam = toplamFB.get(position);
                adet = adetFB.get(position);

                Intent intent = new Intent(getApplicationContext(), SatisBilgisi.class);

                intent.putExtra("islemTarihi", islemTarihi);
                intent.putExtra("isim", isim);
                intent.putExtra("kdv", kdv);
                intent.putExtra("musteriName", musteriName);
                intent.putExtra("netTutar", netTutar);
                intent.putExtra("toplam", toplam);
                intent.putExtra("adet", adet);

                startActivity(intent);

            }
        });
    }
}
