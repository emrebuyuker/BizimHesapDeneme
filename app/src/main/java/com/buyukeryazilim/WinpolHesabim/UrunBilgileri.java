package com.buyukeryazilim.WinpolHesabim;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class UrunBilgileri extends AppCompatActivity {

    EditText eTextIsim;
    EditText eTextSatisFiyati;
    EditText eTextAlisFiyati;

    Spinner spinUrunTipi;
    Spinner spinSatisKDVOranı;
    Spinner spinSatisKDVTuru;
    Spinner spinAlisKDVOranı;
    Spinner spinAlisKDVTuru;

    Uri selected;

    FirebaseDatabase database;
    DatabaseReference myRef;

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_bilgileri);

        eTextIsim = findViewById(R.id.editTextMasrafIsim);
        eTextSatisFiyati = findViewById(R.id.editTextSatisFiyati);
        eTextAlisFiyati = findViewById(R.id.editTextAlisFiyati);
        spinUrunTipi = findViewById(R.id.spinnerUrunTipi);
        spinSatisKDVOranı = findViewById(R.id.spinnerSatisKDVOranı);
        spinSatisKDVTuru = findViewById(R.id.spinnerSatisKDVTuru);
        spinAlisKDVOranı = findViewById(R.id.spinnerAlisKDVOranı);
        spinAlisKDVTuru = findViewById(R.id.spinnerAlisKDVTuru);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

    public void kaydetButtonClick (View view) {


        String isim = eTextIsim.getText().toString();
        String satisFiyati = eTextSatisFiyati.getText().toString();
        String alisFiyati = eTextAlisFiyati.getText().toString();
        String urunTipi = spinUrunTipi.getSelectedItem().toString();
        String satisKDVOranı = spinSatisKDVOranı.getSelectedItem().toString();
        String satisKDVTuru = spinSatisKDVTuru.getSelectedItem().toString();
        String alisKDVOranı = spinAlisKDVOranı.getSelectedItem().toString();
        String alisKDVTuru = spinAlisKDVTuru.getSelectedItem().toString();

        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Ürünler").child(uuidString).child("isim").setValue(isim);
        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Ürünler").child(uuidString).child("satisFiyati").setValue(satisFiyati);
        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Ürünler").child(uuidString).child("alisFiyati").setValue(alisFiyati);
        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Ürünler").child(uuidString).child("urunTipi").setValue(urunTipi);
        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Ürünler").child(uuidString).child("satisKDVOranı").setValue(satisKDVOranı);
        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Ürünler").child(uuidString).child("satisKDVTuru").setValue(satisKDVTuru);
        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Ürünler").child(uuidString).child("alisKDVOranı").setValue(alisKDVOranı);
        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Ürünler").child(uuidString).child("alisKDVTuru").setValue(alisKDVTuru);
        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("Ürünler").child(uuidString).child("adet").setValue("0");

        Toast.makeText(getApplicationContext(),"Ürün eklendi",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getApplicationContext(), UrunBilgileri.class);
        startActivity(intent);

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            String mod = "ürün";
            Intent intent = new Intent(getApplicationContext(), Urunler.class);
            intent.putExtra("mod",mod);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
