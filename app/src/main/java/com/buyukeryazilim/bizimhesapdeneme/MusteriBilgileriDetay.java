package com.buyukeryazilim.bizimhesapdeneme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MusteriBilgileriDetay extends AppCompatActivity {

    TextView textViewMusteriName;
    TextView textViTelefonNumarasi;
    TextView textVMail;
    TextView textVAdres;
    TextView textVToplamCiro;
    TextView textVBorc;
    TextView textVAlacagi;

    String musteriName;

    FirebaseDatabase database;

    ArrayList<String> borçFB;
    ArrayList<String> toplamciroFB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musteri_bilgileri_detay);

        textViewMusteriName = findViewById(R.id.textViewMusteriName);
        textViTelefonNumarasi = findViewById(R.id.textViewTelefonNumarasi);
        textVMail = findViewById(R.id.textViewMail);
        textVAdres = findViewById(R.id.textViewAdres);
        textVToplamCiro = findViewById(R.id.textViewToplamCiro);
        textVBorc = findViewById(R.id.textViewBorc);
        textVAlacagi = findViewById(R.id.textViewAlacagı);

        Intent intent = getIntent();
        musteriName = intent.getStringExtra("musteriName");
        final String musteriAdres = intent.getStringExtra("musteriAdres");
        final String musteriMail = intent.getStringExtra("musteriMail");
        final String musteriTelefon = intent.getStringExtra("musteriTelefon");

        textViewMusteriName.setText(musteriName);
        textViTelefonNumarasi.setText(musteriTelefon);
        textVMail.setText(musteriMail);
        textVAdres.setText(musteriAdres);

        database = FirebaseDatabase.getInstance();

        borçFB = new ArrayList<String>();
        toplamciroFB = new ArrayList<String>();

        getDataFirebase();
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

                int borcInt = Integer.parseInt(borçFB.get(0));
                textVToplamCiro.setText(toplamciroFB.get(0));

                if (borcInt > 0){
                    textVBorc.setText(borçFB.get(0));
                    textVAlacagi.setText("0");
                }else if (borcInt < 0){
                    textVBorc.setText("0");
                    textVAlacagi.setText(Integer.toString(Math.abs(borcInt)));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void odemeButtonClick (View view) {

        Intent intent = new Intent(getApplicationContext(), MusteriOdemeEkrani.class);

        intent.putExtra("borç", borçFB.get(0));
        intent.putExtra("musteriName", musteriName);

        startActivity(intent);

    }
}
