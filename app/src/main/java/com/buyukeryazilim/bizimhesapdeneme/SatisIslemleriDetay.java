package com.buyukeryazilim.bizimhesapdeneme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SatisIslemleriDetay extends AppCompatActivity {

    TextView urunIsmi;
    TextView textVBirimFiyat;
    TextView tutar;
    TextView textVKDVOrani;
    TextView KDVTL;
    TextView textViewToplam;

    EditText miktar;

    ArrayList<String> satisFiyatiFB;
    ArrayList<String> KDVOranıFB;

    FirebaseDatabase database;

    String musteriName;
    String birimFiyat;
    String kdvOrani;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satis_islemleri_detay);

        urunIsmi = findViewById(R.id.textViewUrunIsmi);
        textVBirimFiyat = findViewById(R.id.textViewBirimFiyat);
        tutar = findViewById(R.id.textViewTutar);
        textVKDVOrani = findViewById(R.id.textViewKDVOrani);
        KDVTL = findViewById(R.id.textViewKDVTL);
        textViewToplam = findViewById(R.id.textViewToplam);
        miktar = findViewById(R.id.editTextMiktar);

        Intent intent = getIntent();
        final String urunName = intent.getStringExtra("urunName");
        musteriName = intent.getStringExtra("musteriName");
        birimFiyat = intent.getStringExtra("birimFiyat");
        kdvOrani = intent.getStringExtra("kdvOrani");

        urunIsmi.setText(urunName);
        textVBirimFiyat.setText(birimFiyat);
        textVKDVOrani.setText(kdvOrani);

        database = FirebaseDatabase.getInstance();

        satisFiyatiFB = new ArrayList<String>();
        KDVOranıFB = new ArrayList<String>();

        //getDataFirebase();
        addTextChangedListener();

    }

    private void getDataFirebase() {

        DatabaseReference newReference = database.getReference("Ürünler");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    HashMap<String, Object> hashMap = (HashMap<String, Object>) ds.getValue();
                    satisFiyatiFB.add((String) hashMap.get("satisFiyati"));
                    KDVOranıFB.add((String) hashMap.get("satisKDVOranı"));

                }

                //textVKDVOrani.setText(KDVOranıFB.get(0));
                //textVBirimFiyat.setText(satisFiyatiFB.get(0));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addTextChangedListener(){

        miktar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {


                if(miktar.getText().toString().trim().equals("") )
                {
                    Toast.makeText(context, "Lütfen miktar giriniz", Toast.LENGTH_SHORT).show();
                }else{
                    int ifade1 = Integer.parseInt(satisFiyatiFB.get(0));
                    int ifade2 = Integer.parseInt(miktar.getText().toString());
                    int ifade3 = (ifade2*ifade1);
                    tutar.setText(Integer.toString(ifade3));
                    int kdvtl = Integer.parseInt(KDVOranıFB.get(0));
                    KDVTL.setText(Integer.toString(ifade3*kdvtl/100));
                    textViewToplam.setText(Integer.toString(ifade3+ifade3*kdvtl/100));
                }


            }
        });

    }

    public void kaydetButtonClick (View view) {


        String isim = urunIsmi.getText().toString();
        String adet = miktar.getText().toString();
        String netTutar = tutar.getText().toString();
        String kdv = KDVTL.getText().toString();
        String toplam = textViewToplam.getText().toString();

        Intent intent = new Intent(getApplicationContext(), SatisIslemleri.class);

        intent.putExtra("musteriName", musteriName);
        intent.putExtra("isim", isim);
        intent.putExtra("adet", adet);
        intent.putExtra("netTutar", netTutar);
        intent.putExtra("kdv", kdv);
        intent.putExtra("toplam", toplam);

        startActivity(intent);

    }
}
