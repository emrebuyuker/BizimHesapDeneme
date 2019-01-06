package com.buyukeryazilim.bizimhesapdeneme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Hesaplar extends AppCompatActivity {

    TextView textVKasa;
    TextView textVKrediKarti;
    TextView textVToplam;

    ArrayList<String> nakitFB;
    ArrayList<String> kredikartiFB;

    DatabaseReference myRef;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hesaplar);

        textVKasa = findViewById(R.id.textViewKasa);
        textVKrediKarti = findViewById(R.id.textViewKrediKarti);
        textVToplam = findViewById(R.id.textViewToplam);

        nakitFB = new ArrayList<String>();
        kredikartiFB = new ArrayList<String>();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        getDataFirebase();

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

       DatabaseReference newReference2 = database.getReference("KasaHesabı");
        newReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    HashMap<String, Object> hashMap2 = (HashMap<String, Object>) ds.getValue();
                    nakitFB.add((String) hashMap2.get("nakit"));
                    kredikartiFB.add((String) hashMap2.get("kredikartı"));

                }

                textVKasa.setText(nakitFB.get(0));
                textVKrediKarti.setText(kredikartiFB.get(0));


                int nakit = Integer.parseInt(nakitFB.get(0));
                int krediKarti = Integer.parseInt(kredikartiFB.get(0));

                textVToplam.setText(Integer.toString(nakit+krediKarti));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
